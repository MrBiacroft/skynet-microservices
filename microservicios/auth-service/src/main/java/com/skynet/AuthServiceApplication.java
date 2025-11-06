package com.skynet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @GetMapping("/health")
    public String health() {
        return "✅ Auth Service is RUNNING - SkyNet System";
    }
    
    @GetMapping("/")
    public String home() {
        return "🚀 SkyNet Auth Service - FASE 2 COMPLETADA";
    }
}
