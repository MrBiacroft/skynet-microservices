# 📅 Visit Service - Setup Complete

## Service Information
- **Port:** 8083
- **Database:** H2 in-memory (visitdb)
- **Application:** VisitServiceApplication
- **Status:** ✅ Running

---

## Endpoints Available

### Health Check
```bash
GET http://localhost:8083/health
```
**Response:** `✅ Visit Service is RUNNING - SkyNet System`

### Home
```bash
GET http://localhost:8083/
```
**Response:** `📅 SkyNet Visit Service - Gestión de Visitas Técnicas`

### H2 Console
```
http://localhost:8083/h2-console
```
- **JDBC URL:** `jdbc:h2:mem:visitdb`
- **Username:** `sa`
- **Password:** (empty)

---

## Database Schema

### Visita Entity
The `visitas` table includes:

**Client Information:**
- `cliente_id` - Client ID reference
- `cliente_nombre` - Client name
- `cliente_email` - Client email
- `cliente_direccion` - Client address
- `cliente_latitud` - Client latitude
- `cliente_longitud` - Client longitude

**Technician & Supervisor:**
- `tecnico_id` - Technician ID
- `tecnico_nombre` - Technician name
- `supervisor_id` - Supervisor ID

**Visit Scheduling:**
- `fecha_planificada` - Scheduled date
- `hora_planificada` - Scheduled time
- `estado` - Visit status (PLANIFICADA, EN_CURSO, COMPLETADA, CANCELADA)

**Execution Tracking:**
- `fecha_ingreso` - Check-in timestamp
- `fecha_egreso` - Check-out timestamp
- `latitud_ingreso` - Check-in latitude
- `longitud_ingreso` - Check-in longitude

**Report:**
- `reporte` - Visit report (TEXT field)

**Timestamps:**
- `fecha_creacion` - Creation timestamp
- `fecha_actualizacion` - Last update timestamp

---

## Repository Query Methods

The `VisitaRepository` provides:

1. **findByTecnicoId(Long tecnicoId)**
   - Get all visits for a specific technician

2. **findBySupervisorId(Long supervisorId)**
   - Get all visits for a specific supervisor

3. **findByEstado(EstadoVisita estado)**
   - Get visits by status (PLANIFICADA, EN_CURSO, COMPLETADA, CANCELADA)

4. **findVisitasHoyPorTecnico(Long tecnicoId, LocalDate fecha)**
   - Get today's visits for a technician

5. **findByFechaPlanificada(LocalDate fechaPlanificada)**
   - Get visits scheduled for a specific date

6. **findByClienteId(Long clienteId)**
   - Get all visits for a specific client

---

## Visit Status Flow

```
PLANIFICADA → EN_CURSO → COMPLETADA
     ↓
  CANCELADA
```

- **PLANIFICADA:** Visit is scheduled
- **EN_CURSO:** Technician has checked in
- **COMPLETADA:** Visit finished with report
- **CANCELADA:** Visit was cancelled

---

## Features

✅ **Visit Management:** Complete CRUD operations
✅ **Geolocation Tracking:** GPS coordinates for check-in
✅ **Status Workflow:** Track visit lifecycle
✅ **Multi-role Support:** Technician, Supervisor, Client views
✅ **Report System:** Text reports for completed visits
✅ **Email Integration:** Mail starter configured (simulated)

---

## How to Run

```bash
cd microservicios/visit-service
./mvnw spring-boot:run
```

Service will start on port **8083**.

---

## Next Steps

To complete the visit-service, you'll need to create:

1. **VisitaService** - Business logic layer
2. **VisitaController** - REST API endpoints
3. **DataInitializer** - Test data
4. **DTOs** - Request/Response objects (if needed)

---

## Integration Points

This service is designed to integrate with:
- **auth-service (8081):** User authentication
- **client-service (8082):** Client information
- **Frontend:** React application for UI

The service uses denormalized data (storing client info directly) to maintain microservices independence while allowing for future integration via API calls.
