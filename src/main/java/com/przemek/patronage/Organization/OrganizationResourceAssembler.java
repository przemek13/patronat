package com.przemek.patronage.Organization;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class OrganizationResourceAssembler implements ResourceAssembler<Organization, Resource<Organization>> {

    @Override
    public Resource<Organization> toResource(Organization organization) {

            return new Resource<Organization>(organization,
                    linkTo(methodOn(OrganizationController.class).getOrganizations()).withRel("organizations"));
    }
}
