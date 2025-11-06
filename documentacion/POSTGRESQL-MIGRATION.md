# 🐘 Migración a PostgreSQL - SkyNet Microservices

## ✅ Estado: COMPLETADO

La aplicación ha sido migrada exitosamente de H2 (in-memory) a PostgreSQL.

---

## 🔄 Cambios Realizados

### 1. Dependencias Maven

Agregado PostgreSQL driver a los 3 microservicios:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

H2 se mantiene como opcional para desarrollo local.

### 2. Configuración de Perfiles

Cada servicio ahora soporta dos perfiles:

#### **Perfil DEV** (application-dev.properties)
- Base de datos: H2 (in-memory)
- Para desarrollo local sin Docker
- Datos se reinician en cada ejecución

#### **Perfil PROD** (application.properties)
- Base de datos: PostgreSQL
- Para Docker y producción
- Datos persistentes

### 3. Variables de Entorno

Los servicios ahora aceptan variables de entorno:

| Variable | Descripción | Default |
|----------|-------------|---------|
| `SPRING_PROFILE` | Perfil activo (dev/prod) | `dev` |
| `DATABASE_URL` | URL de PostgreSQL | `jdbc:postgresql://localhost:5432/skynet` |
| `DATABASE_USER` | Usuario de PostgreSQL | `postgres` |
| `DATABASE_PASSWORD` | Contraseña de PostgreSQL | `password` |

---

## 🐳 Docker Compose

### Configuración Actualizada

```yaml
services:
  postgres:
    image: postgres:15-alpine
    environment:
      POSTGRES_DB: skynet
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: skynet2024
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 10s
      timeout: 5s
      retries: 5

  auth-service:
    environment:
      SPRING_PROFILE: prod
      DATABASE_URL: jdbc:postgresql://postgres:5432/skynet
      DATABASE_USER: postgres
      DATABASE_PASSWORD: skynet2024
    depends_on:
      postgres:
        condition: service_healthy
```

### Características

- ✅ **Health Checks**: Los servicios esperan a que PostgreSQL esté listo
- ✅ **Persistencia**: Volumen `postgres_data` para datos permanentes
- ✅ **Restart Policy**: `unless-stopped` para alta disponibilidad
- ✅ **PostgreSQL 15 Alpine**: Imagen ligera y moderna

---

## 🚀 Cómo Usar

### Desarrollo Local (H2)

```bash
# Ejecutar sin Docker - usa H2
cd microservicios/auth-service
./mvnw spring-boot:run
```

### Producción Local (PostgreSQL con Docker)

```bash
# Desplegar con Docker Compose - usa PostgreSQL
./scripts/deploy.sh
```

### Cambiar Perfil Manualmente

```bash
# Usar PostgreSQL sin Docker
export SPRING_PROFILE=prod
export DATABASE_URL=jdbc:postgresql://localhost:5432/skynet
export DATABASE_USER=postgres
export DATABASE_PASSWORD=password
./mvnw spring-boot:run
```

---

## 📊 Estructura de Base de Datos

### Tablas Creadas Automáticamente

**auth-service:**
- `usuarios` - Usuarios del sistema (Admin, Supervisor, Técnico)

**client-service:**
- `clientes` - Clientes de la empresa

**visit-service:**
- `visitas` - Visitas técnicas programadas

### Datos Iniciales

Los datos de prueba se cargan automáticamente al iniciar:
- 3 usuarios (admin, supervisor, técnico)
- 3 clientes con geolocalización
- 3 visitas de ejemplo

---

## 🔐 Credenciales

### PostgreSQL (Docker)
- **Host:** localhost
- **Puerto:** 5432
- **Database:** skynet
- **Usuario:** postgres
- **Password:** skynet2024

### Aplicación
- **Admin:** admin@skynet.com / 123456
- **Supervisor:** supervisor@skynet.com / 123456
- **Técnico:** tecnico@skynet.com / 123456

---

## 🌐 Despliegue en la Nube

### Opción 1: Railway (Recomendado)

**Ventajas:**
- ✅ PostgreSQL incluido (managed)
- ✅ Deploy desde GitHub automático
- ✅ $5 crédito mensual gratis
- ✅ SSL/HTTPS automático
- ✅ Variables de entorno fáciles

**Pasos:**

1. **Crear cuenta en Railway**
   - Ir a https://railway.app
   - Conectar con GitHub

2. **Crear nuevo proyecto**
   - New Project → Deploy from GitHub repo
   - Seleccionar `MrBiacroft/skynet-microservices`

3. **Agregar PostgreSQL**
   - Add Service → Database → PostgreSQL
   - Railway crea automáticamente `DATABASE_URL`

4. **Configurar servicios**
   
   Para cada servicio (auth, client, visit):
   ```
   Build Command: cd microservicios/[service-name] && mvn clean package -DskipTests
   Start Command: java -jar target/*.jar
   ```

   Variables de entorno:
   ```
   SPRING_PROFILE=prod
   DATABASE_URL=${{Postgres.DATABASE_URL}}
   ```

