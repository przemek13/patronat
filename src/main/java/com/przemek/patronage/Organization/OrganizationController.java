package com.przemek.patronage.Organization;

import com.przemek.patronage.Exceptions.NoSuchIdException;
import com.przemek.patronage.Exceptions.SameNameException;
import com.przemek.patronage.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class OrganizationController {

    private OrganizationService service;

    private Mapper mapper;

    @Autowired
    public OrganizationController(OrganizationService service, Mapper mapper) {
        this.service = Objects.requireNonNull(service, "must be defined.");
        this.mapper = Objects.requireNonNull(mapper, "must be defined.");
    }

    @GetMapping("/organizations")
    public ResponseEntity<List<OrganizationDTO>> getOrganizations() {
        return ResponseEntity.ok(service.findAll().stream()
                .map(organization -> mapper.convertToDTO(organization))
                .collect(Collectors.toList()));
    }

    @PostMapping("/organizations")
    public ResponseEntity<OrganizationDTO> addOrganization(@Valid @RequestBody OrganizationDTO newOrganizationDTO) throws SameNameException, ParseException {
        var newOrganization = mapper.convertToEntity(newOrganizationDTO);
        service.save(newOrganization);
        newOrganizationDTO = mapper.convertToDTO(newOrganization);
        return ResponseEntity.ok(newOrganizationDTO);
    }

    @PutMapping("/organizations/{id}")
    public ResponseEntity<OrganizationDTO> updateOrganization(@Valid @RequestBody OrganizationDTO newOrganizationDTO, @PathVariable Long id) throws ParseException {
        var newOrganization = mapper.convertToEntity(newOrganizationDTO);
        service.update(newOrganization, id);
        newOrganizationDTO = mapper.convertToDTO(newOrganization);
        return ResponseEntity.ok(newOrganizationDTO);
    }

    @DeleteMapping("/organizations/{id}")
    public ResponseEntity deleteOrganization(@PathVariable Long id) throws NoSuchIdException {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
