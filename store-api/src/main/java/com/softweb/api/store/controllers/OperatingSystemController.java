package com.softweb.api.store.controllers;

import com.softweb.api.store.model.entities.OperatingSystem;
import com.softweb.api.store.services.OperatingSystemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/operatingSystem")
public class OperatingSystemController {
    private final OperatingSystemService operatingSystemService;

    public OperatingSystemController(OperatingSystemService operatingSystemService) {
        this.operatingSystemService = operatingSystemService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOperatingSystemById (@PathVariable(name = "id") String operatingSystemId) {
        return ResponseEntity.ok(operatingSystemService.getOperatingSystemById(operatingSystemId));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getOperatingSystems () {
        return ResponseEntity.ok(operatingSystemService.getOperatingSystems());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> postOperatingSystem (@RequestBody OperatingSystem operatingSystem) {
        operatingSystemService.saveOperatingSystem(operatingSystem);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> putOperatingSystem (@RequestBody OperatingSystem operatingSystem) {
        operatingSystemService.saveOperatingSystem(operatingSystem);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOperatingSystem (@PathVariable(name = "id") String operatingSystemId) {
        operatingSystemService.deleteOperatingSystemById(operatingSystemId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}