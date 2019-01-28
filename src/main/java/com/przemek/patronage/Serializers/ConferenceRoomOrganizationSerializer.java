package com.przemek.patronage.Serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.przemek.patronage.Organization.OrganizationDTO;

import java.io.IOException;

public class ConferenceRoomOrganizationSerializer extends StdSerializer<OrganizationDTO> {

    public ConferenceRoomOrganizationSerializer() {
        this(null);
    }

    protected ConferenceRoomOrganizationSerializer(Class<OrganizationDTO> t) {
        super(t);
    }

    @Override
    public void serialize(OrganizationDTO value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getId());
    }
}
