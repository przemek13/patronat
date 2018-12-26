package com.przemek.patronage.ConferenceRoom;

import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Organization.OrganizationRepository;
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
            log.info("Preloading " + conferenceRooms.save(new ConferenceRoom("Conference room 1", 10,true,10,10,10, new Organization("Organization 3"))));
            log.info("Preloading " + conferenceRooms.save(new ConferenceRoom("Conference room 2", 1,true,20,20,20,new Organization("Organization 4"))));
        };
    }
}