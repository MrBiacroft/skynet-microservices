package com.skynet.controller;

import com.skynet.model.Cliente;
import com.skynet.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping
    public List<Cliente> obtenerTodosClientes() {
        return clienteService.obtenerTodosClientes();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.obtenerClientePorId(id);
        return cliente.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> crearCliente(@Valid @RequestBody Cliente cliente) {
        // Validar email único
        if (clienteService.existeClienteConEmail(cliente.getEmail())) {
            return ResponseEntity.badRequest()
                    .body("Error: Ya existe un cliente con ese email");
        }
        
        Cliente clienteGuardado = clienteService.guardarCliente(cliente);
        return ResponseEntity.ok(clienteGuardado);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        if (!clienteService.obtenerClientePorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        cliente.setId(id);
        Cliente clienteActualizado = clienteService.guardarCliente(cliente);
        return ResponseEntity.ok(clienteActualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        if (!clienteService.obtenerClientePorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/buscar")
    public List<Cliente> buscarClientesPorNombre(@RequestParam String nombre) {
        return clienteService.buscarPorNombre(nombre);
    }
    
    @GetMapping("/geolocalizacion")
    public List<Cliente> obtenerClientesConGeolocalizacion() {
        return clienteService.obtenerClientesConGeolocalizacion();
    }
}
