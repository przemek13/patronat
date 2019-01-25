package com.przemek.patronage.Equipment;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomDTO;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;


@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class EquipmentDTO {
    @Nullable
    private Long id;
    @Nullable
    private String projectorName;
    private boolean isPhone;
    @Min(value = 0, message = "Internal number has to be an integer between 0 and 99")
    @Max(value = 99, message = "Internal number has to be an integer between 0 and 99")
    @Nullable
    private Integer internalNumber;
    @Nullable
    @Pattern(regexp = "^(\\+\\d{2}\\s\\d{9})$")
    private String externalNumber;
    @Nullable
    private InterfaceConnections connections;
    @JsonSerialize(using = EquipmentConferenceRoomSerializer.class)
    private ConferenceRoomDTO conferenceroom;

    public EquipmentDTO() {
    }

    public EquipmentDTO(@Nullable String projectorName, boolean isPhone,
                        @Min(value = 0, message = "Internal number has to be an integer between 0 and 99")
                        @Max(value = 99, message = "Internal number has to be an integer between 0 and 99") int internalNumber,
                        @Nullable @Pattern(regexp = "^(\\+\\d{2}\\s\\d{9})$") String externalNumber,
                        @Nullable InterfaceConnections connections,
                        ConferenceRoomDTO conferenceroom) {
        this.projectorName = projectorName;
        this.isPhone = isPhone;
        this.internalNumber = internalNumber;
        this.externalNumber = externalNumber;
        this.connections = connections;
        this.conferenceroom = conferenceroom;
    }

    public EquipmentDTO(@Nullable String projectorName, boolean isPhone, ConferenceRoomDTO conferenceroom) {
        this.projectorName = projectorName;
        this.isPhone = isPhone;
        this.conferenceroom = conferenceroom;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    @Nullable
    public String getProjectorName() {
        return projectorName;
    }

    public void setProjectorName(@Nullable String projectorName) {
        this.projectorName = projectorName;
    }

    public boolean isPhone() {
        return isPhone;
    }

    public void setPhone(boolean phone) {
        isPhone = phone;
    }

    @Nullable
    public Integer getInternalNumber() {
        return internalNumber;
    }

    public void setInternalNumber(@Nullable Integer internalNumber) {
        this.internalNumber = internalNumber;
    }

    @Nullable
    public String getExternalNumber() {
        return externalNumber;
    }

    public void setExternalNumber(@Nullable String externalNumber) {
        this.externalNumber = externalNumber;
    }

    @Nullable
    public InterfaceConnections getConnections() {
        return connections;
    }

    public void setConnections(@Nullable InterfaceConnections connections) {
        this.connections = connections;
    }

    public ConferenceRoomDTO getConferenceroom() {
        return conferenceroom;
    }

    public void setConferenceroom(ConferenceRoomDTO conferenceroom) {
        this.conferenceroom = conferenceroom;
    }
}