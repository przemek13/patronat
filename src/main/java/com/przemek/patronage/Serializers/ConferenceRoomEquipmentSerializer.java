package com.przemek.patronage.Serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.przemek.patronage.Equipment.EquipmentDTO;

import java.io.IOException;

public class ConferenceRoomEquipmentSerializer extends StdSerializer<EquipmentDTO> {

    public ConferenceRoomEquipmentSerializer() {
        this(null);
    }

    protected ConferenceRoomEquipmentSerializer(Class<EquipmentDTO> t) {
        super(t);
    }

    @Override
    public void serialize(EquipmentDTO value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getId());
    }
}
