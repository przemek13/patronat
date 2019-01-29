package com.przemek.patronage.Reservation;

import org.springframework.stereotype.Component;

@Component
public class ReservationDataChange {

    public void setNewData(Reservation reservation, Reservation newReservation) {
        reservation.setReservingId(newReservation.getReservingId());
        reservation.setReservationStart(newReservation.getReservationStart());
        reservation.setReservationEnd(newReservation.getReservationEnd());
    }
}
