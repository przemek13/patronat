package com.przemek.patronage.Equipment;

import com.przemek.patronage.ConferenceRoom.ConferenceRoomRepository;
import com.przemek.patronage.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final ConferenceRoomRepository conferenceRoomRepository;
    private final Mapper mapper;
    private final EquipmentDataChange equipmentDataChange;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository, ConferenceRoomRepository conferenceRoomRepository, Mapper mapper, EquipmentDataChange equipmentDataChange) {
        this.equipmentRepository = Objects.requireNonNull(equipmentRepository, "must be defined.");
        this.conferenceRoomRepository = Objects.requireNonNull(conferenceRoomRepository, "must be defined.");
        this.mapper = Objects.requireNonNull(mapper, "must be defined.");
        this.equipmentDataChange = Objects.requireNonNull(equipmentDataChange, "must be defined.");
    }

    public List<EquipmentDTO> findAll() {
        return equipmentRepository.findAll().stream()
                .map(mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public EquipmentDTO save(EquipmentDTO newEquipmentDTO, Long id) {
        var newEquipment = mapper.convertToEntity(newEquipmentDTO);
        if (conferenceRoomRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("The Conference room with id given doesn't exist in the base.");
        }
        if (!newEquipment.isPhone()) {
            newEquipment.setInternalNumber(null);
            newEquipment.setExternalNumber(null);
            newEquipment.setConnections(null);
        }
        var room = conferenceRoomRepository.findById(id).get();
        room.setEquipment(newEquipment);
        newEquipment.setConferenceRoom(room);
        equipmentRepository.save(newEquipment);
        return mapper.convertToDTO(newEquipment);
    }

    public EquipmentDTO update(EquipmentDTO newEquipmentDTO, Long id) {
        var newEquipment = mapper.convertToEntity(newEquipmentDTO);
        return equipmentRepository.findById(id)
                .map(equipment -> {
                    equipmentDataChange.setNewData(equipment, newEquipment);
                    equipmentRepository.save(equipment);
                    return mapper.convertToDTO(equipment);
                })
                .orElseGet(() -> {
                    throw new IllegalArgumentException("The Equipment with id given doesn't exist in the base.");
                });
    }

    public void delete(Long id) {
        if (equipmentRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("The Equipment with id given doesn't exist in the base.");
        }
        equipmentRepository.deleteById(id);
    }
}
