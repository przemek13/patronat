package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Mapper;
import com.przemek.patronage.Organization.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;
    private ConferenceRoomRepository conferenceRoomRepository;
    private OrganizationRepository organizationRepository;
    private Mapper mapper;
    private ReservationCheck reservationCheck;

    @Autowired
    public ReservationService(ReservationRepository reservations, ConferenceRoomRepository conferenceRooms, OrganizationRepository organizations, Mapper mapper, ReservationCheck reservationCheck) {
        this.reservationRepository = Objects.requireNonNull(reservations, "must be defined.");
        this.conferenceRoomRepository = Objects.requireNonNull(conferenceRooms, "must be defined");
        this.organizationRepository = Objects.requireNonNull(organizations, "must be defined");
        this.mapper = Objects.requireNonNull(mapper, "must be defined");
        this.reservationCheck = Objects.requireNonNull(reservationCheck, "must be defined");
    }

    public List<ReservationDTO> findAll() {
        return reservationRepository.findAll().stream()
                .map(mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationDTO> findForConcreteConferenceRoom(Long orgId, Long roomId) {
        return organizationRepository.findById(orgId)
                .orElseThrow(() -> new IllegalArgumentException("The Organization with id given doesn't exist in the base."))
                .getConferenceRoomsList()
                .stream()
                .filter(room -> room.getId().equals(roomId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("The Conference room with id given doesn't exist in the base."))
                .getReservations().stream()
                .map(mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReservationDTO save(ReservationDTO newReservationDTO, Long id) {
        var newReservation = mapper.convertToEntity(newReservationDTO);
        if (conferenceRoomRepository.findById(id).equals(Optional.empty())) {
            throw new IllegalArgumentException("The Conference room with id given doesn't exist in the base.");
        }
        var room = conferenceRoomRepository.findById(id).get();
        newReservation.setConferenceRoom(room);
        reservationCheck.checkReservationDates(newReservation);
        reservationCheck.checkDuration(newReservation);
        reservationCheck.checkIfReserved(newReservation, id);
        room.getReservations().add(newReservation);
        reservationRepository.save(newReservation);
        return mapper.convertToDTO(newReservation);
    }

    public ReservationDTO update(ReservationDTO newReservationDTO, Long id) {
        var newReservation = mapper.convertToEntity(newReservationDTO);
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservation.setReservingId(newReservation.getReservingId());
                    reservation.setReservationStart(newReservation.getReservationStart());
                    reservation.setReservationEnd(newReservation.getReservationEnd());
                    reservationRepository.save(reservation);
                    return mapper.convertToDTO(reservation);
                })
                .orElseGet(() -> {
                    newReservation.setId(id);
                    reservationRepository.save(newReservation);
                    return mapper.convertToDTO(newReservation);
                });
    }

    public void delete(Long id) {
        if (reservationRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("The Reservation with id given doesn't exist in the base.");
        }
        reservationRepository.deleteById(id);
    }
}
