package com.przemek.patronage.ConferenceRoom;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.przemek.patronage.Exceptions.SameNameException;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Organization.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ConferenceRoomService {

    private ConferenceRoomRepository conferenceRooms;
    private OrganizationRepository organizations;

    @Autowired
    public ConferenceRoomService(ConferenceRoomRepository conferenceRooms, OrganizationRepository organizations) {
        this.conferenceRooms = Objects.requireNonNull(conferenceRooms, "must be defined.");
        this.organizations = organizations;
    }

    public List<ConferenceRoom> findAll() {
        return conferenceRooms.findAll();
    }

    public void save(ConferenceRoom newConferenceRoom, Long id) throws SameNameException {
        Organization org = organizations.findById(id).get();
        newConferenceRoom.setOrganization(org);
        if (conferenceRooms.findByName(newConferenceRoom.getName()) == null) {
            org.getConferenceRoomsList().add(newConferenceRoom);
            conferenceRooms.save(newConferenceRoom);
        } else if (conferenceRooms.findByName(newConferenceRoom.getName()).getName().equals(newConferenceRoom.getName())) {
            throw new SameNameException("The Conference room with name given already exist. Please choose different name.");
        }
    }

    ConferenceRoom update(ConferenceRoom newConferenceRoom, Long id) {

        return conferenceRooms.findById(id)
                .map(conferenceRoom -> {
                    conferenceRoom.setName(newConferenceRoom.getName());
                    conferenceRoom.setOptionalId(newConferenceRoom.getOptionalId());
                    conferenceRoom.setFloor(newConferenceRoom.getFloor());
                    conferenceRoom.setAvailable(newConferenceRoom.isAvailable());
                    conferenceRoom.setSittingPlaces(newConferenceRoom.getSittingPlaces());
                    conferenceRoom.setStandingPlaces(newConferenceRoom.getStandingPlaces());
                    conferenceRoom.setHangingPlaces(newConferenceRoom.getHangingPlaces());
                    return conferenceRooms.save(conferenceRoom);
                })
                .orElseGet(() -> {
                    newConferenceRoom.setId(id);
                    return conferenceRooms.save(newConferenceRoom);
                });
    }

    public void delete(Long id) {
        conferenceRooms.deleteById(id);
    }
}
