package com.przemek.patronage.Organization;

import com.przemek.patronage.ConferenceRoom.ConferenceRoom;
import com.przemek.patronage.Reservation.Reservation;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
public class Organization {

    private @Id
    @GeneratedValue
    Long id;
    @NotBlank
    @Size(min=2, max=20, message="Organization name should have minimum 2 and maximum 20 characters.")
    @Column(unique = true)
    private String name;

    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    List<ConferenceRoom> conferenceRooms;

    public Organization() {
    }

    public Organization(@NotBlank @Size(min = 2, max = 20, message = "Organization name should have minimum 2 and maximum 20 characters.") String name, List<ConferenceRoom> conferenceRooms) {
        this.name = name;
        this.conferenceRooms = conferenceRooms;
    }

    public Organization(@NotBlank @Size(min = 2, max = 20, message = "Organization name should have minimum 2 and maximum 20 characters.") String name) {
        this.name = name;
    }
}
