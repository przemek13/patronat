package com.przemek.patronage.Reservation;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
public class ReservationController {

    private final ReservationService service;

    protected ReservationController(ReservationService service) {
        this.service = Objects.requireNonNull(service, "must be defined.");
    }

    @GetMapping("/reservations")
    private List<ReservationDTO> getAllReservations() {
        return service.findAll();
    }

    @GetMapping("/reservations/{orgId}/{roomId}")
    private List<ReservationDTO> getReservations(@PathVariable Long orgId, @PathVariable Long roomId) {
        return service.findForConcreteConferenceRoom(orgId, roomId);
    }

    @PostMapping("/reservations/{id}")
    private ReservationDTO addReservation(@Valid @RequestBody ReservationDTO newReservationDTO, @PathVariable Long id) {
        return service.save(newReservationDTO, id);
    }

    @PutMapping("/reservations/{id}")
    private ReservationDTO updateReservation(@Valid @RequestBody ReservationDTO newReservationDTO, @PathVariable Long id) {
        return service.update(newReservationDTO, id);
    }

    @DeleteMapping("/reservations/{id}")
    private void deleteReservation(@PathVariable Long id) {
        service.delete(id);
    }
}
