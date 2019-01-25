package com.przemek.patronage.ConferenceRoom;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomSerializers.ConferenceRoomEquipmentSerializer;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomSerializers.ConferenceRoomOrganizationSerializer;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomSerializers.ConferenceRoomReservationSerializer;
import com.przemek.patronage.Equipment.EquipmentDTO;
import com.przemek.patronage.Organization.OrganizationDTO;
import com.przemek.patronage.Reservation.ReservationDTO;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;


@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Component
public class ConferenceRoomDTO implements Serializable {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 20, message = "Conference room name should have minimum 2 and maximum 20 characters.")
    private String name;
    @Nullable
    @Size(min = 2, max = 20, message = "Conference name should have minimum 2 and maximum 20 characters.")
    private String optionalId;
    @Min(0)
    @Max(10)
    private int floor;
    private boolean available;
    @NotNull
    private int sittingAndStandingPlaces;
    @Nullable
    private int lyingPlaces;
    @Nullable
    private int hangingPlaces;
    @JsonSerialize(using = ConferenceRoomReservationSerializer.class)
    private List<ReservationDTO> reservations;
    @JsonSerialize(using = ConferenceRoomOrganizationSerializer.class)
    private OrganizationDTO organization;
    @Nullable
    @JsonSerialize(using = ConferenceRoomEquipmentSerializer.class)
    private EquipmentDTO equipment;

    public ConferenceRoomDTO() {
    }

    public ConferenceRoomDTO(@NotBlank @Size(min = 2, max = 20, message = "Conference room name should have minimum 2 and maximum 20 characters.") String name,
                             @Nullable @Size(min = 2, max = 20, message = "Organization name should have minimum 2 and maximum 20 characters.") String optionalId,
                             @Min(0) @Max(10) int floor, boolean available,
                             @NotNull int sittingAndStandingPlaces,
                             @Nullable int lyingPlaces,
                             @Nullable int hangingPlaces,
                             List<ReservationDTO> reservations,
                             OrganizationDTO organization,
                             @Nullable EquipmentDTO equipment
    ) {
        this.name = name;
        this.optionalId = optionalId;
        this.floor = floor;
        this.available = available;
        this.sittingAndStandingPlaces = sittingAndStandingPlaces;
        this.lyingPlaces = lyingPlaces;
        this.hangingPlaces = hangingPlaces;
        this.reservations = reservations;
        this.organization = organization;
        this.equipment = equipment;
    }

    public ConferenceRoomDTO(@NotBlank @Size(min = 2, max = 20, message = "Conference room name should have minimum 2 and maximum 20 characters.") String name,
                             @Min(0) @Max(10) int floor,
                             boolean available,
                             @NotNull int sittingAndStandingPlaces,
                             OrganizationDTO organization
    ) {
        this.name = name;
        this.floor = floor;
        this.available = available;
        this.sittingAndStandingPlaces = sittingAndStandingPlaces;
        this.organization = organization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getOptionalId() {
        return optionalId;
    }

    public void setOptionalId(@Nullable String optionalId) {
        this.optionalId = optionalId;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getSittingAndStandingPlaces() {
        return sittingAndStandingPlaces;
    }

    public void setSittingAndStandingPlaces(int sittingAndStandingPlaces) {
        this.sittingAndStandingPlaces = sittingAndStandingPlaces;
    }

    public int getLyingPlaces() {
        return lyingPlaces;
    }

    public void setLyingPlaces(int lyingPlaces) {
        this.lyingPlaces = lyingPlaces;
    }

    public int getHangingPlaces() {
        return hangingPlaces;
    }

    public void setHangingPlaces(int hangingPlaces) {
        this.hangingPlaces = hangingPlaces;
    }

    public List<ReservationDTO> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationDTO> reservations) {
        this.reservations = reservations;
    }

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
    }

    @Nullable
    public EquipmentDTO getEquipment() {
        return equipment;
    }

    public void setEquipment(@Nullable EquipmentDTO equipment) {
        this.equipment = equipment;
    }
}