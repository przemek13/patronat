package com.przemek.patronage.ConferenceRoom.ConferenceRoomSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.przemek.patronage.Equipment.Equipment;
import com.przemek.patronage.Equipment.EquipmentDTO;

import java.io.IOException;

public class ConferenceRoomDTOEquipmentDTOSerializer extends StdSerializer<EquipmentDTO> {

    public ConferenceRoomDTOEquipmentDTOSerializer() {
        this(null);
    }

    protected ConferenceRoomDTOEquipmentDTOSerializer(Class<EquipmentDTO> t) {
        super(t);
    }

    @Override
    public void serialize(EquipmentDTO value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getId());
    }
}
