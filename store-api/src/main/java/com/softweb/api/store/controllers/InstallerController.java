package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.installer.InstallerDto;
import com.softweb.api.store.model.entities.Installer;
import com.softweb.api.store.services.InstallerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/installer")
public class InstallerController {
    private final InstallerService installerService;

    public InstallerController(InstallerService installerService) {
        this.installerService = installerService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getInstallerById (@PathVariable(name = "id") String installerId) {
        Installer installer = installerService.getInstallerById(installerId);
        return ResponseEntity.ok(new InstallerDto(installer));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getInstallers () {
        List<Installer> installerList = installerService.getInstallers();
        List<InstallerDto> installerDtos = installerList.stream().map(InstallerDto::new).toList();
        return ResponseEntity.ok(installerDtos);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> postInstaller (@RequestBody Installer installer) {
        installerService.saveInstaller(installer);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> putInstaller (@RequestBody Installer installer) {
        installerService.saveInstaller(installer);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteInstaller (@PathVariable(name = "id") String installerId) {
        installerService.deleteInstallerById(installerId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}