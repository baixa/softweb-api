package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.installer.UploadInstallerDto;
import com.softweb.api.store.model.entities.*;
import com.softweb.api.store.services.*;
import com.softweb.api.store.utils.ResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.softweb.api.store.services.FileStorageService.getResourceAsResponseEntity;

/**
 * Controller for applications' installers
 * @see Installer
 */
@RestController
@RequestMapping(value = "/v1/installer")
@Tag(name = "Installer", description = "API provides ability to manipulate applications' installers")
public class InstallerController {
    /**
     * Service, that provides ability to interaction with the Installer entity
     */
    private final InstallerService installerService;

    /**
     * Service, that provides ability manipulate with static files
     */
    private final FileStorageService fileStorageService;

    /**
     * Service providing information about an authorized user
     */
    private final AuthenticationService authenticationService;

    /**
     * Service, that provides ability to interaction with the Application entity
     */
    private final ApplicationService applicationService;

    /**
     * Service, that provides ability to interaction with the OperatingSystem entity
     */
    private final OperatingSystemService operatingSystemService;

    /**
     * Controller
     * 
     * @param installerService Service of installer
     * @param fileStorageService Service of static files
     * @param authenticationService Service of authentication
     * @param applicationService Service of application
     * @param operatingSystemService Service of operating system
     */
    public InstallerController(InstallerService installerService, FileStorageService fileStorageService, AuthenticationService authenticationService, ApplicationService applicationService, OperatingSystemService operatingSystemService) {
        this.installerService = installerService;
        this.fileStorageService = fileStorageService;
        this.authenticationService = authenticationService;
        this.applicationService = applicationService;
        this.operatingSystemService = operatingSystemService;
    }

