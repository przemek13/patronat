package com.przemek.patronage.Organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class OrganizationController {

    private OrganizationService service;

    @Autowired
    public OrganizationController(OrganizationService service) {
        this.service = Objects.requireNonNull(service, "must be defined.");
    }

    @GetMapping("/organizations")
    public ResponseEntity<List<Organization>> getOrganizations() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/organizations")
    ResponseEntity addOrganization (@RequestBody Organization newOrganization) {
        service.save(newOrganization);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/organizations/{id}")
    ResponseEntity updateOrganization(@RequestBody Organization newOrganization, @PathVariable Long id) {
        return ResponseEntity.ok(service.update(newOrganization, id));
    }

    @DeleteMapping("/organizations/{id}")
    ResponseEntity deleteOrganization(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
