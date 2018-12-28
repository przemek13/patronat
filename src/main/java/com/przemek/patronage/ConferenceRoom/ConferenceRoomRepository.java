package com.przemek.patronage.ConferenceRoom;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConferenceRoomRepository extends JpaRepository <ConferenceRoom, Long> {
    ConferenceRoom findByName (String name);
    ConferenceRoom findByOptionalId (String optionalId);
}
