package com.softweb.api.store.controllers;

import com.softweb.api.store.model.entities.Developer;
import com.softweb.api.store.services.DeveloperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/developer")
public class DeveloperController {
    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> getDeveloperById (@PathVariable(name = "id") String developerId) {
        return ResponseEntity.ok(developerService.getDeveloperById(developerId));
    }

    @GetMapping
    @RequestMapping(value = "/")
    public ResponseEntity<?> getDevelopers () {
        return ResponseEntity.ok(developerService.getDevelopers());
    }

    @PostMapping
    @RequestMapping(value = "/")
    public ResponseEntity<?> postDeveloper (@RequestBody Developer developer) {
        developerService.saveDeveloper(developer);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping
    @RequestMapping(value = "/")
    public ResponseEntity<?> putDeveloper (@RequestBody Developer developer) {
        developerService.saveDeveloper(developer);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> deleteDeveloper (@PathVariable(name = "id") String developerId) {
        developerService.deleteDeveloperById(developerId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
