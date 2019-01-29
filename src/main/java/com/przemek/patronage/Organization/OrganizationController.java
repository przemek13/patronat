package com.przemek.patronage.Organization;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
public class OrganizationController {

    private final OrganizationService service;

    private OrganizationController(OrganizationService service) {
        this.service = Objects.requireNonNull(service, "must be defined.");
    }

    @GetMapping("/organizations")
    private List<OrganizationDTO> getOrganizations() {
        return service.findAll();
    }

    @PostMapping("/organizations")
    private OrganizationDTO addOrganization(@Valid @RequestBody OrganizationDTO newOrganizationDTO) {
        return service.save(newOrganizationDTO);
    }

    @PutMapping("/organizations/{id}")
    private OrganizationDTO updateOrganization(@Valid @RequestBody OrganizationDTO newOrganizationDTO, @PathVariable Long id) {
        return service.update(newOrganizationDTO, id);
    }

    @DeleteMapping("/organizations/{id}")
    private void deleteOrganization(@PathVariable Long id) {
        service.delete(id);
    }
}
