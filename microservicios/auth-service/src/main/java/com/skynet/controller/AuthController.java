package com.skynet.controller;

import com.skynet.dto.LoginRequest;
import com.skynet.dto.LoginResponse;
import com.skynet.model.Usuario;
import com.skynet.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        boolean autenticado = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        
        if (autenticado) {
            Optional<Usuario> usuarioOpt = authService.obtenerUsuarioPorEmail(loginRequest.getEmail());
            if (usuarioOpt.isPresent()) {
                // En una aplicación real, aquí generaríamos un JWT token
                String tokenSimulado = "jwt-simulado-" + System.currentTimeMillis();
                
                LoginResponse response = new LoginResponse(
                    tokenSimulado, 
                    usuarioOpt.get(), 
                    "Login exitoso"
                );
                return ResponseEntity.ok(response);
            }
        }
        
        LoginResponse errorResponse = new LoginResponse(
            null, 
            null, 
            "Credenciales inválidas"
        );
        return ResponseEntity.status(401).body(errorResponse);
    }
    
    @GetMapping("/usuarios/{email}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable String email) {
        Optional<Usuario> usuario = authService.obtenerUsuarioPorEmail(email);
        return usuario.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
}
