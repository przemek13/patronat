package com.przemek.patronage.ConferenceRoom;

import com.przemek.patronage.Equipment.Equipment;
import com.przemek.patronage.Organization.Organization;
import com.przemek.patronage.Reservation.Reservation;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
public class ConferenceRoom {
    private @Id
    @GeneratedValue
    Long id;
    private String name;
    private String optionalId;
    private int floor;
    private boolean available;
    private int sittingAndStandingPlaces;
    private int lyingPlaces;
    private int hangingPlaces;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Reservation> reservations;
    @ManyToOne(cascade = CascadeType.ALL)
    private Organization organization;
    @OneToOne(cascade = CascadeType.ALL)
    private Equipment equipment;

    public ConferenceRoom() {
    }

    public ConferenceRoom(String name, String optionalId, int floor,
                          boolean available, int sittingAndStandingPlaces, int lyingPlaces, int hangingPlaces, List<Reservation> reservations, Organization organization, Equipment equipment) {
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

    public ConferenceRoom(String name, int floor, boolean available, int sittingAndStandingPlaces, Organization organization) {
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

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}