package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.application.*;
import com.softweb.api.store.model.entities.Application;
import com.softweb.api.store.model.entities.Authorities;
import com.softweb.api.store.model.entities.Category;
import com.softweb.api.store.model.entities.User;
import com.softweb.api.store.services.*;
import com.softweb.api.store.utils.NumParser;
import com.softweb.api.store.utils.ResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Controller for published applications. Provides getting, creating, modifying, and deleting developer applications
 * @see Application
 */
@RestController
@RequestMapping(value = "/v1/application")
@Tag(name = "Application API", description = "API provides ability to manipulate users' applications")
public class ApplicationController {
    /**
     * Service, that provides ability to interaction with the Application entity
     */
    private final ApplicationService applicationService;

    /**
     * Service providing information about an authorized user
     */
    private final AuthenticationService authenticationService;

    /**
     * Service, that provides ability to interaction with the License entity
     */
    private final LicenseService licenseService;

    /**
     * Service, that provides ability to interaction with the Category entity
     */
    private final CategoryService categoryService;

    /**
     * Service, that provides ability manipulate with static files
     */
    private final FileStorageService fileStorageService;

    /**
     * Controller
     *
     * @param applicationService Application service
     * @param authenticationService Authentication service
     * @param licenseService License service
     * @param categoryService Category service
     * @param fileStorageService FileStorage service
     */
    public ApplicationController(ApplicationService applicationService,
                                 AuthenticationService authenticationService,
                                 LicenseService licenseService,
                                 CategoryService categoryService,
                                 FileStorageService fileStorageService) {
        this.applicationService = applicationService;
        this.authenticationService = authenticationService;
        this.licenseService = licenseService;
        this.categoryService = categoryService;
        this.fileStorageService = fileStorageService;
    }

