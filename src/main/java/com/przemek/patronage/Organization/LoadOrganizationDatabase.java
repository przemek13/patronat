package com.przemek.patronage.Organization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadOrganizationDatabase {

    @Bean
    CommandLineRunner initOrganizationDatabase(OrganizationRepository organizations) {
        return args -> {
            log.info("Preloading " + organizations.save(new Organization("Organization 1")));
        };
    }
}