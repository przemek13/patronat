package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Organization.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class ReservationService {

    private ReservationRepository reservationRepository;
    private ConferenceRoomRepository conferenceRoomRepository;
    private OrganizationRepository organizationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservations, ConferenceRoomRepository conferenceRooms, OrganizationRepository organizations) {
        this.reservationRepository = Objects.requireNonNull(reservations, "must be defined.");
        this.conferenceRoomRepository = Objects.requireNonNull(conferenceRooms, "must be defined");
        this.organizationRepository = Objects.requireNonNull(organizations, "must be defined");
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public List<Reservation> findForConcreteConferenceRoom(Long orgId, Long roomId) {
        if (organizationRepository.findById(orgId).isEmpty()) {
            throw new IllegalArgumentException("The Organization with id given doesn't exist in the base.");
        }
        organizationRepository.findById(orgId).get().getConferenceRoomsList().stream()
                .filter(room -> !(room.getId().equals(roomId)))
                .findAny()
                .ifPresent(room -> {
                    throw new IllegalArgumentException("The Conference room with id given doesn't exist in the base.");
                });
        return organizationRepository.findById(orgId).get().getConferenceRoomsList().stream()
                .filter(room -> room.getId().equals(roomId))
                .findAny()
                .get()
                .getReservations();
    }

    public void save(Reservation newReservation, Long id) {
        if (conferenceRoomRepository.findById(id).equals(Optional.empty())) {
            throw new IllegalArgumentException("The Conference room with id given doesn't exist in the base.");
        }
        var reservationCheck = new ReservationCheck(conferenceRoomRepository);
        var room = conferenceRoomRepository.findById(id).get();
        newReservation.setConferenceRoom(room);
        reservationCheck.checkReservationDates(newReservation);
        reservationCheck.checkDuration(newReservation);
        reservationCheck.checkIfReserved(newReservation, id);
        room.setAvailable(false);
        room.getReservations().add(newReservation);
        reservationRepository.save(newReservation);
    }

    public Reservation update(Reservation newReservation, Long id) {

        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservation.setReservingId(newReservation.getReservingId());
                    reservation.setReservationStart(newReservation.getReservationStart());
                    reservation.setReservationEnd(newReservation.getReservationEnd());
                    reservation.setConferenceRoom(newReservation.getConferenceRoom());
                    return reservationRepository.save(reservation);
                })
                .orElseGet(() -> {
                    newReservation.setId(id);
                    return reservationRepository.save(newReservation);
                });
    }

    public void delete(Long id) {
        if (reservationRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("The Reservation with id given doesn't exist in the base.");
        } else
            reservationRepository.deleteById(id);
    }
}
