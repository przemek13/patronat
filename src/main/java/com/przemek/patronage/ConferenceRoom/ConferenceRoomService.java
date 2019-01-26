package com.przemek.patronage.ConferenceRoom;

import com.przemek.patronage.Mapper;
import com.przemek.patronage.Organization.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ConferenceRoomService {

    private ConferenceRoomRepository conferenceRoomRepository;
    private OrganizationRepository organizationRepository;
    private Mapper mapper;

    @Autowired
    public ConferenceRoomService(ConferenceRoomRepository conferenceRoomRepository, OrganizationRepository organizationRepository, Mapper mapper) {
        this.conferenceRoomRepository = Objects.requireNonNull(conferenceRoomRepository, "must be defined.");
        this.organizationRepository = Objects.requireNonNull(organizationRepository, "must be defined.");
        this.mapper = Objects.requireNonNull(mapper, "must be defined.");
    }

    public List<ConferenceRoomDTO> findAll() {
        return conferenceRoomRepository.findAll().stream()
                .map(mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public ConferenceRoomDTO save(ConferenceRoomDTO newConferenceRoomDTO, Long id) {
        var newConferenceRoom = mapper.convertToEntity(newConferenceRoomDTO);
        if (organizationRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("The Organization with id given doesn't exist in the base.");
        }
        var organization = organizationRepository.findById(id).get();
        newConferenceRoom.setOrganization(organization);
        if (conferenceRoomRepository.findByName(newConferenceRoom.getName()) == null) {
            organization.getConferenceRoomsList().add(newConferenceRoom);
            conferenceRoomRepository.save(newConferenceRoom);
            organizationRepository.save(organization);
            return mapper.convertToDTO(newConferenceRoom);
        } else {
            throw new IllegalArgumentException("The Conference room with name given already exist. Please choose different name.");
        }
    }

    public ConferenceRoomDTO update(ConferenceRoomDTO newConferenceRoomDTO, Long id) {
        var newConferenceRoom = mapper.convertToEntity(newConferenceRoomDTO);
        return conferenceRoomRepository.findById(id)
                .map(conferenceRoom -> {
                    conferenceRoom.setName(newConferenceRoom.getName());
                    conferenceRoom.setOptionalId(newConferenceRoom.getOptionalId());
                    conferenceRoom.setFloor(newConferenceRoom.getFloor());
                    conferenceRoom.setAvailable(newConferenceRoom.isAvailable());
                    conferenceRoom.setSittingAndStandingPlaces(newConferenceRoom.getSittingAndStandingPlaces());
                    conferenceRoom.setLyingPlaces(newConferenceRoom.getLyingPlaces());
                    conferenceRoom.setHangingPlaces(newConferenceRoom.getHangingPlaces());
                    conferenceRoom.setReservations(newConferenceRoom.getReservations());
//                    conferenceRoom.setOrganization(newConferenceRoom.getOrganization());
                    conferenceRoom.setEquipment(newConferenceRoom.getEquipment());
                    conferenceRoomRepository.save(conferenceRoom);
                    return mapper.convertToDTO(conferenceRoom);
                })
                .orElseGet(() -> {
                    newConferenceRoom.setId(id);
                    conferenceRoomRepository.save(newConferenceRoom);
                    return mapper.convertToDTO(newConferenceRoom);
                });
    }

    public void delete(Long id) {
        if (conferenceRoomRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("The Conference room with id given doesn't exist in the base.");
        }
        conferenceRoomRepository.deleteById(id);
    }
}