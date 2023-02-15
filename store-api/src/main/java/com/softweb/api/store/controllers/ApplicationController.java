package com.softweb.api.store.controllers;

import com.softweb.api.store.model.entities.Application;
import com.softweb.api.store.services.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> getApplicationById (@PathVariable(name = "id") String applicationId) {
        return ResponseEntity.ok(applicationService.getApplicationById(applicationId));
    }

    @GetMapping
    @RequestMapping(value = "/")
    public ResponseEntity<?> getApplications () {
        return ResponseEntity.ok(applicationService.getApplications());
    }

    @PostMapping
    @RequestMapping(value = "/")
    public ResponseEntity<?> postApplication (@RequestBody Application application) {
        applicationService.saveApplication(application);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping
    @RequestMapping(value = "/")
    public ResponseEntity<?> putApplication (@RequestBody Application application) {
        applicationService.saveApplication(application);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> deleteApplication (@PathVariable(name = "id") String applicationId) {
        applicationService.deleteApplicationById(applicationId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
