package com.przemek.patronage.Organization;

import com.przemek.patronage.Mapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class OrganizationController {

    private final OrganizationService service;

    private final Mapper mapper;

    private OrganizationController(OrganizationService service, Mapper mapper) {
        this.service = Objects.requireNonNull(service, "must be defined.");
        this.mapper = Objects.requireNonNull(mapper, "must be defined.");
    }

    @GetMapping("/organizations")
    private List<OrganizationDTO> getOrganizations() {
        return service.findAll().stream()
                .map(mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/organizations")
    private OrganizationDTO addOrganization(@Valid @RequestBody OrganizationDTO newOrganizationDTO) throws ParseException {
        var newOrganization = mapper.convertToEntity(newOrganizationDTO);
        service.save(newOrganization);
        newOrganizationDTO = mapper.convertToDTO(newOrganization);
        return newOrganizationDTO;
    }

    @PutMapping("/organizations/{id}")
    private OrganizationDTO updateOrganization(@Valid @RequestBody OrganizationDTO newOrganizationDTO, @PathVariable Long id) throws ParseException {
        var newOrganization = mapper.convertToEntity(newOrganizationDTO);
        service.update(newOrganization, id);
        newOrganizationDTO = mapper.convertToDTO(newOrganization);
        return newOrganizationDTO;
    }

    @DeleteMapping("/organizations/{id}")
    private void deleteOrganization(@PathVariable Long id) {
        service.delete(id);
    }
}
