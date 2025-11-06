# 📅 Visit Service - Complete Implementation

## ✅ Service Status: FULLY IMPLEMENTED

**Port:** 8083  
**Database:** H2 in-memory (visitdb)  
**Status:** Ready for testing

---

## 🏗️ Architecture

### Layers Created:
1. **Model** - `Visita` entity with complete visit lifecycle
2. **Repository** - `VisitaRepository` with custom queries
3. **Service** - `VisitaService` + `EmailService` for business logic
4. **Controller** - `VisitaController` with REST API
5. **Config** - `DataInitializer` with test data

---

## 📊 Test Data

The service initializes with 3 visits:

| ID | Client | Date | Time | Technician | Status |
|----|--------|------|------|------------|--------|
| 1 | Empresa ABC S.A. | Today | 09:00 | Carlos López | PLANIFICADA |
| 2 | Comercial XYZ Ltda. | Today | 11:30 | Carlos López | PLANIFICADA |
| 3 | Servicios Técnicos Modernos | Tomorrow | 14:00 | Carlos López | PLANIFICADA |

---

## 🔌 API Endpoints

### Basic CRUD

```bash
# Get all visits
GET http://localhost:8083/api/visitas

# Get visit by ID
GET http://localhost:8083/api/visitas/1

# Create new visit
POST http://localhost:8083/api/visitas
Content-Type: application/json
{
  "clienteId": 1,
  "clienteNombre": "Test Client",
  "clienteEmail": "test@client.com",
  "clienteDireccion": "Test Address",
  "clienteLatitud": 14.6000,
  "clienteLongitud": -90.5000,
  "tecnicoId": 3,
  "tecnicoNombre": "Carlos López",
  "supervisorId": 2,
  "fechaPlanificada": "2025-11-06",
  "horaPlanificada": "10:00:00"
}

# Update visit
PUT http://localhost:8083/api/visitas/1

# Delete visit
DELETE http://localhost:8083/api/visitas/1
```

### Query Endpoints

```bash
# Get visits by technician
GET http://localhost:8083/api/visitas/tecnicos/3

# Get today's visits for technician
GET http://localhost:8083/api/visitas/tecnicos/3/hoy

# Get visits by supervisor
GET http://localhost:8083/api/visitas/supervisores/2

# Get visits by status
GET http://localhost:8083/api/visitas/estado/PLANIFICADA
# Options: PLANIFICADA, EN_CURSO, COMPLETADA, CANCELADA

# Get today's scheduled visits
GET http://localhost:8083/api/visitas/hoy
```

### Visit Workflow

```bash
# 1. Check-in (registrar ingreso)
POST http://localhost:8083/api/visitas/1/registrar-ingreso
Content-Type: application/json
{
  "latitud": 14.6038,
  "longitud": -90.5069
}

# 2. Check-out (registrar egreso) - Triggers email
POST http://localhost:8083/api/visitas/1/registrar-egreso
Content-Type: application/json
{
  "reporte": "Visita completada exitosamente. Se realizó mantenimiento preventivo del equipo."
}
```

---

## 🔄 Visit Status Flow

```
PLANIFICADA → EN_CURSO → COMPLETADA
     ↓
  CANCELADA
```

### Status Transitions:

1. **PLANIFICADA** (Initial)
   - Visit is scheduled
   - Waiting for technician

2. **EN_CURSO** (After check-in)
   - Technician has arrived
   - GPS coordinates recorded
   - Timestamp saved

3. **COMPLETADA** (After check-out)
   - Visit finished
   - Report submitted
   - Email sent to client

4. **CANCELADA** (Manual)
   - Visit cancelled
   - Can be set manually

---

## 📧 Email Notification System

When a visit is completed (check-out), an email is automatically sent to the client with:

- Visit details (date, time, technician)
- Visit report
- Execution timestamps
- GPS coordinates
- Professional formatting

**Development Mode:** Emails are printed to console  
**Production Mode:** Can be configured to use real SMTP

### Example Email Output:

