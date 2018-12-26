package com.przemek.patronage.ConferenceRoom;

import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Organization.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class ConferenceRoomController {

    private ConferenceRoomService service;

    @Autowired
    public ConferenceRoomController(ConferenceRoomService service) {
        this.service = Objects.requireNonNull(service, "must be defined.");
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ConferenceRoom>> getConferenceRoom() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/rooms")
    ResponseEntity addConferenceRoom (@RequestBody ConferenceRoom newConferenceRoom) {
        service.save(newConferenceRoom);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/rooms/{id}")
    ResponseEntity updateConferenceRoom(@RequestBody ConferenceRoom newConferenceRoom, @PathVariable Long id) {
        return ResponseEntity.ok(service.update(newConferenceRoom, id));
    }

    @DeleteMapping("/rooms/{id}")
    ResponseEntity deleteConferenceRoom(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
