package com.softweb.api.store.model.repository;

import com.softweb.api.store.model.entities.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRepository extends JpaRepository<License, String> {
}