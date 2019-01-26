package com.przemek.patronage.ConferenceRoom;

import com.przemek.patronage.Mapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class ConferenceRoomController {

    private final ConferenceRoomService service;

    private ConferenceRoomController(ConferenceRoomService service, Mapper mapper) {
        this.service = Objects.requireNonNull(service, "must be defined.");
    }

    @GetMapping("/rooms")
    private List<ConferenceRoomDTO> getConferenceRooms() {
        return service.findAll();
    }

    @PostMapping("/rooms/{id}")
    private ConferenceRoomDTO addConferenceRoom(@Valid @RequestBody ConferenceRoomDTO newConferenceRoomDTO, @PathVariable Long id) {
        return service.save(newConferenceRoomDTO, id);
    }

    @PutMapping("/rooms/{id}")
    private ConferenceRoomDTO updateConferenceRoom(@Valid @RequestBody ConferenceRoomDTO newConferenceRoomDTO, @PathVariable Long id) {
        return service.update(newConferenceRoomDTO, id);
    }

    @DeleteMapping("/rooms/{id}")
    private void deleteConferenceRoom(@PathVariable Long id) {
        service.delete(id);
    }
}
