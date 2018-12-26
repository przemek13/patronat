package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {

    private ReservationRepository reservations;

    @Autowired
    public ReservationService(ReservationRepository reservations) {
        this.reservations = Objects.requireNonNull(reservations, "must be defined.");
    }

    public List<Reservation> findAll() {
        return reservations.findAll();
    }

    public void save(Reservation newReservation, ConferenceRoom conferenceRoom) {
        for (Reservation reservation : conferenceRoom.reservations)
            if (!((newReservation.getReservationStart().isEqual(reservation.getReservationStart())) &&
                    (newReservation.getReservationStart().isAfter(reservation.getReservationStart())) &&
                    newReservation.getReservationStart().isBefore(reservation.getReservationEnd()))) {
                reservations.save(newReservation);
            }
    }

    Reservation update(Reservation newReservation, Long id) {

        return reservations.findById(id)
                .map(reservation -> {
                    reservation.setReservingId(newReservation.getReservingId());
                    reservation.setReservationStart(newReservation.getReservationStart());
                    reservation.setReservationEnd(newReservation.getReservationEnd());
                    return reservations.save(reservation);
                })
                .orElseGet(() -> {
                    newReservation.setId(id);
                    return reservations.save(newReservation);
                });
    }

    public void delete(Long id) {
        reservations.deleteById(id);
    }
}
