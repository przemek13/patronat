package com.przemek.patronage.Equipment;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
public class EquipmentController {

    private final EquipmentService service;

    private EquipmentController(EquipmentService service) {
        this.service = Objects.requireNonNull(service, "must be defined.");
    }

    @GetMapping("/equipment")
    private List<EquipmentDTO> getEquipment() {
        return service.findAll();
    }

    @PostMapping("/equipment/{id}")
    private EquipmentDTO addEquipment(@Valid @RequestBody EquipmentDTO newEquipmentDTO, @PathVariable Long id) {
        return service.save(newEquipmentDTO, id);
    }

    @PutMapping("/equipment/{id}")
    private EquipmentDTO updateEquipment(@Valid @RequestBody EquipmentDTO newEquipmentDTO, @PathVariable Long id) {
        return service.update(newEquipmentDTO, id);
    }

    @DeleteMapping("/equipment/{id}")
    private void deleteEquipment(@PathVariable Long id) {
        service.delete(id);
    }
}