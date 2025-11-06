package com.skynet.controller;

import com.skynet.model.Visita;
import com.skynet.service.VisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/visitas")
@CrossOrigin(origins = "*")
public class VisitaController {
    
    @Autowired
    private VisitaService visitaService;
    
    @GetMapping
    public List<Visita> obtenerTodasVisitas() {
        return visitaService.obtenerTodasVisitas();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Visita> obtenerVisitaPorId(@PathVariable Long id) {
        Optional<Visita> visita = visitaService.obtenerVisitaPorId(id);
        return visita.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Visita crearVisita(@RequestBody Visita visita) {
        return visitaService.guardarVisita(visita);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Visita> actualizarVisita(@PathVariable Long id, @RequestBody Visita visita) {
        if (!visitaService.obtenerVisitaPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        visita.setId(id);
        Visita visitaActualizada = visitaService.guardarVisita(visita);
        return ResponseEntity.ok(visitaActualizada);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVisita(@PathVariable Long id) {
        if (!visitaService.obtenerVisitaPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        visitaService.eliminarVisita(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/tecnicos/{tecnicoId}")
    public List<Visita> obtenerVisitasPorTecnico(@PathVariable Long tecnicoId) {
        return visitaService.obtenerVisitasPorTecnico(tecnicoId);
    }
    
    @GetMapping("/tecnicos/{tecnicoId}/hoy")
    public List<Visita> obtenerVisitasHoyPorTecnico(@PathVariable Long tecnicoId) {
        return visitaService.obtenerVisitasHoyPorTecnico(tecnicoId);
    }
    
    @GetMapping("/supervisores/{supervisorId}")
    public List<Visita> obtenerVisitasPorSupervisor(@PathVariable Long supervisorId) {
        return visitaService.obtenerVisitasPorSupervisor(supervisorId);
    }
    
    @GetMapping("/estado/{estado}")
    public List<Visita> obtenerVisitasPorEstado(@PathVariable Visita.EstadoVisita estado) {
        return visitaService.obtenerVisitasPorEstado(estado);
    }
    
    @PostMapping("/{visitaId}/registrar-ingreso")
    public ResponseEntity<Visita> registrarIngreso(@PathVariable Long visitaId, 
                                                   @RequestBody Map<String, Double> geolocalizacion) {
        Double latitud = geolocalizacion.get("latitud");
        Double longitud = geolocalizacion.get("longitud");
        
        Visita visita = visitaService.registrarIngreso(visitaId, latitud, longitud);
        if (visita != null) {
            return ResponseEntity.ok(visita);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{visitaId}/registrar-egreso")
    public ResponseEntity<Visita> registrarEgreso(@PathVariable Long visitaId, 
                                                  @RequestBody Map<String, String> datos) {
        String reporte = datos.get("reporte");
        
        Visita visita = visitaService.registrarEgreso(visitaId, reporte);
        if (visita != null) {
            return ResponseEntity.ok(visita);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/hoy")
    public List<Visita> obtenerVisitasPlanificadasHoy() {
        return visitaService.obtenerVisitasPlanificadasHoy();
    }
}
