package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Exceptions.RoomReservedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;

@Component
public class ReservationCheck {

    private final ConferenceRoomRepository conferenceRoomRepository;

    @Autowired
    public ReservationCheck(ConferenceRoomRepository conferenceRoomRepository) {
        this.conferenceRoomRepository = Objects.requireNonNull(conferenceRoomRepository, "must be defined.");
    }

    protected void checkReservationDates(Reservation newReservation) {
        if (newReservation.getReservationStart().isAfter(newReservation.getReservationEnd())) {
            throw new IllegalArgumentException("Reservation start can't be after reservation end.");
        }
    }

    protected void checkDuration(Reservation newReservation) {
        var duration = Duration.between(newReservation.getReservationEnd(), newReservation.getReservationStart());
        var diff = Math.abs(duration.toMinutes());
        if (120L < diff || diff <= 5L) {
            throw new IllegalArgumentException("Reservation has to be longer than 5 minutes and can not exceed 2 hours.");
        }
    }

    protected void checkIfReserved(Reservation newReservation, Long id) {
        conferenceRoomRepository.findById(id).get().getReservations().stream()
                .filter(reservation ->
                        (newReservation.getReservationStart().isEqual(reservation.getReservationStart())) ||
                                (((newReservation.getReservationStart().isAfter(reservation.getReservationStart()))) && ((newReservation.getReservationStart().isBefore(reservation.getReservationEnd())))) ||
                                (newReservation.getReservationEnd().isEqual(reservation.getReservationEnd())) ||
                                (((newReservation.getReservationEnd().isAfter(reservation.getReservationStart()) && ((newReservation.getReservationEnd().isBefore(reservation.getReservationEnd())))))) ||
                                ((newReservation.getReservationStart().isBefore(reservation.getReservationStart()) && (newReservation.getReservationEnd().isAfter(reservation.getReservationEnd()))))
                )
                .findAny()
                .ifPresent(reservation -> {
                    throw new RoomReservedException("The room is already reserved in the given period.");
                });
    }
}
