package com.softweb.api.store.services;

import com.softweb.api.store.model.entities.License;
import com.softweb.api.store.model.repository.LicenseRepository;
import com.softweb.api.store.model.repository.RepositoryConstants;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public List<License> getLicenses (Integer page) {
        return licenseRepository
                .findAll(PageRequest.of(page, RepositoryConstants.COUNT_PAGE_ELEMENTS, Sort.Direction.ASC, "code"))
                .getContent();
    }
}
