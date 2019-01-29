package com.przemek.patronage.Organization;

import org.springframework.stereotype.Component;

@Component
public class OrganizationDataChange {

    public void setNewData (Organization organization, Organization newOrganization) {
        organization.setName(newOrganization.getName());
        organization.setConferenceRoomsList(newOrganization.getConferenceRoomsList());
    }
}
