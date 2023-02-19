package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.DeveloperDto;
import com.softweb.api.store.model.entities.Developer;
import com.softweb.api.store.services.DeveloperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/developer")
public class DeveloperController {
    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getDeveloperById (@PathVariable(name = "id") String developerId) {
        Developer developer = developerService.getDeveloperById(developerId);
        return ResponseEntity.ok(new DeveloperDto(developer));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getDevelopers () {
        List<Developer> developerList = developerService.getDevelopers();
        List<DeveloperDto> developerDtos = developerList.stream().map(DeveloperDto::new).toList();
        return ResponseEntity.ok(developerDtos);
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
