package com.skynet.service;

import com.skynet.model.Cliente;
import com.skynet.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    public List<Cliente> obtenerTodosClientes() {
        return clienteRepository.findAll();
    }
    
    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }
    
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
    
    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    public Optional<Cliente> obtenerClientePorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
    
    public List<Cliente> obtenerClientesConGeolocalizacion() {
        return clienteRepository.findClientesConGeolocalizacion();
    }
    
    public boolean existeClienteConEmail(String email) {
        return clienteRepository.existsByEmail(email);
    }
}
