# 📊 Estructura de Base de Datos - SkyNet v1.1

## Información General

- **Sistema**: SkyNet Microservices
- **Versión**: 1.1-complete
- **Base de Datos**: PostgreSQL 17.6
- **Fecha**: 2025-11-08
- **Archivo dump**: `dump.sql` (9.4 KB, 159 líneas)

---

## 📋 Tablas del Sistema

### Resumen

| Tabla | Registros | Descripción |
|-------|-----------|-------------|
| **usuarios** | 3 | Usuarios del sistema (Admin, Supervisor, Técnico) |
| **clientes** | 3 | Clientes con geolocalización |
| **visitas** | 9 | Visitas técnicas programadas y completadas |

---

## 1. Tabla: `usuarios`

### Descripción
Almacena los usuarios del sistema con sus roles y credenciales.

### Estructura

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---------------|-------------|
| `id` | bigint | PRIMARY KEY, AUTO_INCREMENT | Identificador único |
| `email` | varchar(255) | NOT NULL, UNIQUE | Email del usuario |
| `nombre` | varchar(255) | NOT NULL | Nombre completo |
| `password` | varchar(255) | NOT NULL | Contraseña (texto plano en demo) |
| `rol` | varchar(255) | CHECK (ADMIN, SUPERVISOR, TECNICO) | Rol del usuario |
| `fecha_creacion` | timestamp(6) | NOT NULL | Fecha de creación |

### Constraints
```sql
CONSTRAINT usuarios_pkey PRIMARY KEY (id)
CONSTRAINT usuarios_rol_check CHECK (rol IN ('ADMIN', 'SUPERVISOR', 'TECNICO'))
```

### Datos de Prueba

| ID | Email | Nombre | Rol | Password |
|----|-------|--------|-----|----------|
| 1 | admin@skynet.com | Administrador Principal | ADMIN | 123456 |
| 2 | supervisor@skynet.com | Juan Pérez Supervisor | SUPERVISOR | 123456 |
| 3 | tecnico@skynet.com | Carlos López Técnico | TECNICO | 123456 |

### Índices
- PRIMARY KEY en `id`
- Secuencia: `usuarios_id_seq` (actual: 3)

---

## 2. Tabla: `clientes`

### Descripción
Almacena información de clientes con ubicación geográfica para visitas técnicas.

### Estructura

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---------------|-------------|
| `id` | bigint | PRIMARY KEY, AUTO_INCREMENT | Identificador único |
| `nombre` | varchar(255) | NOT NULL | Nombre del cliente |
| `direccion` | varchar(255) | NOT NULL | Dirección física |
| `telefono` | varchar(255) | | Teléfono de contacto |
| `email` | varchar(255) | | Email de contacto |
| `latitud` | double precision | | Coordenada GPS (latitud) |
| `longitud` | double precision | | Coordenada GPS (longitud) |
| `fecha_creacion` | timestamp(6) | NOT NULL | Fecha de creación |
| `fecha_actualizacion` | timestamp(6) | NOT NULL | Última actualización |

### Constraints
```sql
CONSTRAINT clientes_pkey PRIMARY KEY (id)
```

### Datos de Prueba

| ID | Nombre | Dirección | Teléfono | Coordenadas |
|----|--------|-----------|----------|-------------|
| 1 | Empresa ABC S.A. | 15 Calle 1-25, Zona 10, Guatemala City | 1234-5678 | 14.6038, -90.5069 |
| 2 | Comercial XYZ Ltda. | 8a Avenida 12-45, Zona 1, Quetzaltenango | 2345-6789 | 14.8347, -91.5181 |
| 3 | Servicios Técnicos Modernos | 5a Calle 8-72, Zona 3, Antigua Guatemala | 3456-7890 | 14.5586, -90.7295 |

### Índices
- PRIMARY KEY en `id`
- Secuencia: `clientes_id_seq` (actual: 3)

---

## 3. Tabla: `visitas`

### Descripción
Almacena las visitas técnicas programadas, en curso y completadas. Incluye geolocalización de ingreso y reportes.

### Estructura

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---------------|-------------|
| `id` | bigint | PRIMARY KEY, AUTO_INCREMENT | Identificador único |
| `cliente_id` | bigint | | ID del cliente (desnormalizado) |
| `cliente_nombre` | varchar(255) | | Nombre del cliente |
| `cliente_email` | varchar(255) | | Email del cliente |
| `cliente_direccion` | varchar(255) | | Dirección del cliente |
| `cliente_latitud` | double precision | | Coordenada del cliente |
| `cliente_longitud` | double precision | | Coordenada del cliente |
| `tecnico_id` | bigint | NOT NULL | ID del técnico asignado |
| `tecnico_nombre` | varchar(255) | | Nombre del técnico |
| `supervisor_id` | bigint | | ID del supervisor |
| `fecha_planificada` | date | NOT NULL | Fecha programada |
| `hora_planificada` | time(6) | NOT NULL | Hora programada |
| `estado` | varchar(255) | CHECK | Estado de la visita |
| `fecha_ingreso` | timestamp(6) | | Timestamp de ingreso |
| `latitud_ingreso` | double precision | | GPS al ingresar |
| `longitud_ingreso` | double precision | | GPS al ingresar |
| `fecha_egreso` | timestamp(6) | | Timestamp de egreso |
| `reporte` | text | | Reporte del técnico |
| `fecha_creacion` | timestamp(6) | NOT NULL | Fecha de creación |
| `fecha_actualizacion` | timestamp(6) | NOT NULL | Última actualización |

