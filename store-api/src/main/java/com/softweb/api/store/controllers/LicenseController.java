package com.softweb.api.store.controllers;

import com.softweb.api.store.model.entities.License;
import com.softweb.api.store.services.LicenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/license")
public class LicenseController {
    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping
    @RequestMapping(value = "/{code}")
    public ResponseEntity<?> getLicenseById (@PathVariable(name = "code") String licenseCode) {
        return ResponseEntity.ok(licenseService.getLicenseById(licenseCode));
    }

    @GetMapping
    @RequestMapping(value = "/")
    public ResponseEntity<?> getLicenses () {
        return ResponseEntity.ok(licenseService.getLicenses());
    }

    @PostMapping
    @RequestMapping(value = "/")
    public ResponseEntity<?> postLicense (@RequestBody License license) {
        licenseService.saveLicense(license);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping
    @RequestMapping(value = "/")
    public ResponseEntity<?> putLicense (@RequestBody License license) {
        licenseService.saveLicense(license);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping
    @RequestMapping(value = "/{code}")
    public ResponseEntity<?> deleteLicense (@PathVariable(name = "code") String licenseCode) {
        licenseService.deleteLicenseById(licenseCode);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}