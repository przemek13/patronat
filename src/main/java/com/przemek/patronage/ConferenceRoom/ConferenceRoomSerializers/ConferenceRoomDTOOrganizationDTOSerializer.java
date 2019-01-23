package com.przemek.patronage.ConferenceRoom.ConferenceRoomSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Organization.OrganizationDTO;

import java.io.IOException;

public class ConferenceRoomDTOOrganizationDTOSerializer  extends StdSerializer<OrganizationDTO> {

    public ConferenceRoomDTOOrganizationDTOSerializer() {
        this(null);
    }

    protected ConferenceRoomDTOOrganizationDTOSerializer(Class<OrganizationDTO> t) {
        super(t);
    }

    @Override
    public void serialize(OrganizationDTO value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getId());
    }
}
