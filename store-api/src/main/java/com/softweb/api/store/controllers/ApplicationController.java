package com.softweb.api.store.controllers;

import com.softweb.api.store.model.entities.Application;
import com.softweb.api.store.services.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getApplicationById (@PathVariable(name = "id") String applicationId) {
        return ResponseEntity.ok(applicationService.getApplicationById(applicationId));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getApplications () {
        return ResponseEntity.ok(applicationService.getApplications());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> postApplication (@RequestBody Application application) {
        applicationService.saveApplication(application);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> putApplication (@RequestBody Application application) {
        applicationService.saveApplication(application);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteApplication (@PathVariable(name = "id") String applicationId) {
        applicationService.deleteApplicationById(applicationId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
