package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.application.AbstractApplicationGetDto;
import com.softweb.api.store.model.dto.image.UploadImageDto;
import com.softweb.api.store.model.entities.*;
import com.softweb.api.store.services.ApplicationService;
import com.softweb.api.store.services.AuthenticationService;
import com.softweb.api.store.services.FileStorageService;
import com.softweb.api.store.services.ImageService;
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
import org.springframework.http.MediaType;
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
 * Controller for applications' images
 * @see Image
 */
@RestController
@RequestMapping(value = "/v1/image")
@Tag(name = "Image", description = "API provides ability to manipulate applications' images")
public class ImageController {

    /**
     * Service, that provides ability to interaction with the Image entity
     */
    private final ImageService imageService;

    /**
     * Service providing information about an authorized user
     */
    private final AuthenticationService authenticationService;

    /**
     * Service, that provides ability to interaction with the Application entity
     */
    private final ApplicationService applicationService;

    /**
     * Service, that provides ability manipulate with static files
     */
    private final FileStorageService fileStorageService;

    /**
     * Controller
     *
     * @param imageService Service of images
     * @param authenticationService Service of authentication
     * @param applicationService Service of applications
     * @param fileStorageService Service of static files
     */
    public ImageController(ImageService imageService,
                           AuthenticationService authenticationService,
                           ApplicationService applicationService,
                           FileStorageService fileStorageService) {
        this.imageService = imageService;
        this.authenticationService = authenticationService;
        this.applicationService = applicationService;
        this.fileStorageService = fileStorageService;
    }

    /**
     * Get image-file by its name
     *
     * @param fileName Name of image-file
     * @param request Current http request
     * @return Requested image
     */
    @GetMapping("/{fileName:.+}")
    @Operation(
            summary = "Get image by file name",
            description = "Returns an image according to the passed filename"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns requested image"),
            @ApiResponse(responseCode = "404", description = "File not found"),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied") })
    public ResponseEntity<Resource> getImageByName(
            @Parameter(description = "Name of requested file")
            @PathVariable String fileName,
            HttpServletRequest request) {
        return getResourceAsResponseEntity(fileName, request, fileStorageService);
    }

    /**
     * Upload and save image in resource folder
     *
     * @param file Savable file
     * @param applicationId ID of linked application
     * @return Saved image
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "api")
    @Operation(
            summary = "Upload application image to server",
            description = "Upload application image to server and save it as Image object"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns created image object",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UploadImageDto.class))}),
            @ApiResponse(responseCode = "404", description = "Linked entity not found"),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied"),
            @ApiResponse(responseCode = "403", description = "Access denied")})
    public ResponseEntity<?> uploadFile(
            @Parameter(description = "Upload image file")
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "ID of linked application")
            @RequestParam String applicationId) {
        User authUser = authenticationService.getAuthenticatedUser();
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        Application application = applicationService.getApplicationById(applicationId);
        if (Objects.isNull(application))
            return ResponseEntity.of(Optional.empty());
        if (!(Objects.equals(application.getUser().getId(), authUser.getId())) && authUserAuthority != Authorities.ADMIN)
           return new ResponseEntity<>(new ResponseError("Access denied. You don't have rights to edit this application"),
                   HttpStatus.FORBIDDEN);
        if (!Objects.requireNonNull(file.getContentType()).contains("image"))
            return new ResponseEntity<>("Invalid file supplied! Available loading only image files",
                    HttpStatus.BAD_REQUEST);
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port("8072")
                .path("/store/v1/image/")
                .path(fileName)
                .toUriString();
        Image image = new Image();
        image.setApplication(application);
        image.setPath(fileDownloadUri);
        imageService.saveImage(image);
        return new ResponseEntity<>(new UploadImageDto(image.getId(), fileName, fileDownloadUri,
                file.getContentType(), file.getSize(), application), HttpStatus.CREATED);
    }

    /**
     * Upload and save images in resource folder
     *
     * @param files Savable files
     * @param applicationId ID of linked application
     * @return Saved images
     */
    @PostMapping("/uploadMultiple")
    @SecurityRequirement(name = "api")
    @Operation(
            summary = "Upload multiple application images to server",
            description = "Upload application images to server and save them as Image objects"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns created image objects",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UploadImageDto.class))}),
            @ApiResponse(responseCode = "404", description = "Linked entities not found"),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied"),
            @ApiResponse(responseCode = "403", description = "Access denied")})
    public ResponseEntity<?> uploadMultipleFiles(
            @Parameter(description = "Upload image files")
            @RequestParam("files") MultipartFile[] files,
            @Parameter(description = "Linked application ID")
            @RequestParam String applicationId) {
        User authUser = authenticationService.getAuthenticatedUser();
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        Application application = applicationService.getApplicationById(applicationId);
        if (Objects.isNull(application))
            return ResponseEntity.of(Optional.empty());
        List<UploadImageDto> uploadImageDtos = new ArrayList<>();
        if (!(Objects.equals(application.getUser().getId(), authUser.getId())) && authUserAuthority != Authorities.ADMIN)
            return new ResponseEntity<>(new ResponseError("Access denied. You don't have rights to edit this application"),
                    HttpStatus.FORBIDDEN);
        for (MultipartFile file : files)
            if (!Objects.requireNonNull(file.getContentType()).contains("image"))
                return new ResponseEntity<>("Invalid file supplied! Available loading only image files",
                        HttpStatus.BAD_REQUEST);
        List<Image> storedImages = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = fileStorageService.storeFile(file);
            String fileDownloadUri = ServletUriComponentsBuilder.newInstance()
                    .scheme("http")
                    .host("localhost")
                    .port("8072")
                    .path("/store/v1/image/")
                    .path(fileName)
                    .toUriString();
            Image image = new Image();
            image.setApplication(application);
            image.setPath(fileDownloadUri);
            storedImages.add(image);
        }
        storedImages = imageService.saveImages(storedImages);
        for (int i = 0; i < files.length; i++) {
            Image image = storedImages.get(i);
            MultipartFile file = files[i];
            uploadImageDtos.add(new UploadImageDto(image.getId(), imageService.getFileName(image), image.getPath(),
                    file.getContentType(), file.getSize(), application));
        }
        return new ResponseEntity<>(uploadImageDtos, HttpStatus.CREATED);
    }

    /**
     * Remove image by id
     *
     * @param id ID of removable image
     * @return status of removing
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "api")
    @Operation(
            summary = "Delete image",
            description = "Delete image by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns status of image removing",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractApplicationGetDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Image not found")})
    public ResponseEntity<?> deleteImage(@PathVariable String id) {
        Image image = imageService.getImageById(id);
        if (Objects.isNull(image))
            return ResponseEntity.of(Optional.empty());
        User authUser = authenticationService.getAuthenticatedUser();
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        if (!(Objects.equals(image.getApplication().getUser().getId(), authUser.getId()))
                && authUserAuthority != Authorities.ADMIN)
            return new ResponseEntity<>(new ResponseError("Access denied. You don't have rights to edit this application"),
                    HttpStatus.FORBIDDEN);
        imageService.deleteImageById(image.getId().toString());

        if (imageService.getCountImagesByPath(image.getPath()) == 0) {
            if (fileStorageService.removeFileByPath(image.getPath()))
                return new ResponseEntity<>(new ResponseError("Image removed successfully!"), HttpStatus.OK);
            else
                return new ResponseEntity<>(new ResponseError("Access denied"), HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(new ResponseError("Image removed successfully!"), HttpStatus.OK);
        }

    }
}
