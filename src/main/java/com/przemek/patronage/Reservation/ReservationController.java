package com.przemek.patronage.Reservation;

import com.przemek.patronage.Exceptions.NoSuchIdException;
import com.przemek.patronage.Exceptions.RoomReservedException;
import com.przemek.patronage.Exceptions.StartAfterEndException;
import com.przemek.patronage.Exceptions.WrongDurationException;
import com.przemek.patronage.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class ReservationController {

    private ReservationService service;

    private Mapper mapper;

    @Autowired
    public ReservationController(ReservationService service, Mapper mapper) {
        this.service = Objects.requireNonNull(service, "must be defined.");
        this.mapper = Objects.requireNonNull(mapper, "must be defined.");
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
//        return ResponseEntity.ok(service.findAll());
        return ResponseEntity.ok(service.findAll().stream()
                .map(reservation -> mapper.convertToDTO(reservation))
                .collect(Collectors.toList()));
    }

    @GetMapping("/reservations/{orgId}/{roomId}")
    public ResponseEntity<List<Reservation>> getReservations(@PathVariable Long orgId, @PathVariable Long roomId) throws NoSuchIdException {
        return ResponseEntity.ok(service.findForOne(orgId, roomId));
    }

    @PostMapping("/reservations/{id}")
    public ResponseEntity<ReservationDTO> addReservation(@Valid @RequestBody ReservationDTO newReservationDTO, @PathVariable Long id) throws NoSuchIdException, WrongDurationException, StartAfterEndException, RoomReservedException, IOException, ParseException {
        var newReservation = mapper.convertToEntity(newReservationDTO);
        service.save(newReservation, id);
        newReservationDTO = mapper.convertToDTO(newReservation);
        return ResponseEntity.ok(newReservationDTO);
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity updateReservation(@Valid @RequestBody Reservation newReservation, @PathVariable Long id) {
        return ResponseEntity.ok(service.update(newReservation, id));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) throws NoSuchIdException {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