    /**
     * Returns an object of the Application class whose id matches the one passed
     *
     * @param applicationId - id of requested application
     * @return requested application
     */
    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Get app by it's id",
            description = "Returns an object of the Application class whose id matches the one passed"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns requested application",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractApplicationGetDto.class))}),
            @ApiResponse(responseCode = "404", description = "Application with given id is absent", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content) })
    public ResponseEntity<?> getApplicationById (
            @Parameter(description = "Id of requested application")
            @PathVariable(name = "id") String applicationId) {

        if (NumParser.parseIntOrNull(applicationId) == null)
            return new ResponseEntity<>(new ResponseError("Invalid application ID"), HttpStatus.BAD_REQUEST);

        Application application = applicationService.getApplicationById(applicationId);
        Authorities userAuthority = authenticationService.getAuthenticationAuthority();

        if (application == null) {
            return ResponseEntity.of(Optional.empty());
        }

        if (userAuthority == null) {
            return ResponseEntity.ok(new ApplicationDefaultGetDto(application));
        }

        ResponseEntity<?> result = null;
        switch (userAuthority) {
            case ADMIN -> result = ResponseEntity.ok(new ApplicationAdminGetDto(application));
            case USER -> {
                if (authenticationService.getAuthenticationName().equals(application.getUser().getUsername())) {
                    result = ResponseEntity.ok(new ApplicationAdminGetDto(application));
                } else {
                    result = ResponseEntity.ok(new ApplicationDefaultGetDto(application));
                }
            }
        }

        return result;
    }

    /**
     * Returns a list of applications, that the size corresponds to the transferred
     *
     * @param pageable Data of elements quality
     * @return List of applications
     */
    @GetMapping
    @Operation(
            summary = "Get apps",
            description = "Returns a list of applications, that the size corresponds to the transferred"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns requested applications",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractApplicationGetDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content) })
    public ResponseEntity<?> getApplications (@ParameterObject Pageable pageable) {
        Authorities userAuthority = authenticationService.getAuthenticationAuthority();
        List<AbstractApplicationGetDto> result = new ArrayList<>();

        if (userAuthority == null) {
            List<Application> applications = applicationService.getApplications(pageable);
            result.addAll(applications.stream().map(ApplicationDefaultGetDto::new).toList());
        } else {
            String authUsername = authenticationService.getAuthenticationName();
            List<Application> applications = applicationService.getApplications(pageable);
            fillApplicationByUserAuthority(userAuthority, result, authUsername, applications);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Fills the resulting list with applications according to the level of user access to them
     *
     * @param userAuthority Level of user access
     * @param result Result list
     * @param authUsername Authenticated user
     * @param applications Base application list
     */
    private void fillApplicationByUserAuthority(Authorities userAuthority, List<AbstractApplicationGetDto> result, String authUsername, List<Application> applications) {
        switch (userAuthority) {
            case USER -> {
                result.addAll(
                        applications.stream()
                                .filter(item -> !item.getUser().getUsername().equals(authUsername))
                                .map(ApplicationDefaultGetDto::new)
                                .toList()
                );
                result.addAll(
                        applications.stream()
                                .filter(item -> item.getUser().getUsername().equals(authUsername))
                                .map(ApplicationAdminGetDto::new)
                                .toList()
                );
            }
            case ADMIN -> result.addAll(applications.stream().map(ApplicationAdminGetDto::new).toList());
        }
    }

    /**
     * Returns a list of applications, that category corresponds request param categoryId
     *
     * @param categoryId Id of requested category
     * @param pageable Data of elements quality
     * @return List of category's applications
     */
    @GetMapping("/category")
    @Operation(
            summary = "Get apps by category id",
            description = "Returns a list of applications, that category corresponds request param categoryId"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns requested applications",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractApplicationGetDto.class))}),
            @ApiResponse(responseCode = "404", description = "Category with given id is absent", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content) })
    public ResponseEntity<?> getApplicationsByCategoryName (
            @Parameter(description = "Id of requested category")
            @RequestParam(name = "categoryId") String categoryId,
            @ParameterObject Pageable pageable) {
        if (NumParser.parseIntOrNull(categoryId) == null)
            return new ResponseEntity<>(new ResponseError("Invalid category ID"), HttpStatus.BAD_REQUEST);
        Authorities userAuthority = authenticationService.getAuthenticationAuthority();
        Category category = categoryService.getCategoryById(categoryId);
        if (Objects.isNull(category))
            return ResponseEntity.of(Optional.empty());
        List<AbstractApplicationGetDto> result = new ArrayList<>();
        if (userAuthority == null) {
            List<Application> applications = applicationService.getApplicationsByCategory(pageable, category);
            result.addAll(applications.stream().map(ApplicationDefaultGetDto::new).toList());
        }
        else {
            String authUsername = authenticationService.getAuthenticationName();
            List<Application> applications = applicationService.getApplicationsByCategory(pageable, category);
            fillApplicationByUserAuthority(userAuthority, result, authUsername, applications);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Create application that are based on request body data
     *
     * @param applicationPostDto Data of creatable application
     * @param logo Image of creatable application
     * @return Created application
     */
    @PostMapping
    @SecurityRequirement(name = "api")
    @Operation(
            summary = "Create application",
            description = "Create application that are based on request body data"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns created application",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractApplicationGetDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content) })
    public ResponseEntity<?> postApplication(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Creating application", required = true,
                content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = ApplicationPostDto.class))})
            @RequestBody ApplicationPostDto applicationPostDto,
            @Parameter(description = "Logo image")
            @RequestParam("logo") MultipartFile logo) {
        if (!Objects.requireNonNull(logo.getContentType()).contains("image"))
            return new ResponseEntity<>(new ResponseError("Invalid logo file type. Allow only image files"),
                    HttpStatus.BAD_REQUEST);
        String fileName = fileStorageService.storeFile(logo);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/image/")
                .path(fileName)
                .toUriString();
        applicationPostDto.setLogo(fileDownloadUri);
        applicationPostDto.setUser(authenticationService.getAuthenticatedUser());
        applicationPostDto.setLicense(licenseService.getLicenseById(applicationPostDto.getLicenseCode()));
        applicationPostDto.setCategory(categoryService.getCategoryById(applicationPostDto.getCategoryName()));
        Application application = applicationService.saveApplication(applicationPostDto);
        return new ResponseEntity<>(new ApplicationDefaultGetDto(application), HttpStatus.CREATED);
    }

    /**
     * Edit application that are based on request body data
     *
     * @param applicationPutDto Data of editable application
     * @param logo Logo image of editable application
     * @return Edited application
     */
    @PutMapping
    @SecurityRequirement(name = "api")
    @Operation(
            summary = "Edit application",
            description = "Edit application that are based on request body data"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns edited application",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractApplicationGetDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content)})
    public ResponseEntity<?> putApplication (
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Creating application", required = true,
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApplicationPutDto.class))})
            @RequestBody ApplicationPutDto applicationPutDto,
            @Parameter(description = "Logo image")
            @RequestParam("logo") MultipartFile logo) {
        User user = authenticationService.getAuthenticatedUser();
        if (!Objects.equals(user.getAuthority().getAuthority(), Authorities.ADMIN.name())
                && !applicationService.isUserOwner(applicationPutDto, user))
            return new ResponseEntity<>(new ResponseError("Access denied! You doesn't have rights to edit this application"),
                    HttpStatus.FORBIDDEN);
        if (!Objects.requireNonNull(logo.getContentType()).contains("image"))
            return new ResponseEntity<>(new ResponseError("Invalid logo file type. Allow only image files"),
                    HttpStatus.BAD_REQUEST);
        String fileName = fileStorageService.storeFile(logo);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/image/")
                .path(fileName)
                .toUriString();
        applicationPutDto.setLogo(fileDownloadUri);
        applicationPutDto.setLicense(licenseService.getLicenseById(applicationPutDto.getLicenseCode()));
        applicationPutDto.setCategory(categoryService.getCategoryById(applicationPutDto.getCategoryName()));
        Application result = applicationService.saveApplication(applicationPutDto);
        return new ResponseEntity<>(new ApplicationAdminGetDto(result), HttpStatus.OK);
    }

    /**
     * Delete application that are based on request body data
     *
     * @param applicationId ID of removable application
     * @return Status of removing
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "api")
    @Operation(
            summary = "Delete application",
            description = "Delete application that are based on request body data"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns edited application",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractApplicationGetDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content)})
    public ResponseEntity<?> deleteApplication (
            @Parameter(description = "Id of removable application")
            @PathVariable(name = "id") String applicationId) {
        if (NumParser.parseIntOrNull(applicationId) == null)
            return new ResponseEntity<>(new ResponseError("Invalid application ID"), HttpStatus.BAD_REQUEST);
        User user = authenticationService.getAuthenticatedUser();
        if (!Objects.equals(user.getAuthority().getAuthority(), Authorities.ADMIN.name())
                && !applicationService.isUserOwner(applicationId, user)) {
            return new ResponseEntity<>(new ResponseError("Access denied! You doesn't have rights to remove this application"),
                    HttpStatus.FORBIDDEN);
        }
        applicationService.deleteApplicationById(applicationId);
        return new ResponseEntity<>(new ResponseError("Application removed successfully!"), HttpStatus.OK);
    }
}
