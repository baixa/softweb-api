package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.installer.UploadInstallerDto;
import com.softweb.api.store.model.entities.*;
import com.softweb.api.store.services.*;
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
@RequestMapping(value = "/v1/installer")
public class InstallerController {
    private final InstallerService installerService;
    private final FileStorageService fileStorageService;
    private final AuthenticationService authenticationService;
    private final ApplicationService applicationService;
    private final OperatingSystemService operatingSystemService;

    public InstallerController(InstallerService installerService, FileStorageService fileStorageService, AuthenticationService authenticationService, ApplicationService applicationService, OperatingSystemService operatingSystemService) {
        this.installerService = installerService;
        this.fileStorageService = fileStorageService;
        this.authenticationService = authenticationService;
        this.applicationService = applicationService;
        this.operatingSystemService = operatingSystemService;
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getInstallerByName(@PathVariable String fileName,
                                                       HttpServletRequest request) throws Exception {
        return getResourceAsResponseEntity(fileName, request, fileStorageService);
    }



    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam String applicationId,
                                        @RequestParam String systemId,
                                        @RequestParam String version) {
        User authUser = authenticationService.getAuthenticatedUser();
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        Application application = applicationService.getApplicationById(applicationId);
        OperatingSystem system = operatingSystemService.getOperatingSystemById(systemId);
        if (Objects.isNull(application) || Objects.isNull(system))
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        if (!(Objects.equals(application.getUser().getId(), authUser.getId())) && authUserAuthority != Authorities.ADMIN)
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        if (!Objects.requireNonNull(file.getContentType()).contains("application"))
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
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
        return ResponseEntity.ok(new UploadInstallerDto(installer.getId(), fileName, fileDownloadUri,
                file.getContentType(), file.getSize(), application, system));
    }

    @PostMapping("/uploadMultiple")
    public ResponseEntity<?> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
                                                 @RequestParam String applicationId,
                                                 @RequestParam String systemId,
                                                 @RequestParam String version) {
        User authUser = authenticationService.getAuthenticatedUser();
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        Application application = applicationService.getApplicationById(applicationId);
        OperatingSystem system = operatingSystemService.getOperatingSystemById(systemId);
        if (Objects.isNull(application) || Objects.isNull(system))
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        List<UploadInstallerDto> uploadInstallerDtos = new ArrayList<>();
        if (!(Objects.equals(application.getUser().getId(), authUser.getId())) && authUserAuthority != Authorities.ADMIN)
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        for (MultipartFile file : files)
            if (!Objects.requireNonNull(file.getContentType()).contains("application"))
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
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
        return ResponseEntity.ok(uploadInstallerDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInstaller(@PathVariable String id) {
        Installer installer = installerService.getInstallerById(id);
        if (installer == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        User authUser = authenticationService.getAuthenticatedUser();
        Authorities authUserAuthority = authenticationService.getAuthenticationAuthority();
        if (!(Objects.equals(installer.getApplication().getUser().getId(), authUser.getId()))
                && authUserAuthority != Authorities.ADMIN)
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        installerService.deleteInstallerById(installer.getId().toString());
        if (fileStorageService.removeFileByPath(installer.getInstallerPath()))
            return new ResponseEntity<>(null, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
}