package com.przemek.patronage.Organization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.przemek.patronage.ConferenceRoom.ConferenceRoom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrganizationConferenceRoomSerializer extends StdSerializer<List<ConferenceRoom>> {

    public OrganizationConferenceRoomSerializer() {
        this(null);
    }

    protected OrganizationConferenceRoomSerializer(Class<List<ConferenceRoom>> t) {
        super(t);
    }

    @Override
    public void serialize(List<ConferenceRoom> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        List<Long> ids = new ArrayList<>();
        for (ConferenceRoom item : value) {
            ids.add(item.getId());
        }
        gen.writeObject(ids);
    }
}
