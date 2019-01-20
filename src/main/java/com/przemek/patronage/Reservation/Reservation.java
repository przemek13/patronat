package com.przemek.patronage.Reservation;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.przemek.patronage.ConferenceRoom.ConferenceRoom;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reservation {
    private @Id
    @GeneratedValue
    Long id;
    @NotBlank
    @Size(min = 2, max = 20, message = "Reservation name should have minimum 2 and maximum 20 characters.")
    private String reservingId;
    @NotNull
    @Future
    private LocalDateTime reservationStart;
    @NotNull
    @Future
    private LocalDateTime reservationEnd;
    @JsonSerialize(using = ReservationConferenceRoomSerializer.class)
    @ManyToOne(cascade = CascadeType.ALL)
    private ConferenceRoom conferenceRoom;

    public Reservation() {
    }

    public Reservation(@NotBlank @Size(min = 2, max = 20, message = "Reservation name should have minimum 2 and maximum 20 characters.") String reservingId,
                       @NotNull @Future String reservationStart, @NotNull @Future String reservationEnd, ConferenceRoom conferenceRoom) {
        this.reservingId = reservingId;
        this.reservationStart = LocalDateTime.parse(reservationStart).truncatedTo(ChronoUnit.MINUTES);
        this.reservationEnd = LocalDateTime.parse(reservationEnd).truncatedTo(ChronoUnit.MINUTES);
        this.conferenceRoom = conferenceRoom;
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