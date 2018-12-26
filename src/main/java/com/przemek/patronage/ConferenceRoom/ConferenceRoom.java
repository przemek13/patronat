package com.przemek.patronage.ConferenceRoom;

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
public class ConferenceRoom {

    private @Id
    @GeneratedValue
    Long id;
    @NotBlank
    @Size(min = 2, max = 20, message = "Conference room name should have minimum 2 and maximum 20 characters.")
    @Column(unique = true)
    private String name;
    @Nullable
    @Size(min = 2, max = 20, message = "Conference name should have minimum 2 and maximum 20 characters.")
    @Column(unique = true)
    private String optionalId;
    @Min(0)
    @Max(10)
    private int floor;
    private boolean available;
    private int sittingPlaces;
    private int standingPlaces;
    private int hangingPlaces;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Reservation> reservations;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Organization organization;

    public ConferenceRoom() {
    }

    public ConferenceRoom(@NotBlank @Size(min = 2, max = 20, message = "Conference room name should have minimum 2 and maximum 20 characters.") String name, boolean available) {
        this.name = name;
        this.available = available;
    }

    public ConferenceRoom(@NotBlank @Size(min = 2, max = 20, message = "Conference room name should have minimum 2 and maximum 20 characters.") String name,
                          @Size(min = 2, max = 20, message = "Organization name should have minimum 2 and maximum 20 characters.") String optionalId,
                          @Min(0) @Max(10) int floor, boolean available, int sittingPlaces, int standingPlaces, int hangingPlaces, List<Reservation> reservations, Organization organization
    ) {
        this.name = name;
        this.optionalId = optionalId;
        this.floor = floor;
        this.available = available;
        this.sittingPlaces = sittingPlaces;
        this.standingPlaces = standingPlaces;
        this.hangingPlaces = hangingPlaces;
        this.reservations = reservations;
        this.organization = organization;
    }

    public ConferenceRoom(@NotBlank @Size(min = 2, max = 20, message = "Conference room name should have minimum 2 and maximum 20 characters.") String name,
                          @Size(min = 2, max = 20, message = "Organization name should have minimum 2 and maximum 20 characters.") String optionalId,
                          @Min(0) @Max(10) int floor, boolean available, int sittingPlaces, int standingPlaces, int hangingPlaces, Organization organization
    ) {
        this.name = name;
        this.optionalId = optionalId;
        this.floor = floor;
        this.available = available;
        this.sittingPlaces = sittingPlaces;
        this.standingPlaces = standingPlaces;
        this.hangingPlaces = hangingPlaces;
        this.organization = organization;
    }

    public ConferenceRoom(@NotBlank @Size(min = 2, max = 20, message = "Conference room name should have minimum 2 and maximum 20 characters.") String name,
                          @Min(0) @Max(10) int floor, boolean available, int sittingPlaces, int standingPlaces, int hangingPlaces, List<Reservation> reservations, Organization organization) {
        this.name = name;
        this.floor = floor;
        this.available = available;
        this.sittingPlaces = sittingPlaces;
        this.standingPlaces = standingPlaces;
        this.hangingPlaces = hangingPlaces;
        this.reservations = reservations;
        this.organization = organization;
    }

    public ConferenceRoom(@NotBlank @Size(min = 2, max = 20, message = "Conference room name should have minimum 2 and maximum 20 characters.") String name,
                          @Min(0) @Max(10) int floor, boolean available, int sittingPlaces, int standingPlaces, int hangingPlaces, Organization organization
    ) {
        this.name = name;
        this.floor = floor;
        this.available = available;
        this.sittingPlaces = sittingPlaces;
        this.standingPlaces = standingPlaces;
        this.hangingPlaces = hangingPlaces;
        this.organization = organization;
    }
}