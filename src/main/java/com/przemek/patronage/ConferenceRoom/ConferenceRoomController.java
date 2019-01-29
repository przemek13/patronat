package com.przemek.patronage.ConferenceRoom;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
public class ConferenceRoomController {

    private final ConferenceRoomService conferenceRoomService;

    private ConferenceRoomController(ConferenceRoomService service) {
        this.conferenceRoomService = Objects.requireNonNull(service, "must be defined.");
    }

    @GetMapping("/rooms")
    private List<ConferenceRoomDTO> getConferenceRooms() {
        return conferenceRoomService.findAll();
    }

    @PostMapping("/rooms/{id}")
    private ConferenceRoomDTO addConferenceRoom(@Valid @RequestBody ConferenceRoomDTO newConferenceRoomDTO, @PathVariable Long id) {
        return conferenceRoomService.save(newConferenceRoomDTO, id);
    }

    @PutMapping("/rooms/{id}")
    private ConferenceRoomDTO updateConferenceRoom(@Valid @RequestBody ConferenceRoomDTO newConferenceRoomDTO, @PathVariable Long id) {
        return conferenceRoomService.update(newConferenceRoomDTO, id);
    }

    @DeleteMapping("/rooms/{id}")
    private void deleteConferenceRoom(@PathVariable Long id) {
        conferenceRoomService.delete(id);
    }
}
