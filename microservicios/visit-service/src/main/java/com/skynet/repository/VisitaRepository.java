package com.skynet.repository;

import com.skynet.model.Visita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface VisitaRepository extends JpaRepository<Visita, Long> {
    
    // Encontrar visitas por técnico
    List<Visita> findByTecnicoId(Long tecnicoId);
    
    // Encontrar visitas por supervisor
    List<Visita> findBySupervisorId(Long supervisorId);
    
    // Encontrar visitas por estado
    List<Visita> findByEstado(Visita.EstadoVisita estado);
    
    // Encontrar visitas de hoy para un técnico
    @Query("SELECT v FROM Visita v WHERE v.tecnicoId = :tecnicoId AND v.fechaPlanificada = :fecha")
    List<Visita> findVisitasHoyPorTecnico(@Param("tecnicoId") Long tecnicoId, @Param("fecha") LocalDate fecha);
    
    // Encontrar visitas planificadas para una fecha
    List<Visita> findByFechaPlanificada(LocalDate fechaPlanificada);
    
    // Encontrar visitas por cliente
    List<Visita> findByClienteId(Long clienteId);
}
