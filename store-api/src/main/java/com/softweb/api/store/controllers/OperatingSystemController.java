package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.operatingsystem.OperatingSystemDto;
import com.softweb.api.store.model.entities.OperatingSystem;
import com.softweb.api.store.services.OperatingSystemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Controller for operating systems
 * @see OperatingSystem
 */
@RestController
@RequestMapping(value = "/v1/operatingSystem")
@Tag(name = "OS", description = "API provides ability to manipulate operating systems")
public class OperatingSystemController {
    /**
     * Service, that provides ability to interaction with th OperatingSystem entity
     */
    private final OperatingSystemService operatingSystemService;

    /**
     * Controller
     *
     * @param operatingSystemService Service of OS
     */
    public OperatingSystemController(OperatingSystemService operatingSystemService) {
        this.operatingSystemService = operatingSystemService;
    }

    /**
     * Returns an object of the OperatingSystem class whose id matches the one passed
     *
     * @param operatingSystemId ID of requested system
     * @return Requested system
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get system by it's id",
            description = "Returns an object of the OperatingSystem class whose id matches the one passed"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns requested system",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OperatingSystemDto.class))}),
            @ApiResponse(responseCode = "404", description = "System with given id is absent", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content) })
    public ResponseEntity<?> getOperatingSystemById (@PathVariable(name = "id") String operatingSystemId) {
        OperatingSystem operatingSystem = operatingSystemService.getOperatingSystemById(operatingSystemId);
        return Objects.isNull(operatingSystem) ? ResponseEntity.of(Optional.empty()) :
                ResponseEntity.ok(new OperatingSystemDto(operatingSystem));
    }

    /**
     * Returns a list of systems
     *
     * @return List of systems
     */
    @GetMapping
    @Operation(
            summary = "Get systems",
            description = "Returns all systems"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns full list of systems",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OperatingSystemDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content) })
    public ResponseEntity<?> getOperatingSystems () {
        List<OperatingSystem> operatingSystems = operatingSystemService.getOperatingSystems();
        List<OperatingSystemDto> operatingSystemDtos = operatingSystems.stream().map(OperatingSystemDto::new).toList();
        return ResponseEntity.ok(operatingSystemDtos);
    }
}