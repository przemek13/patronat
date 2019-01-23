package com.przemek.patronage.Equipment;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomDTO;

import java.io.IOException;

public class EquipmentDTOConferenceRoomDTOSerializer extends StdSerializer<ConferenceRoomDTO> {

    public EquipmentDTOConferenceRoomDTOSerializer() {
        this(null);
    }

    protected EquipmentDTOConferenceRoomDTOSerializer(Class<ConferenceRoomDTO> t) {
        super(t);
    }

    @Override
    public void serialize(ConferenceRoomDTO value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getId());
    }
}