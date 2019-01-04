package com.przemek.patronage.ConferenceRoom;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.przemek.patronage.Organization.Organization;

import java.io.IOException;

public class ConferenceRoomOrganizationSerializer extends StdSerializer<Organization> {

    public ConferenceRoomOrganizationSerializer() {
        this(null);
    }

    protected ConferenceRoomOrganizationSerializer(Class<Organization> t) {
        super(t);
    }

    @Override
    public void serialize(Organization value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getId());
    }
}
