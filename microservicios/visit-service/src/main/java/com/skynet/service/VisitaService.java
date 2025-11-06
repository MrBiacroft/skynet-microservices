package com.skynet.service;

import com.skynet.model.Visita;
import com.skynet.repository.VisitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VisitaService {
    
    @Autowired
    private VisitaRepository visitaRepository;
    
    @Autowired
    private EmailService emailService;
    
    public List<Visita> obtenerTodasVisitas() {
        return visitaRepository.findAll();
    }
    
    public Optional<Visita> obtenerVisitaPorId(Long id) {
        return visitaRepository.findById(id);
    }
    
    public Visita guardarVisita(Visita visita) {
        return visitaRepository.save(visita);
    }
    
    public void eliminarVisita(Long id) {
        visitaRepository.deleteById(id);
    }
    
    public List<Visita> obtenerVisitasPorTecnico(Long tecnicoId) {
        return visitaRepository.findByTecnicoId(tecnicoId);
    }
    
    public List<Visita> obtenerVisitasPorSupervisor(Long supervisorId) {
        return visitaRepository.findBySupervisorId(supervisorId);
    }
    
    public List<Visita> obtenerVisitasHoyPorTecnico(Long tecnicoId) {
        return visitaRepository.findVisitasHoyPorTecnico(tecnicoId, LocalDate.now());
    }
    
    public List<Visita> obtenerVisitasPorEstado(Visita.EstadoVisita estado) {
        return visitaRepository.findByEstado(estado);
    }
    
    public Visita registrarIngreso(Long visitaId, Double latitud, Double longitud) {
        Optional<Visita> visitaOpt = visitaRepository.findById(visitaId);
        if (visitaOpt.isPresent()) {
            Visita visita = visitaOpt.get();
            visita.setEstado(Visita.EstadoVisita.EN_CURSO);
            visita.setFechaIngreso(LocalDateTime.now());
            visita.setLatitudIngreso(latitud);
            visita.setLongitudIngreso(longitud);
            return visitaRepository.save(visita);
        }
        return null;
    }
    
    public Visita registrarEgreso(Long visitaId, String reporte) {
        Optional<Visita> visitaOpt = visitaRepository.findById(visitaId);
        if (visitaOpt.isPresent()) {
            Visita visita = visitaOpt.get();
            visita.setEstado(Visita.EstadoVisita.COMPLETADA);
            visita.setFechaEgreso(LocalDateTime.now());
            visita.setReporte(reporte);
            
            Visita visitaCompletada = visitaRepository.save(visita);
            
            // Enviar email con reporte al cliente
            emailService.enviarReporteVisita(visitaCompletada);
            
            return visitaCompletada;
        }
        return null;
    }
    
    public List<Visita> obtenerVisitasPlanificadasHoy() {
        return visitaRepository.findByFechaPlanificada(LocalDate.now());
    }
}
