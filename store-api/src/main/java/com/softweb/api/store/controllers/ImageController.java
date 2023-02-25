package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.image.UploadImageDto;
import com.softweb.api.store.model.entities.Application;
import com.softweb.api.store.model.entities.Authorities;
import com.softweb.api.store.model.entities.Image;
import com.softweb.api.store.model.entities.User;
import com.softweb.api.store.services.ApplicationService;
import com.softweb.api.store.services.AuthenticationService;
import com.softweb.api.store.services.FileStorageService;
import com.softweb.api.store.services.ImageService;
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

import static com.softweb.api.store.services.FileStorageService.getResourceAsResponseEntity;

@RestController
@RequestMapping(value = "/v1/image")
public class ImageController {
    private final ImageService imageService;
    private final AuthenticationService authenticationService;
    private final ApplicationService applicationService;
    private final FileStorageService fileStorageService;

    public ImageController(ImageService imageService, AuthenticationService authenticationService, ApplicationService applicationService, FileStorageService fileStorageService) {
        this.imageService = imageService;
        this.authenticationService = authenticationService;
        this.applicationService = applicationService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getImageByName(@PathVariable String fileName, HttpServletRequest request) throws Exception {
        return getResourceAsResponseEntity(fileName, request, fileStorageService);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String applicationId) {
        User authUser = authenticationService.getAuthenticatedUser();
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        Application application = applicationService.getApplicationById(applicationId);
        if (Objects.isNull(application))
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        if (!(Objects.equals(application.getUser().getId(), authUser.getId())) && authUserAuthority != Authorities.ADMIN)
           return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        if (!Objects.requireNonNull(file.getContentType()).contains("image"))
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/image/")
                .path(fileName)
                .toUriString();
        Image image = new Image();
        image.setApplication(application);
        image.setPath(fileDownloadUri);
        imageService.saveImage(image);
        return ResponseEntity.ok(new UploadImageDto(image.getId(), fileName, fileDownloadUri,
                file.getContentType(), file.getSize(), application));
    }

    @PostMapping("/uploadMultiple")
    public ResponseEntity<?> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
                                                 @RequestParam String applicationId) {
        User authUser = authenticationService.getAuthenticatedUser();
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        Application application = applicationService.getApplicationById(applicationId);
        if (Objects.isNull(application))
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        List<UploadImageDto> uploadImageDtos = new ArrayList<>();
        if (!(Objects.equals(application.getUser().getId(), authUser.getId())) && authUserAuthority != Authorities.ADMIN)
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        for (MultipartFile file : files)
            if (!Objects.requireNonNull(file.getContentType()).contains("image"))
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        List<Image> storedImages = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = fileStorageService.storeFile(file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/v1/image/")
                    .path(fileName)
                    .toUriString();
            Image image = new Image();
            image.setApplication(application);
            image.setPath(fileDownloadUri);
            storedImages.add(image);
            uploadImageDtos.add(new UploadImageDto(image.getId(), fileName, fileDownloadUri,
                    file.getContentType(), file.getSize(), application));
        }
        imageService.saveImages(storedImages);
        return ResponseEntity.ok(uploadImageDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable String id) {
        Image image = imageService.getImageById(id);
        if (image == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        User authUser = authenticationService.getAuthenticatedUser();
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        if (!(Objects.equals(image.getApplication().getUser().getId(), authUser.getId()))
                && authUserAuthority != Authorities.ADMIN)
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        imageService.deleteImageById(image.getId().toString());
        if (fileStorageService.removeFileByPath(image.getPath()))
            return new ResponseEntity<>(null, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
}
