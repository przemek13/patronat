package com.przemek.patronage.Equipment;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomDTO;

import java.io.IOException;

public class EquipmentConferenceRoomSerializer extends StdSerializer<ConferenceRoomDTO> {

    public EquipmentConferenceRoomSerializer() {
        this(null);
    }

    protected EquipmentConferenceRoomSerializer(Class<ConferenceRoomDTO> t) {
        super(t);
    }

    @Override
    public void serialize(ConferenceRoomDTO value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getId());
    }
}