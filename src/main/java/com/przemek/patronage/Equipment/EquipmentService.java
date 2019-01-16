package com.przemek.patronage.Equipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EquipmentService {
    private EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = Objects.requireNonNull(equipmentRepository, "must be defined.");
    }

    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    public void save(Equipment newEquipment) {
        if (!newEquipment.isPhone()) {
            newEquipment.setInternalNumber(null);
            newEquipment.setExternalNumber(null);
            newEquipment.setConnections(null);
        }
        equipmentRepository.save(newEquipment);
    }

    public Equipment update(Equipment newEquipment, Long id) {

        return equipmentRepository.findById(id)
                .map(equipment -> {
                    equipment.setProjectorName(newEquipment.getProjectorName());
                    equipment.setPhone(newEquipment.isPhone());
                    if (equipment.isPhone()) {
                        equipment.setInternalNumber(newEquipment.getInternalNumber());
                        equipment.setExternalNumber(newEquipment.getExternalNumber());
                        equipment.setConnections(newEquipment.getConnections());
                    }
                    return equipmentRepository.save(equipment);
                })
                .orElseGet(() -> {
                    newEquipment.setId(id);
                    return equipmentRepository.save(newEquipment);
                });
    }

    public void delete(Long id) {
        equipmentRepository.deleteById(id);
    }
}
