package com.skynet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }

    @GetMapping("/health")
    public String health() {
        return "✅ Client Service is RUNNING - SkyNet System";
    }
    
    @GetMapping("/")
    public String home() {
        return "🏢 SkyNet Client Service - Gestión de Clientes";
    }
}
