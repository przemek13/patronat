package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {

    private ReservationRepository reservations;
    private ConferenceRoomRepository conferenceRooms;

    @Autowired
    public ReservationService(ReservationRepository reservations, ConferenceRoomRepository conferenceRooms) {
        this.reservations = Objects.requireNonNull(reservations, "must be defined.");
        this.conferenceRooms = conferenceRooms;
    }

    public List<Reservation> findAll() {
        return reservations.findAll();
    }

    public void save(Reservation newReservation, Long id) {
        ConferenceRoom room = conferenceRooms.findById(id).get();
        newReservation.setConferenceRoom(room);
        reservations.save(newReservation);
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
