package com.przemek.patronage.Organization;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.przemek.patronage.ConferenceRoom.ConferenceRoom;

import javax.persistence.*;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Organization {
    private @Id
    @GeneratedValue
    Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ConferenceRoom> conferenceRoomsList;

    public Organization() {
    }

    public Organization(String name, List<ConferenceRoom> conferenceRooms) {
        this.name = name;
        this.conferenceRoomsList = conferenceRooms;
    }

    public Organization(String name) {
        this.name = name;
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

    public List<ConferenceRoom> getConferenceRoomsList() {
        return conferenceRoomsList;
    }

    public void setConferenceRoomsList(List<ConferenceRoom> conferenceRoomsList) {
        this.conferenceRoomsList = conferenceRoomsList;
    }
}
