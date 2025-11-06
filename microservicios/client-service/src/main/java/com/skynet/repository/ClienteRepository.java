package com.skynet.repository;

import com.skynet.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
    boolean existsByEmail(String email);
    
    @Query("SELECT c FROM Cliente c WHERE c.latitud IS NOT NULL AND c.longitud IS NOT NULL")
    List<Cliente> findClientesConGeolocalizacion();
}
