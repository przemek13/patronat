package com.przemek.patronage.ConferenceRoom;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomSerializers.ConferenceRoomEquipmentSerializer;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomSerializers.ConferenceRoomOrganizationSerializer;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomSerializers.ConferenceRoomReservationSerializer;
import com.przemek.patronage.Equipment.Equipment;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Reservation.Reservation;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConferenceRoom {
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
    private int sittingAndStandingPlaces;
    @Nullable
    private int lyingPlaces;
    @Nullable
    private int hangingPlaces;
    @JsonSerialize(using = ConferenceRoomReservationSerializer.class)
    @OneToMany(cascade = CascadeType.ALL)
    private List<Reservation> reservations;
    @JsonSerialize(using = ConferenceRoomOrganizationSerializer.class)
    @ManyToOne(cascade = CascadeType.ALL)
    private Organization organization;
    @Nullable
    @JsonSerialize(using = ConferenceRoomEquipmentSerializer.class)
    @OneToOne (cascade = CascadeType.ALL)
    private Equipment equipment;

    public ConferenceRoom() {
    }

    public ConferenceRoom(@NotBlank @Size(min = 2, max = 20, message = "Conference room name should have minimum 2 and maximum 20 characters.") String name,
                          @Nullable @Size(min = 2, max = 20, message = "Organization name should have minimum 2 and maximum 20 characters.") String optionalId,
                          @Min(0) @Max(10) int floor, boolean available, int sittingAndStandingPlaces,
                          @Nullable int lyingPlaces, @Nullable int hangingPlaces, List<Reservation> reservations, Organization organization, @Nullable Equipment equipment
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

    public ConferenceRoom(@NotBlank @Size(min = 2, max = 20, message = "Conference room name should have minimum 2 and maximum 20 characters.") String name,
                          @Min(0) @Max(10) int floor, boolean available, int sittingAndStandingPlaces, Organization organization
    ) {
        this.name = name;
        this.floor = floor;
        this.available = available;
        this.sittingAndStandingPlaces = sittingAndStandingPlaces;
        this.organization = organization;
    }
}