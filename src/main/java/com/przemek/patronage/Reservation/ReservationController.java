package com.przemek.patronage.Reservation;

import com.przemek.patronage.Exceptions.RoomReservedException;
import com.przemek.patronage.Mapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class ReservationController {

    private final ReservationService service;

    private final Mapper mapper;

    protected ReservationController(ReservationService service, Mapper mapper) {
        this.service = Objects.requireNonNull(service, "must be defined.");
        this.mapper = Objects.requireNonNull(mapper, "must be defined.");
    }

    @GetMapping("/reservations")
    private List<ReservationDTO> getAllReservations() {
        return service.findAll().stream()
                .map(mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/reservations/{orgId}/{roomId}")
    private List<ReservationDTO> getReservations(@PathVariable Long orgId, @PathVariable Long roomId) {
        return service.findForConcreteConferenceRoom(orgId, roomId).stream()
                .map(mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/reservations/{id}")
    private ReservationDTO addReservation(@Valid @RequestBody ReservationDTO newReservationDTO, @PathVariable Long id) throws RoomReservedException, ParseException {
        var newReservation = mapper.convertToEntity(newReservationDTO);
        service.save(newReservation, id);
        newReservationDTO = mapper.convertToDTO(newReservation);
        return newReservationDTO;
    }

    @PutMapping("/reservations/{id}")
    private ReservationDTO updateReservation(@Valid @RequestBody ReservationDTO newReservationDTO, @PathVariable Long id) throws ParseException {
        var newReservation = mapper.convertToEntity(newReservationDTO);
        service.update(newReservation, id);
        newReservationDTO = mapper.convertToDTO(newReservation);
        return newReservationDTO;
    }

    @DeleteMapping("/reservations/{id}")
    private void deleteReservation(@PathVariable Long id) {
        service.delete(id);
    }
}
