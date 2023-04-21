package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.application.AbstractApplicationGetDto;
import com.softweb.api.store.model.dto.application.ApplicationDefaultGetDto;
import com.softweb.api.store.model.dto.application.ApplicationPostDto;
import com.softweb.api.store.model.dto.application.ApplicationPutDto;
import com.softweb.api.store.model.entities.*;
import com.softweb.api.store.services.*;
import com.softweb.api.store.utils.CollectionsInfoResponse;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Controller for published applications. Provides getting, creating, modifying, and deleting developer applications
 * @see Application
 */
@RestController
@RequestMapping(value = "/v1/application")
@Tag(name = "Application", description = "API provides ability to manipulate users' applications")
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
     * Service, that provides ability to interaction with the User entity
     */
    private final UserService userService;

    /**
     * Service, that provides ability manipulate with static files
     */
    private final FileStorageService fileStorageService;

    /**
     * Controller
     *
     * @param applicationService    Application service
     * @param authenticationService Authentication service
     * @param licenseService        License service
     * @param categoryService       Category service
     * @param userService           User service
     * @param fileStorageService    FileStorage service
     */
    public ApplicationController(ApplicationService applicationService,
                                 AuthenticationService authenticationService,
                                 LicenseService licenseService,
                                 CategoryService categoryService,
                                 UserService userService,
                                 FileStorageService fileStorageService) {
        this.applicationService = applicationService;
        this.authenticationService = authenticationService;
        this.licenseService = licenseService;
        this.categoryService = categoryService;
        this.userService = userService;
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
    public ResponseEntity<?> getApplicationById (@PathVariable(name = "id") String applicationId) {
        if (NumParser.parseIntOrNull(applicationId) == null)
            return new ResponseEntity<>(new ResponseError("Invalid ID"), HttpStatus.BAD_REQUEST);
        Application application = applicationService.getApplicationById(applicationId);
        if (Objects.isNull(application))
            return ResponseEntity.of(Optional.empty());
        else {
            application.view();
            applicationService.saveApplication(application);
            return ResponseEntity.ok(new ApplicationDefaultGetDto(application));
        }
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
        List<Application> applications = applicationService.getApplications(pageable);
        List<ApplicationDefaultGetDto> result = applications.stream().map(ApplicationDefaultGetDto::new).toList();
        return ResponseEntity.ok(result);
    }

    /**
     * Returns info about list of applications (total count of elements, count of pages) by size of page
     *
     * @param size Size of requested page
     * @return Info about list of applications
     */
    @GetMapping("/info")
    @Operation(
            summary = "Get info about list of applications",
            description = "Returns a list of applications (total count of elements, count of pages) by size of page"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns requested info",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CollectionsInfoResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content) })
    public ResponseEntity<?> getApplicationsInfo (@RequestParam Integer size) {
        Long applicationsCount = applicationService.getApplicationsCount();
        return ResponseEntity.ok(new CollectionsInfoResponse(applicationsCount, size));
    }

    /**
     * Returns a list of applications, that category corresponds request param categoryId
     *
     * @param categoryId ID of requested category
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
    public ResponseEntity<?> getApplicationsByCategory(
            @Parameter(description = "Id of requested category")
            @RequestParam(name = "categoryId") String categoryId,
            @ParameterObject Pageable pageable) {
        if (NumParser.parseIntOrNull(categoryId) == null)
            return new ResponseEntity<>(new ResponseError("Invalid category ID"), HttpStatus.BAD_REQUEST);
        Category category = categoryService.getCategoryById(categoryId);
        if (Objects.isNull(category))
            return ResponseEntity.of(Optional.empty());
        List<Application> applications = applicationService.getApplicationsByCategory(pageable, category);
        List<ApplicationDefaultGetDto> result = applications.stream().map(ApplicationDefaultGetDto::new).toList();
        return ResponseEntity.ok(result);
    }

    /**
     * Returns info about list of applications by category (total count of elements, count of pages) by size of page
     *
     * @param size Size of requested page
     * @return Info about list of applications
     */
    @GetMapping("/category/info")
    @Operation(
            summary = "Get info about list of applications by category",
            description = "Returns a list of applications by category (total count of elements, count of pages) by size of page"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns requested info",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CollectionsInfoResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content) })
    public ResponseEntity<?> getApplicationsInfoByCategory (@RequestParam Integer size, @RequestParam String categoryId) {
        if (NumParser.parseIntOrNull(categoryId) == null)
            return new ResponseEntity<>(new ResponseError("Invalid category ID"), HttpStatus.BAD_REQUEST);
        Category category = categoryService.getCategoryById(categoryId);
        if (Objects.isNull(category))
            return ResponseEntity.of(Optional.empty());
        Long applicationsCount = applicationService.getApplicationsCountByCategory(category);
        return ResponseEntity.ok(new CollectionsInfoResponse(applicationsCount, size));
    }

    /**
     * Returns a list of applications, that user corresponds request param userId
     *
     * @param userId Id of requested user
     * @param pageable Data of elements quality
     * @return List of user's applications
     */
    @GetMapping("/user")
    @Operation(
            summary = "Get apps by user id",
            description = "Returns a list of applications, that user corresponds request param userId"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns requested applications",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractApplicationGetDto.class))}),
            @ApiResponse(responseCode = "404", description = "User with given id is absent", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content) })
    public ResponseEntity<?> getApplicationsByUser (
            @Parameter(description = "Id of requested user")
            @RequestParam(name = "userId") String userId,
            @ParameterObject Pageable pageable) {
        if (NumParser.parseIntOrNull(userId) == null)
            return new ResponseEntity<>(new ResponseError("Invalid user ID"), HttpStatus.BAD_REQUEST);
        User user = userService.getUserById(userId);
        if (Objects.isNull(user))
            return ResponseEntity.of(Optional.empty());
        List<Application> applications = applicationService.getApplicationsByUser(pageable, user);
        List<ApplicationDefaultGetDto> result = applications.stream().map(ApplicationDefaultGetDto::new).toList();
        return ResponseEntity.ok(result);
    }

    /**
     * Returns info about list of applications by user (total count of elements, count of pages) by size of page
     *
     * @param size Size of requested page
     * @return Info about list of applications
     */
    @GetMapping("/user/info")
    @Operation(
            summary = "Get info about list of applications by user",
            description = "Returns a list of applications by user (total count of elements, count of pages) by size of page"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns requested info",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CollectionsInfoResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content) })
    public ResponseEntity<?> getApplicationsInfoByUser (@RequestParam Integer size, @RequestParam String userId) {
        if (NumParser.parseIntOrNull(userId) == null)
            return new ResponseEntity<>(new ResponseError("Invalid user ID"), HttpStatus.BAD_REQUEST);
        User user = userService.getUserById(userId);
        if (Objects.isNull(user))
            return ResponseEntity.of(Optional.empty());
        Long applicationsCount = applicationService.getApplicationsCountByUser(user);
        return ResponseEntity.ok(new CollectionsInfoResponse(applicationsCount, size));
    }

    /**
     * Create application that are based on request body data
     *
     * @param name Name of creatable application
     * @param shortDescription Short description of creatable application
     * @param longDescription Long description of creatable application
     * @param licenseCode License code of creatable application
     * @param categoryId Category id of creatable application
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
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content) })
    public ResponseEntity<?> postApplication(
            @Parameter(description = "Logo image")
            @RequestParam("logo") MultipartFile logo,
            @Parameter(description = "Application name")
            @RequestParam String name,
            @Parameter(description = "Application short description")
            @RequestParam String shortDescription,
            @Parameter(description = "Application long description")
            @RequestParam String longDescription,
            @Parameter(description = "License code")
            @RequestParam String licenseCode,
            @Parameter(description = "Category id")
            @RequestParam String categoryId) {
        if (!Objects.requireNonNull(logo.getContentType()).contains("image"))
            return new ResponseEntity<>(new ResponseError("Invalid logo file type. Allow only image files"),
                    HttpStatus.BAD_REQUEST);

        if (NumParser.parseIntOrNull(categoryId) == null)
            return new ResponseEntity<>(new ResponseError("Invalid categoryId"),
                    HttpStatus.BAD_REQUEST);

        Category category = categoryService.getCategoryById(categoryId);
        License license = licenseService.getLicenseById(licenseCode);
        if (Objects.isNull(category) || Objects.isNull(license))
            return ResponseEntity.of(Optional.empty());

        ApplicationPostDto applicationPostDto = new ApplicationPostDto(name, shortDescription,
                longDescription, license, category);
        String fileName = fileStorageService.storeFile(logo);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/image/")
                .path(fileName)
                .toUriString();
        applicationPostDto.setLogo(fileDownloadUri);
        applicationPostDto.setUser(authenticationService.getAuthenticatedUser());
        Application application = applicationService.saveApplication(applicationPostDto);
        return new ResponseEntity<>(new ApplicationDefaultGetDto(application), HttpStatus.CREATED);
    }

    /**
     * Edit application that are based on request body data
     *
     * @param id ID of editable application
     * @param name Name of editable application
     * @param shortDescription Short description of editable application
     * @param longDescription Long description of editable application
     * @param categoryId Category ID of editable application
     * @param licenseCode License code of editable application
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
            @Parameter(description = "Logo image")
            @RequestParam("logo") MultipartFile logo,
            @Parameter(description = "Application id")
            @RequestParam String id,
            @Parameter(description = "Application name")
            @RequestParam String name,
            @Parameter(description = "Application short description")
            @RequestParam String shortDescription,
            @Parameter(description = "Application long description")
            @RequestParam String longDescription,
            @Parameter(description = "License code")
            @RequestParam String licenseCode,
            @Parameter(description = "Category id")
            @RequestParam String categoryId) {

        if (NumParser.parseIntOrNull(categoryId, id) == null)
            return new ResponseEntity<>(new ResponseError("Invalid id"),
                    HttpStatus.BAD_REQUEST);

        Category category = categoryService.getCategoryById(categoryId);
        License license = licenseService.getLicenseById(licenseCode);
        if (Objects.isNull(category) || Objects.isNull(license))
            return ResponseEntity.of(Optional.empty());

        ApplicationPutDto applicationPutDto = new ApplicationPutDto(id, name, shortDescription, longDescription, license, category);

        User user = authenticationService.getAuthenticatedUser();
        if (!Objects.equals(user.getAuthority().getAuthority(), Authorities.ADMIN.name())
                && !applicationService.isUserOwner(applicationPutDto, user))
            return new ResponseEntity<>(new ResponseError("Access denied! You don't have rights to edit this application"),
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
        Application result = applicationService.saveApplication(applicationPutDto);
        return new ResponseEntity<>(new ApplicationDefaultGetDto(result), HttpStatus.OK);
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
            @ApiResponse(responseCode = "200", description = "Returns status of application removing"),
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
            return new ResponseEntity<>(new ResponseError("Access denied! You don't have rights to remove this application"),
                    HttpStatus.FORBIDDEN);
        }
        applicationService.deleteApplicationById(applicationId);
        return new ResponseEntity<>(new ResponseError("Application removed successfully!"), HttpStatus.OK);
    }
}
