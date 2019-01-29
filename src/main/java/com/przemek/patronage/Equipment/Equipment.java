package com.przemek.patronage.Equipment;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;

import javax.persistence.*;

@Entity
public class Equipment {
    private @Id
    @GeneratedValue
    Long id;
    private String projectorName;
    private boolean isPhone;
    private Integer internalNumber;
    private String externalNumber;
    private String connections;
    @OneToOne(cascade = CascadeType.ALL)
    private ConferenceRoom conferenceRoom;

    public Equipment() {
    }

    public Equipment(String projectorName, boolean isPhone, int internalNumber, String externalNumber, InterfaceConnections connections, ConferenceRoom conferenceRoom) {
        this.projectorName = projectorName;
        this.isPhone = isPhone;
        this.internalNumber = internalNumber;
        this.externalNumber = externalNumber;
        this.connections = connections.toString();
        this.conferenceRoom = conferenceRoom;
    }

    public Equipment(String projectorName, boolean isPhone, ConferenceRoom conferenceRoom) {
        this.projectorName = projectorName;
        this.isPhone = isPhone;
        this.conferenceRoom = conferenceRoom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectorName() {
        return projectorName;
    }

    public void setProjectorName(String projectorName) {
        this.projectorName = projectorName;
    }

    public boolean isPhone() {
        return isPhone;
    }

    public void setPhone(boolean phone) {
        isPhone = phone;
    }

    public Integer getInternalNumber() {
        return internalNumber;
    }

    public void setInternalNumber(Integer internalNumber) {
        this.internalNumber = internalNumber;
    }

    public String getExternalNumber() {
        return externalNumber;
    }

    public void setExternalNumber(String externalNumber) {
        this.externalNumber = externalNumber;
    }

    public String getConnections() {
        return connections;
    }

    public void setConnections(String connections) {
        this.connections = connections;
    }

    public ConferenceRoom getConferenceRoom() {
        return conferenceRoom;
    }

    public void setConferenceRoom(ConferenceRoom conferenceRoom) {
        this.conferenceRoom = conferenceRoom;
    }
}