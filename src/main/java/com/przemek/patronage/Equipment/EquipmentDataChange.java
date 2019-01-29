package com.przemek.patronage.Equipment;

import org.springframework.stereotype.Component;

@Component
public class EquipmentDataChange {

    public void setNewData(Equipment equipment, Equipment newEquipment) {
        equipment.setProjectorName(newEquipment.getProjectorName());
        equipment.setPhone(newEquipment.isPhone());
        equipment.setInternalNumber(newEquipment.getInternalNumber());
        equipment.setExternalNumber(newEquipment.getExternalNumber());
        equipment.setConnections(newEquipment.getConnections());
    }
}
