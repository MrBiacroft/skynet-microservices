package com.skynet.config;

import com.skynet.model.Visita;
import com.skynet.repository.VisitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private VisitaRepository visitaRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Crear visitas de prueba si no existen
        if (visitaRepository.count() == 0) {
            // Visita 1 - Para hoy
            visitaRepository.save(new Visita(
                1L, "Empresa ABC S.A.", "contacto@empresaabc.com", 
                "15 Calle 1-25, Zona 10, Guatemala City", 14.6038, -90.5069,
                3L, "Carlos López Técnico", 2L,
                LocalDate.now(), LocalTime.of(9, 0)
            ));
            
            // Visita 2 - Para hoy
            visitaRepository.save(new Visita(
                2L, "Comercial XYZ Ltda.", "ventas@comercialxyz.com",
                "8a Avenida 12-45, Zona 1, Quetzaltenango", 14.8347, -91.5181,
                3L, "Carlos López Técnico", 2L,
                LocalDate.now(), LocalTime.of(11, 30)
            ));
            
            // Visita 3 - Para mañana
            visitaRepository.save(new Visita(
                3L, "Servicios Técnicos Modernos", "info@serviciostecnicos.com",
                "5a Calle 8-72, Zona 3, Antigua Guatemala", 14.5586, -90.7295,
                3L, "Carlos López Técnico", 2L,
                LocalDate.now().plusDays(1), LocalTime.of(14, 0)
            ));
            
            System.out.println("✅ Visitas de prueba creadas:");
            System.out.println("   📅 2 visitas para HOY");
            System.out.println("   📅 1 visita para MAÑANA");
            System.out.println("   👨‍💼 Todas asignadas a Carlos López Técnico");
        }
    }
}
