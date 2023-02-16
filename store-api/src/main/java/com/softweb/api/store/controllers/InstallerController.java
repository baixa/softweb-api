package com.softweb.api.store.controllers;

import com.softweb.api.store.model.entities.Installer;
import com.softweb.api.store.services.InstallerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/installer")
public class InstallerController {
    private final InstallerService installerService;

    public InstallerController(InstallerService installerService) {
        this.installerService = installerService;
    }

    @GetMapping
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> getInstallerById (@PathVariable(name = "id") String installerId) {
        return ResponseEntity.ok(installerService.getInstallerById(installerId));
    }

    @GetMapping
    @RequestMapping(value = "/")
    public ResponseEntity<?> getInstallers () {
        return ResponseEntity.ok(installerService.getInstallers());
    }

    @PostMapping
    @RequestMapping(value = "/")
    public ResponseEntity<?> postInstaller (@RequestBody Installer installer) {
        installerService.saveInstaller(installer);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping
    @RequestMapping(value = "/")
    public ResponseEntity<?> putInstaller (@RequestBody Installer installer) {
        installerService.saveInstaller(installer);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> deleteInstaller (@PathVariable(name = "id") String installerId) {
        installerService.deleteInstallerById(installerId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}