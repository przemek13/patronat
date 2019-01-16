package com.przemek.patronage.ConferenceRoom;

import com.przemek.patronage.Exceptions.NoSuchIdException;
import com.przemek.patronage.Exceptions.SameNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class ConferenceRoomController {

    private ConferenceRoomService service;

    @Autowired
    public ConferenceRoomController(ConferenceRoomService service) {
        this.service = Objects.requireNonNull(service, "must be defined.");
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ConferenceRoom>> getConferenceRooms() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/rooms/{id}")
    public ResponseEntity <ConferenceRoom> addConferenceRoom(@RequestBody ConferenceRoom newConferenceRoom, @PathVariable Long id) throws SameNameException, NoSuchIdException {
        service.save(newConferenceRoom, id);
        return ResponseEntity.ok(newConferenceRoom);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity updateConferenceRoom(@RequestBody ConferenceRoom newConferenceRoom, @PathVariable Long id) {
        return ResponseEntity.ok(service.update(newConferenceRoom, id));
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity deleteConferenceRoom(@PathVariable Long id) throws NoSuchIdException {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
