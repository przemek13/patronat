package com.przemek.patronage.Reservation;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomDTO;
import org.springframework.stereotype.Component;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationDTO {
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
    @JsonSerialize(using = ReservationDTOConferenceRoomDTOSerializer.class)
    @ManyToOne(cascade = CascadeType.ALL)
    private ConferenceRoomDTO conferenceRoom;

    public ReservationDTO() {
    }

    public ReservationDTO(@NotBlank @Size(min = 2, max = 20, message = "Reservation name should have minimum 2 and maximum 20 characters.") String reservingId,
                          @NotNull @Future String reservationStart,
                          @NotNull @Future String reservationEnd,
                          ConferenceRoomDTO conferenceRoom) {
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

    public ConferenceRoomDTO getConferenceRoomDTO() {
        return conferenceRoom;
    }

    public void setConferenceRoomDTO(ConferenceRoomDTO conferenceRoom) {
        this.conferenceRoom = conferenceRoom;
    }
}
