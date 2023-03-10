package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.license.LicenseDto;
import com.softweb.api.store.model.entities.License;
import com.softweb.api.store.services.LicenseService;
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
 * Controller for licenses
 * @see License
 */
@RestController
@RequestMapping(value = "/v1/license")
@Tag(name = "License", description = "API provides ability to manipulate licenses")
public class LicenseController {
    /**
     * Service, that provides ability to interaction with the License entity
     */
    private final LicenseService licenseService;

    /**
     * Controller
     *
     * @param licenseService Service of license
     */
    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    /**
     * Returns an object of the License class whose code matches the one passed
     *
     * @param licenseCode ID of requested license
     * @return Requested license
     */
    @GetMapping("/{code}")
    @Operation(
            summary = "Get license by it's code",
            description = "Returns an object of the License class whose code matches the one passed"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns requested license",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LicenseDto.class))}),
            @ApiResponse(responseCode = "404", description = "License with given code is absent", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content) })
    public ResponseEntity<?> getLicenseById (@PathVariable(name = "code") String licenseCode) {
        License license = licenseService.getLicenseById(licenseCode);
        return Objects.isNull(license) ? ResponseEntity.of(Optional.empty()) : ResponseEntity.ok(new LicenseDto(license));
    }

    /**
     * Returns a list of licenses
     *
     * @return List of licenses
     */
    @GetMapping
    @Operation(
            summary = "Get licenses",
            description = "Returns all licenses"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns full list of licenses",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LicenseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content) })
    public ResponseEntity<?> getLicenses () {
        List<License> licenses = licenseService.getLicenses();
        List<LicenseDto> licenseDtos = licenses.stream().map(LicenseDto::new).toList();
        return ResponseEntity.ok(licenseDtos);
    }
}