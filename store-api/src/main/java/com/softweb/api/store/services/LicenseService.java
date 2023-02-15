package com.softweb.api.store.services;

import com.softweb.api.store.model.entities.License;
import com.softweb.api.store.model.repository.LicenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LicenseService {
    private final LicenseRepository licenseRepository;

    public LicenseService(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public License getLicenseById (String licenseCode) {
        return licenseRepository.findById(licenseCode).orElse(null);
    }

    public List<License> getLicenses () {
        return licenseRepository.findAll();
    }

    public void saveLicense (License license) {
        licenseRepository.save(license);
    }

    public void deleteLicense (License license) {
        licenseRepository.delete(license);
    }

    public void deleteLicenseById (String licenseCode) {
        licenseRepository.deleteById(licenseCode);
    }
}
