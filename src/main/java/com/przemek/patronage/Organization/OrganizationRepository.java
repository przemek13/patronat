package com.przemek.patronage.Organization;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Organization findByName(String name);
}