```
═══════════════════════════════════════
📧 EMAIL ENVIADO (Simulación):
Para: contacto@empresaabc.com
Asunto: 📋 Reporte de Visita Técnica - SkyNet S.A.
Contenido:
Estimado/a Cliente: Empresa ABC S.A.

Le informamos que se ha completado la visita técnica programada:

📅 Fecha de Visita: 2025-11-06
⏰ Hora Programada: 09:00
👨‍💼 Técnico Asignado: Carlos López Técnico
📍 Dirección Visitada: 15 Calle 1-25, Zona 10, Guatemala City

📋 Reporte de la Visita:
Visita completada exitosamente. Se realizó mantenimiento preventivo del equipo.

⏱️ Horarios de Ejecución:
   • Ingreso: 2025-11-06T09:05:00
   • Egreso: 2025-11-06T10:30:00

📍 Ubicación Registrada:
   • Latitud: 14.6038
   • Longitud: -90.5069

Agradecemos su confianza en SkyNet S.A.
¡Quedamos a su disposición para cualquier consulta!

Atentamente,
El equipo de SkyNet S.A.
═══════════════════════════════════════
```

---

## 🧪 Testing Workflow

### Complete Visit Lifecycle Test:

```bash
# 1. Start the service
cd microservicios/visit-service
./mvnw spring-boot:run

# 2. Get today's visits for technician
curl http://localhost:8083/api/visitas/tecnicos/3/hoy

# 3. Check-in to first visit
curl -X POST http://localhost:8083/api/visitas/1/registrar-ingreso \
  -H "Content-Type: application/json" \
  -d '{"latitud": 14.6038, "longitud": -90.5069}'

# 4. Verify status changed to EN_CURSO
curl http://localhost:8083/api/visitas/1

# 5. Complete visit with report
curl -X POST http://localhost:8083/api/visitas/1/registrar-egreso \
  -H "Content-Type: application/json" \
  -d '{"reporte": "Mantenimiento completado exitosamente"}'

# 6. Check console for email output
# 7. Verify status changed to COMPLETADA
curl http://localhost:8083/api/visitas/1
```

---

## 🗄️ Database Schema

### Visitas Table

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key |
| cliente_id | BIGINT | Client reference |
| cliente_nombre | VARCHAR | Client name |
| cliente_email | VARCHAR | Client email |
| cliente_direccion | VARCHAR | Client address |
| cliente_latitud | DOUBLE | Client latitude |
| cliente_longitud | DOUBLE | Client longitude |
| tecnico_id | BIGINT | Technician ID |
| tecnico_nombre | VARCHAR | Technician name |
| supervisor_id | BIGINT | Supervisor ID |
| fecha_planificada | DATE | Scheduled date |
| hora_planificada | TIME | Scheduled time |
| estado | VARCHAR | Visit status |
| fecha_ingreso | TIMESTAMP | Check-in time |
| fecha_egreso | TIMESTAMP | Check-out time |
| latitud_ingreso | DOUBLE | Check-in latitude |
| longitud_ingreso | DOUBLE | Check-in longitude |
| reporte | TEXT | Visit report |
| fecha_creacion | TIMESTAMP | Creation timestamp |
| fecha_actualizacion | TIMESTAMP | Update timestamp |

---

## 🔗 Integration Points

### With Other Services:

1. **auth-service (8081)**
   - Validates technician/supervisor IDs
   - Provides user information

2. **client-service (8082)**
   - Provides client information
   - Validates client IDs

3. **Frontend**
   - Displays visit calendar
   - Mobile app for technicians
   - Dashboard for supervisors

---

## 🚀 How to Run

```bash
cd microservicios/visit-service
./mvnw spring-boot:run
```

Service will start on port **8083**.

---

## ✅ Features Implemented

- ✅ Complete CRUD operations
- ✅ Visit scheduling and planning
- ✅ Technician assignment
- ✅ Supervisor oversight
- ✅ GPS tracking (check-in/check-out)
- ✅ Visit status workflow
- ✅ Report submission
- ✅ Email notifications
- ✅ Multi-role queries
- ✅ Date-based filtering
- ✅ Test data initialization

---

## 📝 Next Steps

To complete the full system:

1. **Frontend Development**
   - React application for web interface
   - Mobile app for technicians

2. **Service Integration**
   - Connect with auth-service for authentication
   - Connect with client-service for client data

3. **Production Configuration**
   - Configure real SMTP for emails
   - Set up PostgreSQL database
   - Add security (JWT tokens)
   - Implement logging and monitoring

---

**Status:** 🎉 Visit Service is COMPLETE and ready for testing!
