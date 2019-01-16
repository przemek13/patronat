package com.przemek.patronage.Organization;

import com.przemek.patronage.Exceptions.NoSuchIdException;
import com.przemek.patronage.Exceptions.SameNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public void save(Organization newOrganization) throws SameNameException {
        if (organizations.findByName(newOrganization.getName()) == null) {
            organizations.save(newOrganization);
        } else {
            throw new SameNameException("The Organization with name given already exist. Please choose different name.");
        }
    }

    public Organization update(Organization newOrganization, Long id) {

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

    public void delete(Long id) throws NoSuchIdException {
        if (organizations.findById(id).equals(Optional.empty())) {
            throw new NoSuchIdException("The Organization with id given doesn't exist in the base.");
        } else
            organizations.deleteById(id);
    }
}
