package com.skynet.config;

import com.skynet.model.Usuario;
import com.skynet.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Crear usuarios de prueba si no existen
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new Usuario(
                "admin@skynet.com", 
                "123456", 
                "Administrador Principal", 
                Usuario.Rol.ADMIN
            ));
            
            usuarioRepository.save(new Usuario(
                "supervisor@skynet.com", 
                "123456", 
                "Juan Pérez Supervisor", 
                Usuario.Rol.SUPERVISOR
            ));
            
            usuarioRepository.save(new Usuario(
                "tecnico@skynet.com", 
                "123456", 
                "Carlos López Técnico", 
                Usuario.Rol.TECNICO
            ));
            
            System.out.println("✅ Usuarios de prueba creados:");
            System.out.println("   👤 admin@skynet.com / 123456 (ADMIN)");
            System.out.println("   👤 supervisor@skynet.com / 123456 (SUPERVISOR)");
            System.out.println("   👤 tecnico@skynet.com / 123456 (TECNICO)");
        }
    }
}
