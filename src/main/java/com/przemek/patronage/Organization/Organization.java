package com.przemek.patronage.Organization;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import lombok.Data;
import org.springframework.hateoas.core.EmbeddedWrappers;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Organization {

    private @Id
    @GeneratedValue
    Long id;
    @NotBlank
    @Size(min = 2, max = 20, message = "Organization name should have minimum 2 and maximum 20 characters.")
//    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    List<ConferenceRoom> conferenceRoomsList;

    public Organization() {
    }

    public Organization(@NotBlank @Size(min = 2, max = 20, message = "Organization name should have minimum 2 and maximum 20 characters.") String name, List<ConferenceRoom> conferenceRooms) {
        this.name = name;
        this.conferenceRoomsList = conferenceRooms;
    }

    public Organization(@NotBlank @Size(min = 2, max = 20, message = "Organization name should have minimum 2 and maximum 20 characters.") String name) {
        this.name = name;
    }
}
