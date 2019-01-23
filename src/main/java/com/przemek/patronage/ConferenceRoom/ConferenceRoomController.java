package com.przemek.patronage.ConferenceRoom;

import com.przemek.patronage.Exceptions.NoSuchIdException;
import com.przemek.patronage.Exceptions.SameNameException;
import com.przemek.patronage.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class ConferenceRoomController {

    private ConferenceRoomService service;

    private Mapper mapper;

    @Autowired
    public ConferenceRoomController(ConferenceRoomService service, Mapper mapper) {
        this.service = Objects.requireNonNull(service, "must be defined.");
        this.mapper = Objects.requireNonNull(mapper, "must be defined.");
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ConferenceRoomDTO>> getConferenceRooms() {
        return ResponseEntity.ok(service.findAll().stream()
                .map(conferenceRoom -> mapper.convertToDTO(conferenceRoom))
                .collect(Collectors.toList()));
    }

    @PostMapping("/rooms/{id}")
    public ResponseEntity<ConferenceRoomDTO> addConferenceRoom(@Valid @RequestBody ConferenceRoomDTO newConferenceRoomDTO, @PathVariable Long id) throws SameNameException, NoSuchIdException, ParseException {
        var newConferenceRoom = mapper.convertToEntity(newConferenceRoomDTO);
        service.save(newConferenceRoom, id);
        newConferenceRoomDTO = mapper.convertToDTO(newConferenceRoom);
        return ResponseEntity.ok(newConferenceRoomDTO);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<ConferenceRoomDTO> updateConferenceRoom(@Valid @RequestBody ConferenceRoomDTO newConferenceRoomDTO, @PathVariable Long id) throws ParseException {
        var newConferenceRoom = mapper.convertToEntity(newConferenceRoomDTO);
        service.update(newConferenceRoom, id);
        newConferenceRoomDTO = mapper.convertToDTO(newConferenceRoom);
        return ResponseEntity.ok(newConferenceRoomDTO);
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity deleteConferenceRoom(@PathVariable Long id) throws NoSuchIdException {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
