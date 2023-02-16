package com.softweb.api.store.controllers;

import com.softweb.api.store.model.entities.License;
import com.softweb.api.store.services.LicenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/license")
public class LicenseController {
    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public ResponseEntity<?> getLicenseById (@PathVariable(name = "code") String licenseCode) {
        return ResponseEntity.ok(licenseService.getLicenseById(licenseCode));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getLicenses () {
        return ResponseEntity.ok(licenseService.getLicenses());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> postLicense (@RequestBody License license) {
        licenseService.saveLicense(license);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> putLicense (@RequestBody License license) {
        licenseService.saveLicense(license);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteLicense (@PathVariable(name = "code") String licenseCode) {
        licenseService.deleteLicenseById(licenseCode);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}