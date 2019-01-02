package com.przemek.patronage.ConferenceRoom;

import com.przemek.patronage.Exceptions.NoSuchIdException;
import com.przemek.patronage.Exceptions.SameNameException;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Organization.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public void save(ConferenceRoom newConferenceRoom, Long id) throws SameNameException, NoSuchIdException {
        if (organizations.findById(id).equals(Optional.empty())) {
            throw new NoSuchIdException("The Organization with id given doesn't exist in the base.");
        }
        Organization org = organizations.findById(id).get();
        newConferenceRoom.setOrganization(org);
        if (conferenceRooms.findByName(newConferenceRoom.getName()) == null) {
            org.getConferenceRoomsList().add(newConferenceRoom);
            organizations.save(org);
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

    public void delete(Long id) throws NoSuchIdException {
        if (conferenceRooms.findById(id).equals(Optional.empty())) {
            throw new NoSuchIdException("The Conference room with id given doesn't exist in the base.");
        }
        else
        conferenceRooms.deleteById(id);
    }
}
