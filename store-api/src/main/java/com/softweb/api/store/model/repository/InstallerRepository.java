package com.softweb.api.store.model.repository;

import com.softweb.api.store.model.entities.Installer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallerRepository extends JpaRepository<Installer, Long> {
    long countByInstallerPath(String installerPath);
}