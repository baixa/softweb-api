package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.operatingsystem.OperatingSystemDto;
import com.softweb.api.store.model.entities.OperatingSystem;
import com.softweb.api.store.services.OperatingSystemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/v1/operatingSystem")
public class OperatingSystemController {
    private final OperatingSystemService operatingSystemService;

    public OperatingSystemController(OperatingSystemService operatingSystemService) {
        this.operatingSystemService = operatingSystemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOperatingSystemById (@PathVariable(name = "id") String operatingSystemId) {
        OperatingSystem operatingSystem = operatingSystemService.getOperatingSystemById(operatingSystemId);
        return Objects.isNull(operatingSystem) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(new OperatingSystemDto(operatingSystem));
    }

    @GetMapping
    public ResponseEntity<?> getOperatingSystems () {
        List<OperatingSystem> operatingSystems = operatingSystemService.getOperatingSystems();
        List<OperatingSystemDto> operatingSystemDtos = operatingSystems.stream().map(OperatingSystemDto::new).toList();
        return ResponseEntity.ok(operatingSystemDtos);
    }
}