5. **Configurar Frontend**
   ```
   Build Command: cd frontend && npm install && npm run build
   Start Command: npx serve -s dist -l $PORT
   ```

   Variables de entorno:
   ```
   VITE_API_AUTH_URL=https://auth-service.railway.app
   VITE_API_CLIENT_URL=https://client-service.railway.app
   VITE_API_VISIT_URL=https://visit-service.railway.app
   ```

### Opción 2: Render

**Ventajas:**
- ✅ PostgreSQL incluido
- ✅ Tier gratuito disponible
- ✅ SSL automático

**Pasos:**

1. Crear cuenta en https://render.com
2. New → PostgreSQL (crear base de datos)
3. New → Web Service (para cada microservicio)
4. Conectar GitHub repo
5. Configurar build commands y variables

### Opción 3: Heroku

**Ventajas:**
- ✅ PostgreSQL addon disponible
- ✅ Muy documentado
- ✅ CLI poderoso

**Costo:** $7/mes por dyno

### Opción 4: DigitalOcean App Platform

**Ventajas:**
- ✅ PostgreSQL managed database
- ✅ Escalable
- ✅ Buen rendimiento

**Costo:** ~$12/mes

---

## 📝 Configuración para Railway

### 1. Crear `railway.json` en la raíz

```json
{
  "$schema": "https://railway.app/railway.schema.json",
  "build": {
    "builder": "NIXPACKS"
  },
  "deploy": {
    "numReplicas": 1,
    "restartPolicyType": "ON_FAILURE",
    "restartPolicyMaxRetries": 10
  }
}
```

### 2. Crear `Procfile` para cada servicio

**auth-service/Procfile:**
```
web: java -Dserver.port=$PORT -jar target/*.jar
```

### 3. Variables de Entorno en Railway

```bash
# Automáticas (Railway las crea)
DATABASE_URL=postgresql://user:pass@host:5432/railway
PORT=8080

# Manuales (agregar en Railway dashboard)
SPRING_PROFILE=prod
```

---

## 🔧 Troubleshooting

### Error: "Connection refused"
```bash
# Verificar que PostgreSQL esté corriendo
docker-compose ps postgres

# Ver logs de PostgreSQL
docker-compose logs postgres
```

### Error: "Authentication failed"
```bash
# Verificar credenciales en docker-compose.yml
# Verificar variables de entorno en los servicios
docker-compose logs auth-service | grep DATABASE
```

### Datos no persisten
```bash
# Verificar volumen
docker volume ls | grep postgres

# Recrear volumen si es necesario
docker-compose down -v
docker-compose up -d
```

### Migrar datos de H2 a PostgreSQL
```bash
# 1. Exportar datos de H2 (si los tienes)
# Conectar a H2 console y ejecutar:
# SCRIPT TO 'backup.sql'

# 2. Importar a PostgreSQL
docker-compose exec postgres psql -U postgres -d skynet -f /backup.sql
```

---

## 📊 Comparación H2 vs PostgreSQL

| Característica | H2 | PostgreSQL |
|----------------|-----|------------|
| **Persistencia** | ❌ In-memory | ✅ Disco |
| **Producción** | ❌ No recomendado | ✅ Sí |
| **Rendimiento** | ⚡ Muy rápido | ⚡ Rápido |
| **Escalabilidad** | ❌ Limitada | ✅ Excelente |
| **Características** | ⚠️ Básicas | ✅ Avanzadas |
| **Costo** | ✅ Gratis | ✅ Gratis (open source) |
| **Uso** | 🔧 Desarrollo | 🚀 Producción |

---

## ✅ Checklist de Migración

- [x] Agregar dependencia PostgreSQL a pom.xml
- [x] Crear perfiles dev/prod
- [x] Configurar variables de entorno
- [x] Actualizar docker-compose.yml
- [x] Agregar health checks
- [x] Configurar volúmenes para persistencia
- [x] Probar localmente con Docker
- [ ] Desplegar en Railway/Render
- [ ] Configurar variables de entorno en la nube
- [ ] Probar en producción
- [ ] Configurar backups automáticos

---

## 🎯 Próximos Pasos

1. **Probar localmente:**
   ```bash
   ./scripts/deploy.sh
   ./scripts/test-all.sh
   ```

2. **Desplegar en Railway:**
   - Crear cuenta
   - Conectar GitHub
   - Configurar servicios
   - Agregar PostgreSQL

3. **Configurar CI/CD:**
   - GitHub Actions para testing
   - Deploy automático en push a main

4. **Monitoreo:**
   - Configurar logs centralizados
   - Agregar métricas
   - Configurar alertas

---

## 📚 Recursos

- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Spring Boot + PostgreSQL](https://spring.io/guides/gs/accessing-data-postgresql/)
- [Railway Documentation](https://docs.railway.app/)
- [Render Documentation](https://render.com/docs)
- [Docker Compose](https://docs.docker.com/compose/)

---

**Estado:** ✅ **MIGRACIÓN COMPLETADA**

**Fecha:** Noviembre 2025

**Versión:** 2.0.0 (PostgreSQL)
