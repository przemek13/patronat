package com.przemek.patronage.Reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomDTO;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Component
public class ReservationDTO implements Serializable {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 20, message = "Reservation name should have minimum 2 and maximum 20 characters.")
    private String reservingId;
    @NotNull
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime reservationStart;
    @NotNull
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime reservationEnd;
    @JsonSerialize(using = ReservationConferenceRoomSerializer.class)
    private ConferenceRoomDTO conferenceRoom;

    public ReservationDTO() {
    }

    public ReservationDTO(@NotBlank @Size(min = 2, max = 20, message = "Reservation name should have minimum 2 and maximum 20 characters.") String reservingId,
                          @NotNull @Future LocalDateTime reservationStart,
                          @NotNull @Future LocalDateTime reservationEnd,
                          ConferenceRoomDTO conferenceRoom) {
        this.reservingId = reservingId;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
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

    public ConferenceRoomDTO getConferenceRoom() {
        return conferenceRoom;
    }

    public void setConferenceRoom(ConferenceRoomDTO conferenceRoom) {
        this.conferenceRoom = conferenceRoom;
    }
}
