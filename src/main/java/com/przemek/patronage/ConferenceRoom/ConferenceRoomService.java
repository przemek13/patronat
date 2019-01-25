package com.przemek.patronage.ConferenceRoom;

import com.przemek.patronage.Organization.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ConferenceRoomService {

    private ConferenceRoomRepository conferenceRoomRepository;
    private OrganizationRepository organizationRepository;

    @Autowired
    public ConferenceRoomService(ConferenceRoomRepository conferenceRoomRepository, OrganizationRepository organizationRepository) {
        this.conferenceRoomRepository = Objects.requireNonNull(conferenceRoomRepository, "must be defined.");
        this.organizationRepository = Objects.requireNonNull(organizationRepository, "must be defined.");
    }

    public List<ConferenceRoom> findAll() {
        return conferenceRoomRepository.findAll();
    }

    public void save(ConferenceRoom newConferenceRoom, Long id) {
        if (organizationRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("The Organization with id given doesn't exist in the base.");
        }
        var organization = organizationRepository.findById(id).get();
        newConferenceRoom.setOrganization(organization);
        if (conferenceRoomRepository.findByName(newConferenceRoom.getName()) == null) {
            organization.getConferenceRoomsList().add(newConferenceRoom);
            conferenceRoomRepository.save(newConferenceRoom);
            organizationRepository.save(organization);
        } else {
            throw new IllegalArgumentException("The Conference room with name given already exist. Please choose different name.");
        }
    }

    public ConferenceRoom update(ConferenceRoom newConferenceRoom, Long id) {
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
                    conferenceRoom.setOrganization(newConferenceRoom.getOrganization());
                    conferenceRoom.setEquipment(newConferenceRoom.getEquipment());
                    return conferenceRoomRepository.save(conferenceRoom);
                })
                .orElseGet(() -> {
                    newConferenceRoom.setId(id);
                    return conferenceRoomRepository.save(newConferenceRoom);
                });
    }

    public void delete(Long id) {
        if (conferenceRoomRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("The Conference room with id given doesn't exist in the base.");
        } else
            conferenceRoomRepository.deleteById(id);
    }
}