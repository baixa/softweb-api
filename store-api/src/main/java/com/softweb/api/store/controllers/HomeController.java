package com.softweb.api.store.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Home controller
 */
@RestController
@RequestMapping("/")
@Hidden
public class HomeController {

    /**
     * Profile of Spring Cloud application
     */
    @Value("${application.profile.name}")
    private String profile;

    /**
     * Home endpoint, that info user about current profile
     *
     * @return message for user
     */
    @GetMapping
    public String main() {
        return "Welcome! Current profile is " + profile;
    }
}
