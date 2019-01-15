package com.przemek.patronage.Reservation;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
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

    @Future
    private LocalDateTime reservationStart;
    @Future
    private LocalDateTime reservationEnd;

    @JsonSerialize(using = ReservationConferenceRoomSerializer.class)
    @ManyToOne(cascade = CascadeType.ALL)
    private ConferenceRoom conferenceRoom;

    public Reservation() {
    }

    public Reservation(@NotBlank @Size(min = 2, max = 20, message = "Reservation name should have minimum 2 and maximum 20 characters.") String reservingId,
                       String reservationStart, String reservationEnd, ConferenceRoom conferenceRoom) {
        this.reservingId = reservingId;
        this.reservationStart = LocalDateTime.parse(reservationStart).truncatedTo(ChronoUnit.MINUTES);
        this.reservationEnd = LocalDateTime.parse(reservationEnd).truncatedTo(ChronoUnit.MINUTES);
        this.conferenceRoom = conferenceRoom;
    }
}
