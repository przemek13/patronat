package com.przemek.patronage.Reservation.ReservationSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomDTO;

import java.io.IOException;

public class ReservationConferenceRoomSerializer extends StdSerializer<ConferenceRoomDTO> {

    public ReservationConferenceRoomSerializer() {
        this(null);
    }

    protected ReservationConferenceRoomSerializer(Class<ConferenceRoomDTO> t) {
        super(t);
    }

    @Override
    public void serialize(ConferenceRoomDTO value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getId());
    }
}
