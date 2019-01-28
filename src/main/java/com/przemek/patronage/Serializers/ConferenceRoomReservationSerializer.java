package com.przemek.patronage.Serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.przemek.patronage.Reservation.ReservationDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConferenceRoomReservationSerializer extends StdSerializer<List<ReservationDTO>> {

    public ConferenceRoomReservationSerializer() {
        this(null);
    }

    protected ConferenceRoomReservationSerializer(Class<List<ReservationDTO>> t) {
        super(t);
    }

    @Override
    public void serialize(List<ReservationDTO> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        List<Long> ids = new ArrayList<>();
        for (ReservationDTO item : value) {
            ids.add(item.getId());
        }
        gen.writeObject(ids);
    }
}
