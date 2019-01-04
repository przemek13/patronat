package com.przemek.patronage.Reservation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.przemek.patronage.ConferenceRoom.ConferenceRoom;

import java.io.IOException;

public class ReservationConferenceRoomSerializer extends StdSerializer<ConferenceRoom> {

    public ReservationConferenceRoomSerializer() {
        this(null);
    }

    protected ReservationConferenceRoomSerializer(Class<ConferenceRoom> t) {
        super(t);
    }

    @Override
    public void serialize(ConferenceRoom value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getId());
    }
}
