package com.przemek.patronage.ConferenceRoom;

import com.przemek.patronage.Organization.Organization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadConferenceRoomDatabase {

    @Bean
    CommandLineRunner initConferenceRoomDatabase(ConferenceRoomRepository conferenceRooms) {
        return args -> {
            log.info("Preloading " + conferenceRooms.save(new ConferenceRoom("BLUE", 10, true, 10, 10, 10, new Organization("Organization 2"))));
        };
    }
}