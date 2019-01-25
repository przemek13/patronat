package com.przemek.patronage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomDTO;
import com.przemek.patronage.Equipment.Equipment;
import com.przemek.patronage.Equipment.EquipmentDTO;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Organization.OrganizationDTO;
import com.przemek.patronage.Reservation.Reservation;
import com.przemek.patronage.Reservation.ReservationDTO;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Mapper() {
    }

    ObjectMapper objectMapper = new ObjectMapper();

    public OrganizationDTO convertToDTO(Organization organization) {
        return objectMapper.convertValue(organization, OrganizationDTO.class);
    }

    public Organization convertToEntity(OrganizationDTO organizationDTO) {
        return objectMapper.convertValue(organizationDTO, Organization.class);
    }

    public EquipmentDTO convertToDTO(Equipment equipment) {
        return objectMapper.convertValue(equipment, EquipmentDTO.class);
    }

    public Equipment convertToEntity(EquipmentDTO equipmentDTO) {
        return objectMapper.convertValue(equipmentDTO, Equipment.class);
    }

    public ConferenceRoomDTO convertToDTO(ConferenceRoom conferenceRoom) {
        return objectMapper.convertValue(conferenceRoom, ConferenceRoomDTO.class);
    }

    public ConferenceRoom convertToEntity(ConferenceRoomDTO conferenceRoomDTO) {
        return objectMapper.convertValue(conferenceRoomDTO, ConferenceRoom.class);
    }

    public ReservationDTO convertToDTO(Reservation reservation) {
        return objectMapper.convertValue(reservation, ReservationDTO.class);
    }

    public Reservation convertToEntity(ReservationDTO reservationDTO) {
        return objectMapper.convertValue(reservationDTO, Reservation.class);
    }
}