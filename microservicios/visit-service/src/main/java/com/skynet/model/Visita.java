package com.skynet.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "visitas")
public class Visita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Datos del cliente (en un sistema real, sería una relación con client-service)
    private Long clienteId;
    private String clienteNombre;
    private String clienteEmail;
    private String clienteDireccion;
    private Double clienteLatitud;
    private Double clienteLongitud;
    
    // Datos del técnico
    private Long tecnicoId;
    private String tecnicoNombre;
    
    // Datos del supervisor
    private Long supervisorId;
    
    // Planificación de la visita
    private LocalDate fechaPlanificada;
    private LocalTime horaPlanificada;
    
    // Estado de la visita
    @Enumerated(EnumType.STRING)
    private EstadoVisita estado;
    
    // Registro de ejecución
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaEgreso;
    private Double latitudIngreso;
    private Double longitudIngreso;
    
    // Reporte de la visita
    @Column(columnDefinition = "TEXT")
    private String reporte;
    
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    
    // Enums para estados
    public enum EstadoVisita {
        PLANIFICADA, EN_CURSO, COMPLETADA, CANCELADA
    }
    
    // Constructores
    public Visita() {
        this.estado = EstadoVisita.PLANIFICADA;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    public Visita(Long clienteId, String clienteNombre, String clienteEmail, String clienteDireccion,
                  Double clienteLatitud, Double clienteLongitud, Long tecnicoId, String tecnicoNombre,
                  Long supervisorId, LocalDate fechaPlanificada, LocalTime horaPlanificada) {
        this();
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
        this.clienteEmail = clienteEmail;
        this.clienteDireccion = clienteDireccion;
        this.clienteLatitud = clienteLatitud;
        this.clienteLongitud = clienteLongitud;
        this.tecnicoId = tecnicoId;
        this.tecnicoNombre = tecnicoNombre;
        this.supervisorId = supervisorId;
        this.fechaPlanificada = fechaPlanificada;
        this.horaPlanificada = horaPlanificada;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    
    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }
    
    public String getClienteEmail() { return clienteEmail; }
    public void setClienteEmail(String clienteEmail) { this.clienteEmail = clienteEmail; }
    
    public String getClienteDireccion() { return clienteDireccion; }
    public void setClienteDireccion(String clienteDireccion) { this.clienteDireccion = clienteDireccion; }
    
    public Double getClienteLatitud() { return clienteLatitud; }
    public void setClienteLatitud(Double clienteLatitud) { this.clienteLatitud = clienteLatitud; }
    
    public Double getClienteLongitud() { return clienteLongitud; }
    public void setClienteLongitud(Double clienteLongitud) { this.clienteLongitud = clienteLongitud; }
    
    public Long getTecnicoId() { return tecnicoId; }
    public void setTecnicoId(Long tecnicoId) { this.tecnicoId = tecnicoId; }
    
    public String getTecnicoNombre() { return tecnicoNombre; }
    public void setTecnicoNombre(String tecnicoNombre) { this.tecnicoNombre = tecnicoNombre; }
    
    public Long getSupervisorId() { return supervisorId; }
    public void setSupervisorId(Long supervisorId) { this.supervisorId = supervisorId; }
    
    public LocalDate getFechaPlanificada() { return fechaPlanificada; }
    public void setFechaPlanificada(LocalDate fechaPlanificada) { this.fechaPlanificada = fechaPlanificada; }
    
    public LocalTime getHoraPlanificada() { return horaPlanificada; }
    public void setHoraPlanificada(LocalTime horaPlanificada) { this.horaPlanificada = horaPlanificada; }
    
    public EstadoVisita getEstado() { return estado; }
    public void setEstado(EstadoVisita estado) { this.estado = estado; }
    
    public LocalDateTime getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDateTime fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    
    public LocalDateTime getFechaEgreso() { return fechaEgreso; }
    public void setFechaEgreso(LocalDateTime fechaEgreso) { this.fechaEgreso = fechaEgreso; }
    
    public Double getLatitudIngreso() { return latitudIngreso; }
    public void setLatitudIngreso(Double latitudIngreso) { this.latitudIngreso = latitudIngreso; }
    
    public Double getLongitudIngreso() { return longitudIngreso; }
    public void setLongitudIngreso(Double longitudIngreso) { this.longitudIngreso = longitudIngreso; }
    
    public String getReporte() { return reporte; }
    public void setReporte(String reporte) { this.reporte = reporte; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
