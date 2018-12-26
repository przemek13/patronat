package com.przemek.patronage.Organization;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
class LoadOrganizationDatabase {

    @Bean
    CommandLineRunner initOrganizationDatabase(OrganizationRepository organizations) {
        return args -> {
            log.info("Preloading " + organizations.save(new Organization("Organization 1")));
            log.info("Preloading " + organizations.save(new Organization("Organization 2")));
        };
    }
}