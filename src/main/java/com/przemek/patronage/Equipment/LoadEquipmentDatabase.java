package com.przemek.patronage.Equipment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadEquipmentDatabase {

    @Bean
    CommandLineRunner initEquipmentDatabase(EquipmentRepository equipmentRepository) {
        return args -> {
            log.info("Preloading " + equipmentRepository.save(new Equipment("BenQ", true, 1, "+12 123456789", InterfaceConnections.BLUETOOTH)));
            log.info("Preloading " + equipmentRepository.save(new Equipment("Hitachi", false)));
        };
    }
}
