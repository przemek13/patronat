package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Exceptions.NoSuchIdException;
import com.przemek.patronage.Exceptions.RoomReservedException;
import com.przemek.patronage.Exceptions.StartAfterEndException;
import com.przemek.patronage.Exceptions.WrongDurationException;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservationService {

    private ReservationRepository reservations;
    private ConferenceRoomRepository conferenceRooms;

    @Autowired
    public ReservationService(ReservationRepository reservations, ConferenceRoomRepository conferenceRooms) {
        this.reservations = Objects.requireNonNull(reservations, "must be defined.");
        this.conferenceRooms = Objects.requireNonNull(conferenceRooms, "must be defined");
    }

    public List<Reservation> findAll() {
        return reservations.findAll();
    }

    public void save(Reservation newReservation, Long id) throws NoSuchIdException, StartAfterEndException, WrongDurationException, RoomReservedException {
        if (conferenceRooms.findById(id).equals(Optional.empty())) {
            throw new NoSuchIdException("The Conference room with id given doesn't exist in the base.");
        }
        var room = conferenceRooms.findById(id).get();
        newReservation.setConferenceRoom(room);
        newReservation.setReservationStart(newReservation.getReservationStart().truncatedTo(ChronoUnit.MINUTES));
        newReservation.setReservationEnd(newReservation.getReservationEnd().truncatedTo(ChronoUnit.MINUTES));
        if (newReservation.getReservationStart().isAfter(newReservation.getReservationEnd())) {
            throw new StartAfterEndException("Reservation start can't be after reservation end.");
        }
        var duration = Duration.between(newReservation.getReservationEnd(), newReservation.getReservationStart());
        var diff = Math.abs(duration.toMinutes());
        if (120L < diff || diff <= 5L) {
            throw new WrongDurationException("Reservation has to be longer than 5 minutes and can not exceed 2 hours.");
        }
        conferenceRooms.findById(id).get().getReservations().stream()
                .filter(reservation ->
                        (newReservation.getReservationStart().isEqual(reservation.getReservationStart())) ||
                        (((newReservation.getReservationStart().isAfter(reservation.getReservationStart()))) && ((newReservation.getReservationStart().isBefore(reservation.getReservationEnd())))) ||
                        (newReservation.getReservationEnd().isEqual(reservation.getReservationEnd())) ||
                        (((newReservation.getReservationEnd().isAfter(reservation.getReservationStart()) && ((newReservation.getReservationEnd().isBefore(reservation.getReservationEnd())))))) ||
                        ((newReservation.getReservationStart().isBefore(reservation.getReservationStart()) && (newReservation.getReservationEnd().isAfter(reservation.getReservationEnd()))))
                )
                .findAny()
                .ifPresent(reservation -> {
                    throw new IllegalArgumentException("Room already reserved in the given period.");
                });
        room.setAvailable(false);
        room.getReservations().add(newReservation);
        reservations.save(newReservation);
    }

    public Reservation update(Reservation newReservation, Long id) {

        return reservations.findById(id)
                .map(reservation -> {
                    reservation.setReservingId(newReservation.getReservingId());
                    reservation.setReservationStart(newReservation.getReservationStart());
                    reservation.setReservationEnd(newReservation.getReservationEnd());
                    reservation.setConferenceRoom(newReservation.getConferenceRoom());
                    return reservations.save(reservation);
                })
                .orElseGet(() -> {
                    newReservation.setId(id);
                    return reservations.save(newReservation);
                });
    }

    public void delete(Long id) throws NoSuchIdException {
        if (reservations.findById(id).equals(Optional.empty())) {
            throw new NoSuchIdException("The Reservation with id given doesn't exist in the base.");
        } else
            reservations.deleteById(id);
    }
}
