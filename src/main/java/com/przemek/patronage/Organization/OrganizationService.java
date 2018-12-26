package com.przemek.patronage.Organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrganizationService {

    private OrganizationRepository organizations;

    @Autowired
    public OrganizationService(OrganizationRepository organizations) {
        this.organizations = Objects.requireNonNull(organizations, "must be defined.");
    }

    public List<Organization> findAll() {
        return organizations.findAll();
    }

    public void save(Organization newOrganization) {
        organizations.save(newOrganization);
    }

    Organization update(Organization newOrganization, Long id) {

        return organizations.findById(id)
                .map(organization -> {
                    organization.setName(newOrganization.getName());
                    return organizations.save(organization);
                })
                .orElseGet(() -> {
                    newOrganization.setId(id);
                    return organizations.save(newOrganization);
                });
    }

    public void delete(Long id) {
        organizations.deleteById(id);
    }
}
