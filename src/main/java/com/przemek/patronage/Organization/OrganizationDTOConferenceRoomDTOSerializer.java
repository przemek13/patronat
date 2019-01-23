package com.przemek.patronage.Organization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrganizationDTOConferenceRoomDTOSerializer extends StdSerializer<List<ConferenceRoomDTO>> {

    public OrganizationDTOConferenceRoomDTOSerializer() {
        this(null);
    }

    protected OrganizationDTOConferenceRoomDTOSerializer(Class<List<ConferenceRoomDTO>> t) {
        super(t);
    }

    @Override
    public void serialize(List<ConferenceRoomDTO> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        List<Long> ids = new ArrayList<>();
        for (ConferenceRoomDTO item : value) {
            ids.add(item.getId());
        }
        gen.writeObject(ids);
    }
}