package com.przemek.patronage.ConferenceRoom;

import org.springframework.stereotype.Component;

@Component
public class ConferenceRoomDataChange {

    public void setNewData(ConferenceRoom conferenceRoom, ConferenceRoom newConferenceRoom) {
        conferenceRoom.setName(newConferenceRoom.getName());
        conferenceRoom.setOptionalId(newConferenceRoom.getOptionalId());
        conferenceRoom.setFloor(newConferenceRoom.getFloor());
        conferenceRoom.setAvailable(newConferenceRoom.isAvailable());
        conferenceRoom.setSittingAndStandingPlaces(newConferenceRoom.getSittingAndStandingPlaces());
        conferenceRoom.setLyingPlaces(newConferenceRoom.getLyingPlaces());
        conferenceRoom.setHangingPlaces(newConferenceRoom.getHangingPlaces());
        conferenceRoom.setReservations(newConferenceRoom.getReservations());
        conferenceRoom.setEquipment(newConferenceRoom.getEquipment());
    }
}
