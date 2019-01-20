package com.przemek.patronage.Equipment;

import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Exceptions.NoSuchIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EquipmentService {
    private EquipmentRepository equipmentRepository;
    private ConferenceRoomRepository conferenceRooms;

    public EquipmentService() {

    }

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository, ConferenceRoomRepository conferenceRooms) {
        this.equipmentRepository = Objects.requireNonNull(equipmentRepository, "must be defined.");
        this.conferenceRooms = Objects.requireNonNull(conferenceRooms, "must be defined.");
    }

    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    public void save(Equipment newEquipment, Long id) throws NoSuchIdException {
        if (conferenceRooms.findById(id).equals(Optional.empty())) {
            throw new NoSuchIdException("The Conference room with id given doesn't exist in the base.");
        }
        if (!newEquipment.isPhone()) {
            newEquipment.setInternalNumber(null);
            newEquipment.setExternalNumber(null);
            newEquipment.setConnections(null);
        }
        var room = conferenceRooms.findById(id).get();
        room.setEquipment(newEquipment);
        newEquipment.setConferenceroom(room);
        equipmentRepository.save(newEquipment);
    }

    public Equipment update(Equipment newEquipment, Long id) {

        return equipmentRepository.findById(id)
                .map(equipment -> {
                    equipment.setProjectorName(newEquipment.getProjectorName());
                    equipment.setPhone(newEquipment.isPhone());
                    equipment.setInternalNumber(newEquipment.getInternalNumber());
                    equipment.setExternalNumber(newEquipment.getExternalNumber());
                    equipment.setConnections(newEquipment.getConnections());
                    equipment.setConferenceroom(newEquipment.getConferenceroom());
                    return equipmentRepository.save(equipment);
                })
                .orElseGet(() -> {
                    newEquipment.setId(id);
                    return equipmentRepository.save(newEquipment);
                });
    }

    public void delete(Long id) throws NoSuchIdException {
        if (equipmentRepository.findById(id).equals(Optional.empty())) {
            throw new NoSuchIdException("The Organization with id given doesn't exist in the base.");
        } else
            equipmentRepository.deleteById(id);
    }
}
