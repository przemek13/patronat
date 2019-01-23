package com.przemek.patronage;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomDTO;
import com.przemek.patronage.Equipment.Equipment;
import com.przemek.patronage.Equipment.EquipmentDTO;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Organization.OrganizationDTO;
import com.przemek.patronage.Reservation.Reservation;
import com.przemek.patronage.Reservation.ReservationDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class Mapper {

    ModelMapper modelMapper = new ModelMapper();

    public OrganizationDTO convertToDTO(Organization organization) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(organization, OrganizationDTO.class);
    }

    public Organization convertToEntity(OrganizationDTO organizationDTO) throws ParseException {
        return modelMapper.map(organizationDTO, Organization.class);
    }

    public EquipmentDTO convertToDTO(Equipment equipment) {
        return modelMapper.map(equipment, EquipmentDTO.class);
    }

    public Equipment convertToEntity(EquipmentDTO equipmentDTO) throws ParseException {
        return modelMapper.map(equipmentDTO, Equipment.class);
    }

    public ConferenceRoomDTO convertToDTO(ConferenceRoom conferenceRoom) {
        return modelMapper.map(conferenceRoom, ConferenceRoomDTO.class);
    }

    public ConferenceRoom convertToEntity(ConferenceRoomDTO conferenceRoomDTO) throws ParseException {
        return modelMapper.map(conferenceRoomDTO, ConferenceRoom.class);
    }

    public ReservationDTO convertToDTO(Reservation reservation) {
        return modelMapper.map(reservation, ReservationDTO.class);
    }

    public Reservation convertToEntity(ReservationDTO reservationDTO) throws ParseException {
        return modelMapper.map(reservationDTO, Reservation.class);
    }
}