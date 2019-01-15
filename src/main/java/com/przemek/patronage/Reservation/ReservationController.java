package com.przemek.patronage.Reservation;

import com.przemek.patronage.Exceptions.NoSuchIdException;
import com.przemek.patronage.Exceptions.RoomReservedException;
import com.przemek.patronage.Exceptions.StartAfterEndException;
import com.przemek.patronage.Exceptions.WrongDurationException;
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
    public ResponseEntity <Reservation> addReservation(@RequestBody Reservation newReservation, @PathVariable Long id) throws NoSuchIdException, WrongDurationException, StartAfterEndException, RoomReservedException {
        service.save(newReservation, id);
        return ResponseEntity.ok(newReservation);
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity updateReservation(@RequestBody Reservation newReservation, @PathVariable Long id) {
        return ResponseEntity.ok(service.update(newReservation, id));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) throws NoSuchIdException {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
