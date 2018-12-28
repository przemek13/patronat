package com.przemek.patronage.Reservation;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Data
@Entity
public class Reservation {

    private @Id
    @GeneratedValue
    Long id;
    @NotBlank
    @Size(min = 2, max = 20, message = "Reservation name should have minimum 2 and maximum 20 characters.")
    @Column(unique = true)
    private String reservingId;
    LocalDateTime reservationStart;
    LocalDateTime reservationEnd;

    @ManyToOne(cascade = CascadeType.ALL)
    ConferenceRoom conferenceRoom;

    public Reservation() {
    }

    public Reservation(@NotBlank @Size(min = 2, max = 20, message = "Reservation name should have minimum 2 and maximum 20 characters.") String reservingId,
                       String reservationStart, String reservationEnd, ConferenceRoom conferenceRoom) {
        this.reservingId = reservingId;
        this.reservationStart = LocalDateTime.parse(reservationStart);
        this.reservationEnd = LocalDateTime.parse(reservationEnd);
        this.conferenceRoom = conferenceRoom;
    }
}
