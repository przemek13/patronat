package com.przemek.patronage.Organization;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

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
