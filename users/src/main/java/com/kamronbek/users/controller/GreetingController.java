package com.kamronbek.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/greeting")
public class GreetingController {

    @GetMapping
    public String greeting() {
        return "Hello, k8s!";
    }
}
