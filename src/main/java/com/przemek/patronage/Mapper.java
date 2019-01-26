package com.przemek.patronage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomDTO;
import com.przemek.patronage.Equipment.Equipment;
import com.przemek.patronage.Equipment.EquipmentDTO;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Organization.OrganizationDTO;
import com.przemek.patronage.Reservation.JsonDateDeserializer;
import com.przemek.patronage.Reservation.JsonDateSerializer;
import com.przemek.patronage.Reservation.Reservation;
import com.przemek.patronage.Reservation.ReservationDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Mapper {

    private final ObjectMapper objectMapper;

    public Mapper() {
        objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new JsonDateSerializer());
        javaTimeModule.addDeserializer(LocalDateTime.class, new JsonDateDeserializer());
        objectMapper.registerModule(javaTimeModule);
    }

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