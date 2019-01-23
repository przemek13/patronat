package com.przemek.patronage.Equipment;

import com.przemek.patronage.Exceptions.NoSuchIdException;
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
public class EquipmentController {

    private EquipmentService service;

    private Mapper mapper;

    @Autowired
    public EquipmentController(EquipmentService service, Mapper mapper) {
        this.service = Objects.requireNonNull(service, "must be defined.");
        this.mapper = Objects.requireNonNull(mapper, "must be defined.");
    }

    @GetMapping("/equipment")
    public ResponseEntity<List<EquipmentDTO>> getEquipment() {
        return ResponseEntity.ok(service.findAll().stream()
                .map(equipment -> mapper.convertToDTO(equipment))
                .collect(Collectors.toList()));
    }

    @PostMapping("/equipment/{id}")
    public ResponseEntity<EquipmentDTO> addEquipment(@Valid @RequestBody EquipmentDTO newEquipmentDTO, @PathVariable Long id) throws NoSuchIdException, ParseException {
        var newEquipment = mapper.convertToEntity(newEquipmentDTO);
        service.save(newEquipment, id);
        newEquipmentDTO = mapper.convertToDTO(newEquipment);
        return ResponseEntity.ok(newEquipmentDTO);
    }

    @PutMapping("/equipment/{id}")
    public ResponseEntity<EquipmentDTO> updateEquipment(@Valid @RequestBody EquipmentDTO newEquipmentDTO, @PathVariable Long id) throws ParseException {
        var newEquipment = mapper.convertToEntity(newEquipmentDTO);
        service.update(newEquipment, id);
        newEquipmentDTO = mapper.convertToDTO(newEquipment);
        return ResponseEntity.ok(newEquipmentDTO);
    }

    @DeleteMapping("/equipment/{id}")
    public ResponseEntity deleteEquipment(@PathVariable Long id) throws NoSuchIdException {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}