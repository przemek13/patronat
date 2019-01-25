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

import java.text.ParseException;

//import org.modelmapper.ModelMapper;

@Component
public class Mapper {

    public Mapper() {
    }

    ObjectMapper objectMapper = new ObjectMapper();

//    ModelMapper modelMapper = new ModelMapper();

    public OrganizationDTO convertToDTO(Organization organization) {
        return objectMapper.convertValue(organization, OrganizationDTO.class);
//        return modelMapper.map(organization, OrganizationDTO.class);
    }

    public Organization convertToEntity(OrganizationDTO organizationDTO) throws ParseException {
        return objectMapper.convertValue(organizationDTO, Organization.class);
//        return modelMapper.map(organizationDTO, Organization.class);
    }

    public EquipmentDTO convertToDTO(Equipment equipment) {
        return objectMapper.convertValue(equipment, EquipmentDTO.class);
//        return modelMapper.map(equipment, EquipmentDTO.class);
    }

    public Equipment convertToEntity(EquipmentDTO equipmentDTO) throws ParseException {
        return objectMapper.convertValue(equipmentDTO, Equipment.class);
//        return modelMapper.map(equipmentDTO, Equipment.class);
    }

    public ConferenceRoomDTO convertToDTO(ConferenceRoom conferenceRoom) {
        return objectMapper.convertValue(conferenceRoom, ConferenceRoomDTO.class);
//        return modelMapper.map(conferenceRoom, ConferenceRoomDTO.class);
    }

    public ConferenceRoom convertToEntity(ConferenceRoomDTO conferenceRoomDTO) throws ParseException {
        return objectMapper.convertValue(conferenceRoomDTO, ConferenceRoom.class);
//        return modelMapper.map(conferenceRoomDTO, ConferenceRoom.class);
    }

    public ReservationDTO convertToDTO(Reservation reservation) {
        return objectMapper.convertValue(reservation, ReservationDTO.class);
//        return modelMapper.map(reservation, ReservationDTO.class);
    }

    public Reservation convertToEntity(ReservationDTO reservationDTO) throws ParseException {
        return objectMapper.convertValue(reservationDTO, Reservation.class);
//        return modelMapper.map(reservationDTO, Reservation.class);
    }
}