# 📅 Visit Service - Workflow Example

## Complete Visit Lifecycle Test

This document shows the expected responses for a complete visit workflow.

---

## Step 1: Check-in (Registrar Ingreso)

### Request:
```bash
curl -X POST http://localhost:8083/api/visitas/1/registrar-ingreso \
  -H "Content-Type: application/json" \
  -d '{
    "latitud": 14.6039,
    "longitud": -90.5068
  }' | jq
```

### Expected Response:
```json
{
  "id": 1,
  "clienteId": 1,
  "clienteNombre": "Empresa ABC S.A.",
  "clienteEmail": "contacto@empresaabc.com",
  "clienteDireccion": "15 Calle 1-25, Zona 10, Guatemala City",
  "clienteLatitud": 14.6038,
  "clienteLongitud": -90.5069,
  "tecnicoId": 3,
  "tecnicoNombre": "Carlos López Técnico",
  "supervisorId": 2,
  "fechaPlanificada": "2025-11-06",
  "horaPlanificada": "09:00:00",
  "estado": "EN_CURSO",
  "fechaIngreso": "2025-11-06T08:15:23.456789",
  "fechaEgreso": null,
  "latitudIngreso": 14.6039,
  "longitudIngreso": -90.5068,
  "reporte": null,
  "fechaCreacion": "2025-11-06T08:03:33.816354",
  "fechaActualizacion": "2025-11-06T08:15:23.456789"
}
```

### What Changed:
- ✅ **estado**: `PLANIFICADA` → `EN_CURSO`
- ✅ **fechaIngreso**: Set to current timestamp
- ✅ **latitudIngreso**: 14.6039
- ✅ **longitudIngreso**: -90.5068
- ✅ **fechaActualizacion**: Updated

---

## Step 2: Check-out (Registrar Egreso)

### Request:
```bash
curl -X POST http://localhost:8083/api/visitas/1/registrar-egreso \
  -H "Content-Type: application/json" \
  -d '{
    "reporte": "Visita completada exitosamente. Se realizó mantenimiento preventivo del equipo. Todo funcionando correctamente."
  }' | jq
```

### Expected Response:
```json
{
  "id": 1,
  "clienteId": 1,
  "clienteNombre": "Empresa ABC S.A.",
  "clienteEmail": "contacto@empresaabc.com",
  "clienteDireccion": "15 Calle 1-25, Zona 10, Guatemala City",
  "clienteLatitud": 14.6038,
  "clienteLongitud": -90.5069,
  "tecnicoId": 3,
  "tecnicoNombre": "Carlos López Técnico",
  "supervisorId": 2,
  "fechaPlanificada": "2025-11-06",
  "horaPlanificada": "09:00:00",
  "estado": "COMPLETADA",
  "fechaIngreso": "2025-11-06T08:15:23.456789",
  "fechaEgreso": "2025-11-06T09:45:12.789456",
  "latitudIngreso": 14.6039,
  "longitudIngreso": -90.5068,
  "reporte": "Visita completada exitosamente. Se realizó mantenimiento preventivo del equipo. Todo funcionando correctamente.",
  "fechaCreacion": "2025-11-06T08:03:33.816354",
  "fechaActualizacion": "2025-11-06T09:45:12.789456"
}
```

### What Changed:
- ✅ **estado**: `EN_CURSO` → `COMPLETADA`
- ✅ **fechaEgreso**: Set to current timestamp
- ✅ **reporte**: Visit report saved
- ✅ **fechaActualizacion**: Updated

### Email Notification Sent:

The service console will show:

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
Visita completada exitosamente. Se realizó mantenimiento preventivo del equipo. Todo funcionando correctamente.

⏱️ Horarios de Ejecución:
   • Ingreso: 2025-11-06T08:15:23.456789
   • Egreso: 2025-11-06T09:45:12.789456

📍 Ubicación Registrada:
   • Latitud: 14.6039
   • Longitud: -90.5068

Agradecemos su confianza en SkyNet S.A.
¡Quedamos a su disposición para cualquier consulta!

Atentamente,
El equipo de SkyNet S.A.
═══════════════════════════════════════
```

---

## Complete Workflow Summary

### Status Flow:
```
PLANIFICADA → EN_CURSO → COMPLETADA
```

### Timeline:
1. **08:03:33** - Visit created (PLANIFICADA)
2. **08:15:23** - Technician checks in (EN_CURSO)
3. **09:45:12** - Technician checks out with report (COMPLETADA)
4. **09:45:12** - Email sent to client

### Duration:
- **Total visit time**: ~1 hour 30 minutes
- **From scheduled time (09:00)**: Started 15 minutes late, finished 45 minutes after scheduled

---

## Additional Test Endpoints

### Get Today's Visits for Technician:
```bash
curl "http://localhost:8083/api/visitas/tecnicos/3/hoy" | jq
```

**Response:** Array of 2 visits scheduled for today

### Get Visits by Status:
```bash
# Get completed visits
curl "http://localhost:8083/api/visitas/estado/COMPLETADA" | jq

# Get in-progress visits
curl "http://localhost:8083/api/visitas/estado/EN_CURSO" | jq

# Get planned visits
curl "http://localhost:8083/api/visitas/estado/PLANIFICADA" | jq
```

### Get All Today's Visits:
```bash
curl "http://localhost:8083/api/visitas/hoy" | jq
```

---

## How to Run the Complete Test

### Option 1: Manual Testing

**Terminal 1:**
```bash
cd microservicios/visit-service
./mvnw spring-boot:run
```

**Terminal 2:**
```bash
# Check-in
curl -X POST http://localhost:8083/api/visitas/1/registrar-ingreso \
  -H "Content-Type: application/json" \
  -d '{"latitud": 14.6039, "longitud": -90.5068}' | jq

# Check-out
curl -X POST http://localhost:8083/api/visitas/1/registrar-egreso \
  -H "Content-Type: application/json" \
  -d '{"reporte": "Mantenimiento completado exitosamente"}' | jq
```

### Option 2: Automated Test Script

**Terminal 1:**
```bash
cd microservicios/visit-service
./mvnw spring-boot:run
```

**Terminal 2:**
```bash
./scripts/test-visit-workflow.sh
```

---

## Key Features Demonstrated

✅ **GPS Tracking**: Coordinates recorded at check-in  
✅ **Status Workflow**: Automatic state transitions  
✅ **Timestamp Recording**: Precise timing of visit events  
✅ **Report System**: Detailed visit reports  
✅ **Email Notifications**: Automatic client notifications  
✅ **Data Validation**: All fields properly validated  

---

## Notes

- The email is **simulated** in development mode (printed to console)
- In production, configure real SMTP in `application.properties`
- GPS coordinates should come from the technician's mobile device
- The system tracks the exact time of check-in and check-out
- Reports are stored in the database for future reference

---

**Status:** Ready for production deployment with real SMTP configuration! 🚀
