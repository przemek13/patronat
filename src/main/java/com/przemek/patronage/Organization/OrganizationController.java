package com.przemek.patronage.Organization;

import com.przemek.patronage.Exceptions.NoSuchIdException;
import com.przemek.patronage.Exceptions.SameNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class OrganizationController {

    private OrganizationService service;
    private final OrganizationResourceAssembler assembler;

    @Autowired
    public OrganizationController(OrganizationService service, OrganizationResourceAssembler assembler) {
        this.service = Objects.requireNonNull(service, "must be defined.");
        this.assembler = assembler;
    }

    @GetMapping("/organizations")
    public Resources<Resource<Organization>> getOrganizations() {

        List<Resource<Organization>> organizations = service.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(organizations,
                linkTo(methodOn(OrganizationController.class).getOrganizations()).withSelfRel());
    }

    @PostMapping("/organizations")
    ResponseEntity addOrganization (@Valid @RequestBody Organization newOrganization) throws SameNameException {
        service.save(newOrganization);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/organizations/{id}")
    ResponseEntity updateOrganization(@RequestBody Organization newOrganization, @PathVariable Long id) {
        return ResponseEntity.ok(service.update(newOrganization, id));
    }

    @DeleteMapping("/organizations/{id}")
    ResponseEntity deleteOrganization(@PathVariable Long id) throws NoSuchIdException {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
