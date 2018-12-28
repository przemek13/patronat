package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class ReservationController {

    private ReservationService service;

    @Autowired

    public ReservationController(ReservationService service) {
        this.service = Objects.requireNonNull(service, "must be defined.");
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/reservations/{id}")
    ResponseEntity addReservation(@RequestBody Reservation newReservation, @PathVariable Long id) {
        service.save(newReservation, id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reservations/{id}")
    ResponseEntity updateReservation(@RequestBody Reservation newReservation, @PathVariable Long id) {
        return ResponseEntity.ok(service.update(newReservation, id));
    }

    @DeleteMapping("/reservations/{id}")
    ResponseEntity deleteReservation(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
