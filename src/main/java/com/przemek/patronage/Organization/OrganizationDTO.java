package com.przemek.patronage.Organization;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.przemek.patronage.ConferenceRoom.ConferenceRoomDTO;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Component
public class OrganizationDTO {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 20, message = "Organization name should have minimum 2 and maximum 20 characters.")
    private String name;
    @JsonSerialize(using = OrganizationConferenceRoomSerializer.class)
    private List<ConferenceRoomDTO> conferenceRoomsList;

    public OrganizationDTO() {
    }

    public OrganizationDTO(Long id,
                           @NotBlank @Size(min = 2, max = 20, message = "Organization name should have minimum 2 and maximum 20 characters.") String name,
                           List<ConferenceRoomDTO> conferenceRooms) {
        this.id = id;
        this.name = name;
        this.conferenceRoomsList = conferenceRooms;
    }

    public OrganizationDTO(@NotBlank @Size(min = 2, max = 20, message = "Organization name should have minimum 2 and maximum 20 characters.") String name,
                           List<ConferenceRoomDTO> conferenceRooms) {
        this.name = name;
        this.conferenceRoomsList = conferenceRooms;
    }

    public OrganizationDTO(@NotBlank @Size(min = 2, max = 20, message = "Organization name should have minimum 2 and maximum 20 characters.") String name) {
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

    public List<ConferenceRoomDTO> getConferenceRoomsList() {
        return conferenceRoomsList;
    }

    public void setConferenceRoomsList(List<ConferenceRoomDTO> conferenceRoomsList) {
        this.conferenceRoomsList = conferenceRoomsList;

    }
}
