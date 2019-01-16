package com.przemek.patronage.ConferenceRoom.ConferenceRoomSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.przemek.patronage.Equipment.Equipment;
import com.przemek.patronage.Organization.Organization;

import java.io.IOException;

public class ConferenceRoomEquipmentSerializer extends StdSerializer<Equipment> {

    public ConferenceRoomEquipmentSerializer() {
        this(null);
    }

    protected ConferenceRoomEquipmentSerializer(Class<Equipment> t) {
        super(t);
    }

    @Override
    public void serialize(Equipment value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getId());
    }
}
