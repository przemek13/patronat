package com.przemek.patronage.Reservation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.przemek.patronage.ConferenceRoom.ConferenceRoom;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservation {
    private @Id
    @GeneratedValue
    Long id;
    private String reservingId;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime reservationStart;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime reservationEnd;
    @ManyToOne(cascade = CascadeType.ALL)
    private ConferenceRoom conferenceRoom;

    public Reservation() {
    }

    public Reservation(String reservingId, LocalDateTime reservationStart, LocalDateTime reservationEnd, ConferenceRoom conferenceRoom) {
        this.reservingId = reservingId;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
        this.conferenceRoom = conferenceRoom;
        //truncatedTo(ChronoUnit.MINUTES)
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReservingId() {
        return reservingId;
    }

    public void setReservingId(String reservingId) {
        this.reservingId = reservingId;
    }

    public LocalDateTime getReservationStart() {
        return reservationStart;
    }

    public void setReservationStart(LocalDateTime reservationStart) {
        this.reservationStart = reservationStart;
    }

    public LocalDateTime getReservationEnd() {
        return reservationEnd;
    }

    public void setReservationEnd(LocalDateTime reservationEnd) {
        this.reservationEnd = reservationEnd;
    }

    public ConferenceRoom getConferenceRoom() {
        return conferenceRoom;
    }

    public void setConferenceRoom(ConferenceRoom conferenceRoom) {
        this.conferenceRoom = conferenceRoom;
    }
}