### Constraints
```sql
CONSTRAINT visitas_pkey PRIMARY KEY (id)
CONSTRAINT visitas_estado_check CHECK (estado IN ('PLANIFICADA', 'EN_CURSO', 'COMPLETADA', 'CANCELADA'))
```

### Estados Posibles

| Estado | Descripción |
|--------|-------------|
| **PLANIFICADA** | Visita programada, no iniciada |
| **EN_CURSO** | Técnico ha registrado ingreso |
| **COMPLETADA** | Visita finalizada con reporte |
| **CANCELADA** | Visita cancelada |

### Datos de Prueba (Resumen)

| ID | Cliente | Técnico | Fecha | Estado | Reporte |
|----|---------|---------|-------|--------|---------|
| 1 | Empresa ABC S.A. | Carlos López | 2025-11-06 09:00 | PLANIFICADA | - |
| 2 | Comercial XYZ Ltda. | Carlos López | 2025-11-06 11:30 | PLANIFICADA | - |
| 3 | Servicios Técnicos | Carlos López | 2025-11-07 14:00 | EN_CURSO | - |
| 4 | Empresa ABC S.A. | Carlos López | 2025-11-09 10:00 | COMPLETADA | ✅ |
| 5 | Empresa ABC S.A. | Carlos López | 2025-11-08 15:00 | COMPLETADA | ✅ |
| 6 | Bryan Cano | Carlos López | 2025-11-08 15:00 | COMPLETADA | ✅ |
| 7 | Empresa ABC S.A. | Carlos López | 2025-11-08 08:00 | COMPLETADA | ✅ |
| 8 | Comercial XYZ Ltda. | Carlos López | 2025-11-08 10:30 | COMPLETADA | ✅ |
| 9 | Servicios Técnicos | Carlos López | 2025-11-08 14:00 | COMPLETADA | ✅ |

### Índices
- PRIMARY KEY en `id`
- Secuencia: `visitas_id_seq` (actual: 9)

---

## 🔄 Relaciones

### Diagrama Conceptual

```
usuarios (id)
    ↓
visitas (tecnico_id, supervisor_id)
    ↓
clientes (cliente_id) [desnormalizado]
```

**Nota**: Las relaciones están desnormalizadas para permitir independencia entre microservicios.

---

## 📊 Estadísticas

### Distribución de Visitas por Estado

| Estado | Cantidad | Porcentaje |
|--------|----------|------------|
| PLANIFICADA | 2 | 22% |
| EN_CURSO | 1 | 11% |
| COMPLETADA | 6 | 67% |
| CANCELADA | 0 | 0% |

### Visitas por Cliente

| Cliente | Total Visitas |
|---------|---------------|
| Empresa ABC S.A. | 4 |
| Comercial XYZ Ltda. | 2 |
| Servicios Técnicos Modernos | 2 |
| Bryan Cano (Prueba) | 1 |

---

## 🔧 Uso del Dump

### Restaurar Base de Datos

```bash
# Opción 1: Desde archivo
psql -h localhost -U postgres -d skynet < dump.sql

# Opción 2: Desde Railway
PGPASSWORD='password' psql -h hopper.proxy.rlwy.net -p 39723 -U postgres -d railway < dump.sql
```

### Verificar Restauración

```sql
-- Contar registros
SELECT 'usuarios' as tabla, COUNT(*) as registros FROM usuarios
UNION ALL
SELECT 'clientes', COUNT(*) FROM clientes
UNION ALL
SELECT 'visitas', COUNT(*) FROM visitas;

-- Resultado esperado:
-- usuarios | 3
-- clientes | 3
-- visitas  | 9
```

---

## 🛠️ Mantenimiento

### Backup Recomendado

```bash
# Backup completo
pg_dump -h host -U user -d database > backup_$(date +%Y%m%d).sql

# Backup solo esquema
pg_dump -h host -U user -d database --schema-only > schema.sql

# Backup solo datos
pg_dump -h host -U user -d database --data-only > data.sql
```

### Limpieza de Datos de Prueba

```sql
-- Eliminar visitas de prueba
DELETE FROM visitas WHERE id > 3;

-- Resetear secuencias
SELECT setval('visitas_id_seq', 3, true);
```

---

## 📝 Notas Importantes

1. **Passwords**: En producción, usar BCrypt o similar
2. **Relaciones**: Desnormalizadas para arquitectura de microservicios
3. **Geolocalización**: Coordenadas en formato decimal (WGS84)
4. **Timestamps**: Zona horaria UTC
5. **Secuencias**: Auto-incrementales con PostgreSQL

---

## 📄 Archivos Relacionados

- **dump.sql**: Dump completo de la base de datos
- **README.md**: Documentación general del proyecto
- **ESTADO-SISTEMA.md**: Estado actual del sistema

---

**Generado**: 2025-11-08  
**Versión**: 1.1-complete  
**Sistema**: SkyNet Microservices
