package com.softweb.api.store.controllers;

import com.softweb.api.store.model.entities.OperatingSystem;
import com.softweb.api.store.services.OperatingSystemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/operatingSystem")
public class OperatingSystemController {
    private final OperatingSystemService operatingSystemService;

    public OperatingSystemController(OperatingSystemService operatingSystemService) {
        this.operatingSystemService = operatingSystemService;
    }

    @GetMapping
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> getOperatingSystemById (@PathVariable(name = "id") String operatingSystemId) {
        return ResponseEntity.ok(operatingSystemService.getOperatingSystemById(operatingSystemId));
    }

    @GetMapping
    @RequestMapping(value = "/")
    public ResponseEntity<?> getOperatingSystems () {
        return ResponseEntity.ok(operatingSystemService.getOperatingSystems());
    }

    @PostMapping
    @RequestMapping(value = "/")
    public ResponseEntity<?> postOperatingSystem (@RequestBody OperatingSystem operatingSystem) {
        operatingSystemService.saveOperatingSystem(operatingSystem);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping
    @RequestMapping(value = "/")
    public ResponseEntity<?> putOperatingSystem (@RequestBody OperatingSystem operatingSystem) {
        operatingSystemService.saveOperatingSystem(operatingSystem);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> deleteOperatingSystem (@PathVariable(name = "id") String operatingSystemId) {
        operatingSystemService.deleteOperatingSystemById(operatingSystemId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}