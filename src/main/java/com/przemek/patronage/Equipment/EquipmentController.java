package com.przemek.patronage.Equipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/equipment")
    public ResponseEntity <Equipment> addEquipment(@RequestBody Equipment newEquipment) {
        service.save(newEquipment);
        return ResponseEntity.ok(newEquipment);
    }

    @PutMapping("/equipment/{id}")
    public ResponseEntity updateEquipment(@RequestBody Equipment newEquipment, @PathVariable Long id) {
        return ResponseEntity.ok(service.update(newEquipment, id));
    }

    @DeleteMapping("/equipment/{id}")
    public ResponseEntity deleteEquipment(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}