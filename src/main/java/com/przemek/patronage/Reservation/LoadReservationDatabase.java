package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.Organization.Organization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadReservationDatabase {

    @Bean
    public CommandLineRunner initReservationDatabase(ReservationRepository reservations) {
        return args -> {
            log.info("Preloading " + reservations.save(new Reservation("Reserving 1", "2019-03-23T16:05:03", "2019-03-23T17:00:04",new ConferenceRoom("Conference Room 2", 5, true, 5, new Organization("Organization 3")))));
        };
    }
}
