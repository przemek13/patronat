package com.przemek.patronage.ConferenceRoom;

import com.przemek.patronage.Exceptions.NoSuchIdException;
import com.przemek.patronage.Exceptions.SameNameException;
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
    public ConferenceRoomService(ConferenceRoomRepository conferenceRooms, OrganizationRepository organizationRepository) {
        this.conferenceRoomRepository = Objects.requireNonNull(conferenceRooms, "must be defined.");
        this.organizationRepository = Objects.requireNonNull(organizationRepository, "must be defined.");
    }

    public List<ConferenceRoom> findAll() {
        return conferenceRoomRepository.findAll();
    }

    public void save(ConferenceRoom newConferenceRoom, Long id) throws SameNameException, NoSuchIdException {
        if (organizationRepository.findById(id).isEmpty()) {
            throw new NoSuchIdException("The Organization with id given doesn't exist in the base.");
        }
        var org = organizationRepository.findById(id).get();
        newConferenceRoom.setOrganization(org);
        if (conferenceRoomRepository.findByName(newConferenceRoom.getName()) == null) {
            org.getConferenceRoomsList().add(newConferenceRoom);
            conferenceRoomRepository.save(newConferenceRoom);
            organizationRepository.save(org);
        } else {
            throw new SameNameException("The Conference room with name given already exist. Please choose different name.");
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

    public void delete(Long id) throws NoSuchIdException {
        if (conferenceRoomRepository.findById(id).isEmpty()) {
            throw new NoSuchIdException("The Conference room with id given doesn't exist in the base.");
        } else
            conferenceRoomRepository.deleteById(id);
    }
}