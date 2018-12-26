package com.przemek.patronage.ConferenceRoom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ConferenceRoomService {
    private ConferenceRoomRepository conferenceRooms;

    @Autowired
    public ConferenceRoomService(ConferenceRoomRepository conferenceRooms) {
        this.conferenceRooms = Objects.requireNonNull(conferenceRooms, "must be defined.");
    }

    public List<ConferenceRoom> findAll() {
        return conferenceRooms.findAll();
    }

    public void save(ConferenceRoom newConferenceRoom) {
        conferenceRooms.save(newConferenceRoom);
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
