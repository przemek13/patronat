package com.przemek.patronage.ConferenceRoom;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.przemek.patronage.Reservation.Reservation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConferenceRoomReservationSerializer extends StdSerializer<List<Reservation>> {

    public ConferenceRoomReservationSerializer() {
        this(null);
    }

    protected ConferenceRoomReservationSerializer(Class<List<Reservation>> t) {
        super(t);
    }

    @Override
    public void serialize(List<Reservation> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        List<Long> ids = new ArrayList<>();
        for (Reservation item : value) {
            ids.add(item.getId());
        }
        gen.writeObject(ids);
    }
}
