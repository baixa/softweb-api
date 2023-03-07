package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.license.LicenseDto;
import com.softweb.api.store.model.entities.License;
import com.softweb.api.store.services.LicenseService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/v1/license")
public class LicenseController {
    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> getLicenseById (@PathVariable(name = "code") String licenseCode) {
        License license = licenseService.getLicenseById(licenseCode);
        return Objects.isNull(license) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : ResponseEntity.ok(new LicenseDto(license));
    }

    @GetMapping
    public ResponseEntity<?> getLicenses (@ParameterObject Pageable pageable) {
        List<License> licenses = licenseService.getLicenses(pageable);
        List<LicenseDto> licenseDtos = licenses.stream().map(LicenseDto::new).toList();
        return ResponseEntity.ok(licenseDtos);
    }
}