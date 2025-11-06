package com.skynet.config;

import com.skynet.model.Cliente;
import com.skynet.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Crear clientes de prueba si no existen
        if (clienteRepository.count() == 0) {
            // Clientes en Guatemala con coordenadas reales
            clienteRepository.save(new Cliente(
                "Empresa ABC S.A.", 
                "15 Calle 1-25, Zona 10, Guatemala City", 
                "1234-5678", 
                "contacto@empresaabc.com",
                14.6038,  // Latitud Guatemala City
                -90.5069  // Longitud Guatemala City
            ));
            
            clienteRepository.save(new Cliente(
                "Comercial XYZ Ltda.", 
                "8a Avenida 12-45, Zona 1, Quetzaltenango", 
                "2345-6789", 
                "ventas@comercialxyz.com",
                14.8347,  // Latitud Quetzaltenango
                -91.5181  // Longitud Quetzaltenango
            ));
            
            clienteRepository.save(new Cliente(
                "Servicios Técnicos Modernos", 
                "5a Calle 8-72, Zona 3, Antigua Guatemala", 
                "3456-7890", 
                "info@serviciostecnicos.com",
                14.5586,  // Latitud Antigua Guatemala
                -90.7295  // Longitud Antigua Guatemala
            ));
            
            System.out.println("✅ Clientes de prueba creados con geolocalización");
            System.out.println("   🏢 3 clientes disponibles para pruebas");
        }
    }
}
