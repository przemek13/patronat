package com.przemek.patronage.Organization;

import com.przemek.patronage.Exceptions.NoSuchIdException;
import com.przemek.patronage.Exceptions.SameNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrganizationService {

    private OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizations) {
        this.organizationRepository = Objects.requireNonNull(organizations, "must be defined.");
    }

    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    public void save(Organization newOrganization) throws SameNameException {
        if (organizationRepository.findByName(newOrganization.getName()) == null) {
            organizationRepository.save(newOrganization);
        } else {
            throw new SameNameException("The Organization with name given already exist. Please choose different name.");
        }
    }

    public Organization update(Organization newOrganization, Long id) {

        return organizationRepository.findById(id)
                .map(organization -> {
                    organization.setName(newOrganization.getName());
                    organization.setConferenceRoomsList(newOrganization.getConferenceRoomsList());
                    return organizationRepository.save(organization);
                })
                .orElseGet(() -> {
                    newOrganization.setId(id);
                    return organizationRepository.save(newOrganization);
                });
    }

    public void delete(Long id) throws NoSuchIdException {
        if (organizationRepository.findById(id).isEmpty()) {
            throw new NoSuchIdException("The Organization with id given doesn't exist in the base.");
        } else
            organizationRepository.deleteById(id);
    }
}
