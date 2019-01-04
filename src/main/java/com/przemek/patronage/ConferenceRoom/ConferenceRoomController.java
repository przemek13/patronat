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
    private final ConferenceRoomAssembler assembler;

    @Autowired
    public ConferenceRoomController(ConferenceRoomService service, ConferenceRoomAssembler assembler) {
        this.service = Objects.requireNonNull(service, "must be defined.");
        this.assembler = assembler;
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ConferenceRoom>> getConferenceRooms() {
        return ResponseEntity.ok(service.findAll());
    }

//    public Resources<Resource<ConferenceRoom>> getConferenceRooms() {
//        List<Resource<ConferenceRoom>> rooms = service.findAll().stream()
//                .map(assembler::toResource)
//                .collect(Collectors.toList());
//        return new Resources<>(rooms,
//                linkTo(methodOn(ConferenceRoomController.class).getConferenceRoom()).withSelfRel());
//    }

    @PostMapping("/rooms/{id}")
    public ResponseEntity addConferenceRoom(@RequestBody ConferenceRoom newConferenceRoom, @PathVariable Long id) throws SameNameException, NoSuchIdException {
        service.save(newConferenceRoom, id);
        return ResponseEntity.ok().build();
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
