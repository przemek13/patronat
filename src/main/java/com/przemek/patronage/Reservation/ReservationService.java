package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Exceptions.*;
import com.przemek.patronage.Organization.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.przemek.patronage.Exceptions.WrappedException.throwWrapped;

@Service
public class ReservationService {

    private ReservationRepository reservations;
    private ConferenceRoomRepository conferenceRooms;
    private OrganizationRepository organizations;

    @Autowired
    public ReservationService(ReservationRepository reservations, ConferenceRoomRepository conferenceRooms, OrganizationRepository organizations) {
        this.reservations = Objects.requireNonNull(reservations, "must be defined.");
        this.conferenceRooms = Objects.requireNonNull(conferenceRooms, "must be defined");
        this.organizations = Objects.requireNonNull(organizations, "must be defined");
    }

    public List<Reservation> findAll() {
        return reservations.findAll();
    }

    public List<Reservation> findForOne(Long orgId, Long roomId) throws NoSuchIdException {
        if (organizations.findById(orgId).equals(Optional.empty())) {
            throw new NoSuchIdException("The Organization with id given doesn't exist in the base.");
        }
        try {
            organizations.findById(orgId).get().getConferenceRoomsList().stream()
                    .filter(room -> !(room.getId().equals(roomId)))
                    .findAny()
                    .ifPresent(reservation -> {
                        try {
                            throw new NoSuchIdException("The Conference room with id given doesn't exist in the base.");
                        } catch (NoSuchIdException e) {
                            throwWrapped(e);
                        }
                    });
        } catch (WrappedException w) {
            throw (NoSuchIdException) w.cause;
        }
        return organizations.findById(orgId).get().getConferenceRoomsList().stream()
                .filter(room -> room.getId().equals(roomId))
                .findAny()
                .get()
                .getReservations();
    }

    public void save(Reservation newReservation, Long id) throws NoSuchIdException, StartAfterEndException, WrongDurationException, RoomReservedException {
        if (conferenceRooms.findById(id).equals(Optional.empty())) {
            throw new NoSuchIdException("The Conference room with id given doesn't exist in the base.");
        }
        var room = conferenceRooms.findById(id).get();
        newReservation.setConferenceRoom(room);
        checkReservationDates(newReservation);
        checkDuration(newReservation);
        checkIfReserved(newReservation, id);
        room.setAvailable(false);
        room.getReservations().add(newReservation);
        reservations.save(newReservation);
    }

    private void checkReservationDates(Reservation newReservation) throws StartAfterEndException {
        newReservation.setReservationStart(newReservation.getReservationStart().truncatedTo(ChronoUnit.MINUTES));
        newReservation.setReservationEnd(newReservation.getReservationEnd().truncatedTo(ChronoUnit.MINUTES));
        if (newReservation.getReservationStart().isAfter(newReservation.getReservationEnd())) {
            throw new StartAfterEndException("Reservation start can't be after reservation end.");
        }
    }

    private void checkDuration(Reservation newReservation) throws WrongDurationException {
        var duration = Duration.between(newReservation.getReservationEnd(), newReservation.getReservationStart());
        var diff = Math.abs(duration.toMinutes());
        if (120L < diff || diff <= 5L) {
            throw new WrongDurationException("Reservation has to be longer than 5 minutes and can not exceed 2 hours.");
        }
    }

    private void checkIfReserved(Reservation newReservation, Long id) throws RoomReservedException {
        try {
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
                        try {
                            throw new RoomReservedException("Room is already reserved in the given period.");
                        } catch (RoomReservedException e) {
                            throwWrapped(e);
                        }
                    });
        } catch (WrappedException w) {
            throw (RoomReservedException) w.cause;
        }
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
