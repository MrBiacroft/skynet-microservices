package com.skynet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VisitServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VisitServiceApplication.class, args);
    }

    @GetMapping("/health")
    public String health() {
        return "✅ Visit Service is RUNNING - SkyNet System";
    }
    
    @GetMapping("/")
    public String home() {
        return "📅 SkyNet Visit Service - Gestión de Visitas Técnicas";
    }
}
