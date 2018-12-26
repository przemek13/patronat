package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@Slf4j
public class LoadReservationDatabase {

    @Bean
    CommandLineRunner initReservationDatabase(ReservationRepository reservations) {
        return args -> {
            log.info("Preloading " + reservations.save(new Reservation("Reserving 1", "2018-12-23T16:15", "2018-12-23T17:00",new ConferenceRoom("BLUE", false))));
            log.info("Preloading " + reservations.save(new Reservation("Reserving 2", "2018-12-23T14:00", "2018-12-23T15:00",new ConferenceRoom("RED", false))));
        };
    }
}
