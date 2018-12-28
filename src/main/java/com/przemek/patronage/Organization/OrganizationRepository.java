package com.przemek.patronage.Organization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Organization findByName (String name);
}
