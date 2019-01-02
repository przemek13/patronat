package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Exceptions.NoSuchIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        this.conferenceRooms = conferenceRooms;
    }

    public List<Reservation> findAll() {
        return reservations.findAll();
    }

    public void save(Reservation newReservation, Long id) throws NoSuchIdException {
        if (conferenceRooms.findById(id).equals(Optional.empty())) {
            throw new NoSuchIdException("The Conference room with id given doesn't exist in the base.");
        }
        ConferenceRoom room = conferenceRooms.findById(id).get();
        newReservation.setConferenceRoom(room);
        room.getReservations().add(newReservation);
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

    public void delete(Long id) throws NoSuchIdException {
        if (reservations.findById(id).equals(Optional.empty())) {
            throw new NoSuchIdException("The Organization with id given doesn't exist in the base.");
        } else
            reservations.deleteById(id);
    }
}