    /**
     * Get installer-file by its name
     *
     * @param fileName Name of installer-file
     * @param request Current http request
     * @return Requested file
     */
    @GetMapping("/{fileName:.+}")
    @Operation(
            summary = "Get installer by file name",
            description = "Returns an file according to the passed filename"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns requested file"),
            @ApiResponse(responseCode = "404", description = "File not found"),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied") })
    public ResponseEntity<Resource> getInstallerByName(
            @Parameter(description = "Name of requested file")
            @PathVariable String fileName, 
            HttpServletRequest request) {
        return getResourceAsResponseEntity(fileName, request, fileStorageService);
    }



    /**
     * Upload and save installer in resource folder
     *
     * @param file Savable file
     * @param applicationId ID of linked application
     * @return Saved file
     */
    @PostMapping("/upload")
    @SecurityRequirement(name = "api")
    @Operation(
            summary = "Upload application installer to server",
            description = "Upload application installer to server and save it as Installer object"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns created installer object",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UploadInstallerDto.class))}),
            @ApiResponse(responseCode = "404", description = "Linked entity not found"),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied"),
            @ApiResponse(responseCode = "403", description = "Access denied")})
    public ResponseEntity<?> uploadFile(
            @Parameter(description = "Upload image file")
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "ID of linked application")
            @RequestParam String applicationId,
            @Parameter(description = "ID of linked OS")
            @RequestParam String systemId,
            @Parameter(description = "Version of installer")
            @RequestParam String version) {
        User authUser = authenticationService.getAuthenticatedUser();
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        Application application = applicationService.getApplicationById(applicationId);
        OperatingSystem system = operatingSystemService.getOperatingSystemById(systemId);
        if (Objects.isNull(application) || Objects.isNull(system))
            return ResponseEntity.of(Optional.empty());
        if (!(Objects.equals(application.getUser().getId(), authUser.getId())) && authUserAuthority != Authorities.ADMIN)
            return new ResponseEntity<>(new ResponseError("Access denied. You don't have rights to edit this application"), 
                    HttpStatus.FORBIDDEN);
        if (!Objects.requireNonNull(file.getContentType()).contains("application"))
            return new ResponseEntity<>("Invalid file supplied", HttpStatus.BAD_REQUEST);
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/installer/")
                .path(fileName)
                .toUriString();
        Installer installer = new Installer();
        installer.setApplication(application);
        installer.setInstallerPath(fileDownloadUri);
        installer.setSize((int) file.getSize());
        installer.setSystem(system);
        installer.setVersion(version);
        installerService.saveInstaller(installer);
        return new ResponseEntity<>(new UploadInstallerDto(installer.getId(), fileName, fileDownloadUri,
                file.getContentType(), file.getSize(), application, system), HttpStatus.CREATED);
    }

    /**
     * Upload and save installers in resource folder
     *
     * @param files Savable files
     * @param applicationId ID of linked application
     * @return Saved files
     */
    @PostMapping("/uploadMultiple")
    @SecurityRequirement(name = "api")
    @Operation(
            summary = "Upload multiple application installers to server",
            description = "Upload application installers to server and save them as Installer objects"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns created installer objects",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UploadInstallerDto.class))}),
            @ApiResponse(responseCode = "404", description = "Linked entities not found"),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied"),
            @ApiResponse(responseCode = "403", description = "Access denied")})
    public ResponseEntity<?> uploadMultipleFiles(
            @Parameter(description = "Upload image files")
            @RequestParam("files") MultipartFile[] files,
            @Parameter(description = "Linked application ID")
            @RequestParam String applicationId,
            @Parameter(description = "Linked system ID")
            @RequestParam String systemId,
            @Parameter(description = "Version of installer")
            @RequestParam String version) {
        User authUser = authenticationService.getAuthenticatedUser();
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        Application application = applicationService.getApplicationById(applicationId);
        OperatingSystem system = operatingSystemService.getOperatingSystemById(systemId);
        if (Objects.isNull(application) || Objects.isNull(system))
            return ResponseEntity.of(Optional.empty());
        List<UploadInstallerDto> uploadInstallerDtos = new ArrayList<>();
        if (!(Objects.equals(application.getUser().getId(), authUser.getId())) && authUserAuthority != Authorities.ADMIN)
            return new ResponseEntity<>(new ResponseError("Access denied! You don't have rights to edit this application"), 
                    HttpStatus.FORBIDDEN);
        for (MultipartFile file : files)
            if (!Objects.requireNonNull(file.getContentType()).contains("application"))
                return new ResponseEntity<>("Invalid file supplied", HttpStatus.BAD_REQUEST);
        List<Installer> storedInstaller = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = fileStorageService.storeFile(file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/v1/installer/")
                    .path(fileName)
                    .toUriString();
            Installer installer = new Installer();
            installer.setApplication(application);
            installer.setInstallerPath(fileDownloadUri);
            installer.setSize((int) file.getSize());
            installer.setSystem(system);
            installer.setVersion(version);
            installerService.saveInstaller(installer);
            uploadInstallerDtos.add(new UploadInstallerDto(installer.getId(), fileName, fileDownloadUri,
                    file.getContentType(), file.getSize(), application, system));
        }
        installerService.saveInstallers(storedInstaller);
        return new ResponseEntity<>(uploadInstallerDtos, HttpStatus.CREATED);
    }

    /**
     * Remove installer by id
     *
     * @param id ID of removable installer
     * @return status of removing
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "api")
    @Operation(
            summary = "Delete installer",
            description = "Delete installer by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns status of installer removing",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UploadInstallerDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Installer not found")})
    public ResponseEntity<?> deleteInstaller(@PathVariable String id) {
        Installer installer = installerService.getInstallerById(id);
        if (installer == null)
            return ResponseEntity.of(Optional.empty());
        User authUser = authenticationService.getAuthenticatedUser();
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        if (!(Objects.equals(installer.getApplication().getUser().getId(), authUser.getId()))
                && authUserAuthority != Authorities.ADMIN)
            return new ResponseEntity<>(new ResponseError("Access denied! You don't have rights to edit this installer"), 
                    HttpStatus.FORBIDDEN);
        installerService.deleteInstallerById(installer.getId().toString());
        if (installerService.getCountInstallersByPath(installer.getInstallerPath()) == 0) {
            if (fileStorageService.removeFileByPath(installer.getInstallerPath()))
                return new ResponseEntity<>(new ResponseError("Installer removed successfully!"), HttpStatus.OK);
            else
                return new ResponseEntity<>(new ResponseError("Access denied!"), HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(new ResponseError("Installer removed successfully!"), HttpStatus.OK);
        }
    }
}