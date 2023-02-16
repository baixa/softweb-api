package com.softweb.api.store.controllers;

import com.softweb.api.store.model.entities.Developer;
import com.softweb.api.store.services.DeveloperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/developer")
public class DeveloperController {
    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getDeveloperById (@PathVariable(name = "id") String developerId) {
        return ResponseEntity.ok(developerService.getDeveloperById(developerId));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getDevelopers () {
        return ResponseEntity.ok(developerService.getDevelopers());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> postDeveloper (@RequestBody Developer developer) {
        developerService.saveDeveloper(developer);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> putDeveloper (@RequestBody Developer developer) {
        developerService.saveDeveloper(developer);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDeveloper (@PathVariable(name = "id") String developerId) {
        developerService.deleteDeveloperById(developerId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
