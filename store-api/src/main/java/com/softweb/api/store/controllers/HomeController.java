package com.softweb.api.store.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

//    @Value("${test.phrase}")
    private String phrase;

    @GetMapping
    public String main() {
        return "Welcome!";
    }

    @GetMapping
    public String verificateConfigServer() {
        return phrase;
    }
}
