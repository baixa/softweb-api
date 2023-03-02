package com.softweb.api.store.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @Value("${application.profile.name}")
    private String profile;

    @GetMapping
    public String main() {
        return "Welcome! Current profile is " + profile;
    }
}
