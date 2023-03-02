package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.application.*;
import com.softweb.api.store.model.entities.Application;
import com.softweb.api.store.model.entities.Authorities;
import com.softweb.api.store.model.entities.Category;
import com.softweb.api.store.model.entities.User;
import com.softweb.api.store.services.ApplicationService;
import com.softweb.api.store.services.AuthenticationService;
import com.softweb.api.store.services.CategoryService;
import com.softweb.api.store.services.LicenseService;
import com.softweb.api.store.utils.NumParser;
import com.softweb.api.store.utils.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/v1/application")
public class ApplicationController {
    private final ApplicationService applicationService;
    private final AuthenticationService authenticationService;
    private final LicenseService licenseService;
    private final CategoryService categoryService;

    public ApplicationController(ApplicationService applicationService, AuthenticationService authenticationService, LicenseService licenseService, CategoryService categoryService) {
        this.applicationService = applicationService;
        this.authenticationService = authenticationService;
        this.licenseService = licenseService;
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getApplicationById (@PathVariable(name = "id") String applicationId) {
        Application application = applicationService.getApplicationById(applicationId);
        Authorities userAuthority = authenticationService.getAuthenticationAuthority();

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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getApplications (@RequestParam(name = "page") String page) {
        Integer pageValue = StringUtil.isBlankString(page) ? Integer.valueOf(0) : NumParser.parseIntOrNull(page);
        Authorities userAuthority = authenticationService.getAuthenticationAuthority();
        List<AbstractApplicationGetDto> result = new ArrayList<>();
        if (userAuthority == null) {
            List<Application> applications = applicationService.getApplications(pageValue);
            result.addAll(applications.stream().map(ApplicationDefaultGetDto::new).toList());
        }
        else {
            String authUsername = authenticationService.getAuthenticationName();
            List<Application> applications = applicationService.getApplications(pageValue);
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
        return ResponseEntity.ok(result);
    }

    @GetMapping("/category")
    public ResponseEntity<?> getApplicationsByCategoryName (@RequestParam(name = "page") String page,
                                                            @RequestParam(name = "categoryId") String categoryId) {
        Integer pageValue = StringUtil.isBlankString(page) ? Integer.valueOf(0) : NumParser.parseIntOrNull(page);
        Authorities userAuthority = authenticationService.getAuthenticationAuthority();
        Category category = categoryService.getCategoryById(categoryId);
        if (Objects.isNull(category))
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        List<AbstractApplicationGetDto> result = new ArrayList<>();
        if (userAuthority == null) {
            List<Application> applications = applicationService.getApplicationsByCategory(pageValue, category);
            result.addAll(applications.stream().map(ApplicationDefaultGetDto::new).toList());
        }
        else {
            String authUsername = authenticationService.getAuthenticationName();
            List<Application> applications = applicationService.getApplicationsByCategory(pageValue, category);
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
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> postApplication(@RequestBody ApplicationPostDto applicationPostDto) {
        applicationPostDto.setUser(authenticationService.getAuthenticatedUser());
        applicationPostDto.setLicense(licenseService.getLicenseById(applicationPostDto.getLicenseCode()));
        applicationPostDto.setCategory(categoryService.getCategoryById(applicationPostDto.getCategoryName()));
        applicationService.saveApplication(applicationPostDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> putApplication (@RequestBody ApplicationPutDto applicationPutDto) {
        User user = authenticationService.getAuthenticatedUser();
        if (!Objects.equals(user.getAuthority().getAuthority(), Authorities.ADMIN.name()) && !applicationService.isUserOwner(applicationPutDto, user)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        applicationPutDto.setLicense(licenseService.getLicenseById(applicationPutDto.getLicenseCode()));
        applicationPutDto.setCategory(categoryService.getCategoryById(applicationPutDto.getCategoryName()));
        Application result = applicationService.saveApplication(applicationPutDto);
        if (Objects.isNull(result)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new ApplicationAdminGetDto(result), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApplication (@PathVariable(name = "id") String applicationId) {
        User user = authenticationService.getAuthenticatedUser();
        if (!Objects.equals(user.getAuthority().getAuthority(), Authorities.ADMIN.name()) && !applicationService.isUserOwner(applicationId, user)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        applicationService.deleteApplicationById(applicationId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
