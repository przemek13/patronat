package com.przemek.patronage.Equipment;

import com.przemek.patronage.Mapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class EquipmentController {

    private final EquipmentService service;

    private EquipmentController(EquipmentService service, Mapper mapper) {
        this.service = Objects.requireNonNull(service, "must be defined.");
    }

    @GetMapping("/equipment")
    private List<EquipmentDTO> getEquipment() {
        return service.findAll();
    }

    @PostMapping("/equipment/{id}")
    private EquipmentDTO addEquipment(@Valid @RequestBody EquipmentDTO newEquipmentDTO, @PathVariable Long id) throws ParseException {
        return service.save(newEquipmentDTO, id);
    }

    @PutMapping("/equipment/{id}")
    private EquipmentDTO updateEquipment(@Valid @RequestBody EquipmentDTO newEquipmentDTO, @PathVariable Long id) throws ParseException {
        return service.update(newEquipmentDTO, id);
    }

    @DeleteMapping("/equipment/{id}")
    private void deleteEquipment(@PathVariable Long id) {
        service.delete(id);
    }
}