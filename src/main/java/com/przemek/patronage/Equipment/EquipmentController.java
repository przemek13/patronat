package com.przemek.patronage.Equipment;

import com.przemek.patronage.Exceptions.NoSuchIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
public class EquipmentController {

    private EquipmentService service;

    @Autowired
    public EquipmentController(EquipmentService service) {
        this.service = Objects.requireNonNull(service, "must be defined.");
    }

    @GetMapping("/equipment")
    public ResponseEntity<List<Equipment>> getEquipment() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/equipment/{id}")
    public ResponseEntity<Equipment> addEquipment(@Valid @RequestBody Equipment newEquipment, @PathVariable Long id) throws NoSuchIdException {
        service.save(newEquipment, id);
        return ResponseEntity.ok(newEquipment);
    }

    @PutMapping("/equipment/{id}")
    public ResponseEntity updateEquipment(@Valid @RequestBody Equipment newEquipment, @PathVariable Long id) {
        return ResponseEntity.ok(service.update(newEquipment, id));
    }

    @DeleteMapping("/equipment/{id}")
    public ResponseEntity deleteEquipment(@PathVariable Long id) throws NoSuchIdException {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}