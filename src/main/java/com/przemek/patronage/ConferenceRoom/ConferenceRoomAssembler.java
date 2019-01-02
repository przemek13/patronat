package com.przemek.patronage.ConferenceRoom;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ConferenceRoomAssembler implements ResourceAssembler<ConferenceRoom, Resource<ConferenceRoom>> {

    @Override
    public Resource<ConferenceRoom> toResource(ConferenceRoom conferenceRoom) {

        return new Resource<ConferenceRoom>(conferenceRoom,
                linkTo(methodOn(ConferenceRoomController.class).getConferenceRoom()).withRel("Conference rooms"));
    }
}
