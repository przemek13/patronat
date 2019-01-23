package com.przemek.patronage.ConferenceRoom;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomSerializers.*;
import com.przemek.patronage.Equipment.EquipmentDTO;
import com.przemek.patronage.Organization.OrganizationDTO;
import com.przemek.patronage.Reservation.ReservationDTO;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Component
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConferenceRoomDTO {
    private @Id
    @GeneratedValue
    Long id;
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
    @JsonSerialize(using = ConferenceRoomDTOReservationDTOSerializer.class)
    @OneToMany(cascade = CascadeType.ALL)
    private List<ReservationDTO> reservationsList;
    @JsonSerialize(using = ConferenceRoomDTOOrganizationDTOSerializer.class)
    @ManyToOne(cascade = CascadeType.ALL)
    private OrganizationDTO organization;
    @Nullable
    @JsonSerialize(using = ConferenceRoomDTOEquipmentDTOSerializer.class)
    @OneToOne(cascade = CascadeType.ALL)
    private EquipmentDTO equipment;

    public ConferenceRoomDTO() {
    }

    public ConferenceRoomDTO(@NotBlank @Size(min = 2, max = 20, message = "Conference room name should have minimum 2 and maximum 20 characters.") String name,
                             @Nullable @Size(min = 2, max = 20, message = "Organization name should have minimum 2 and maximum 20 characters.") String optionalId,
                             @Min(0) @Max(10) int floor, boolean available,
                             @NotNull int sittingAndStandingPlaces,
                             @Nullable int lyingPlaces,
                             @Nullable int hangingPlaces,
                             List<ReservationDTO> reservationsList,
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
        this.reservationsList = reservationsList;
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

    public List<ReservationDTO> getReservationsList() {
        return reservationsList;
    }

    public void setReservationsList(List<ReservationDTO> reservationsList) {
        this.reservationsList = reservationsList;
    }

    public OrganizationDTO getOrganizationDTO() {
        return organization;
    }

    public void setOrganizationDTO(OrganizationDTO organizationDTO) {
        this.organization = organization;
    }

    @Nullable
    public EquipmentDTO getEquipmentDTO() {
        return equipment;
    }

    public void setEquipmentDTO(@Nullable EquipmentDTO equipment) {
        this.equipment = equipment;
    }
}