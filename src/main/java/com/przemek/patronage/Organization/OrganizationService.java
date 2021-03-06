package com.przemek.patronage.Organization;

import com.przemek.patronage.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final Mapper mapper;
    private final OrganizationDataChange organizationDataChange;

    public OrganizationService(OrganizationRepository organizations, Mapper mapper, OrganizationDataChange organizationDataChange) {
        this.organizationRepository = Objects.requireNonNull(organizations, "must be defined.");
        this.mapper = Objects.requireNonNull(mapper, "must be defined.");
        this.organizationDataChange = Objects.requireNonNull(organizationDataChange, "must be defined.");
    }

    public List<OrganizationDTO> findAll() {
        return organizationRepository.findAll().stream()
                .map(mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrganizationDTO save(OrganizationDTO newOrganizationDTO) {
        var newOrganization = mapper.convertToEntity(newOrganizationDTO);
        if (organizationRepository.findByName(newOrganization.getName()) != null) {
            throw new IllegalArgumentException("The Organization with name given already exist. Please choose different name.");
        }
        organizationRepository.save(newOrganization);
        return mapper.convertToDTO(newOrganization);
    }

    public OrganizationDTO update(OrganizationDTO newOrganizationDTO, Long id) {
        var newOrganization = mapper.convertToEntity(newOrganizationDTO);
        return organizationRepository.findById(id)
                .map(organization -> {
                    organizationDataChange.setNewData(organization, newOrganization);
                    organizationRepository.save(organization);
                    return mapper.convertToDTO(organization);
                })
                .orElseGet(() -> {
                    throw new IllegalArgumentException("The Organization with id given doesn't exist in the base.");
                });
    }

    public void delete(Long id) {
        if (organizationRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("The Organization with id given doesn't exist in the base.");
        }
        organizationRepository.deleteById(id);
    }
}