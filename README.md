# 🚀 SkyNet Microservices System

Sistema completo de gestión de visitas técnicas con arquitectura de microservicios.

## ✨ Características

- 🔐 **Autenticación** - Sistema de login con roles (Admin, Supervisor, Técnico)
- 👥 **Gestión de Clientes** - CRUD completo con geolocalización
- 📅 **Gestión de Visitas** - Planificación, seguimiento y reportes
- 📍 **Geolocalización** - Check-in/out con GPS
- 🎨 **Dashboards** - Interfaces específicas por rol
- 🐳 **Docker** - Containerización completa
- 🗄️ **PostgreSQL** - Base de datos persistente

## 🏗️ Arquitectura

```
skynet-microservices/
├── frontend/                    # React + Vite + Tailwind
├── microservicios/
│   ├── auth-service/           # Puerto 8081 - Autenticación
│   ├── client-service/         # Puerto 8082 - Clientes
│   └── visit-service/          # Puerto 8083 - Visitas
├── documentacion/              # Guías completas
└── scripts/                    # Automatización
```

## 🚀 Inicio Rápido

### Opción 1: Docker (Recomendado)

```bash
# Desplegar todo con un comando
./scripts/deploy.sh

# Acceder a:
# Frontend: http://localhost:3000
# Auth API: http://localhost:8081
# Client API: http://localhost:8082
# Visit API: http://localhost:8083
# PostgreSQL: localhost:5432
```

### Opción 2: Desarrollo Local

```bash
# Backend (3 terminales)
cd microservicios/auth-service && ./mvnw spring-boot:run
cd microservicios/client-service && ./mvnw spring-boot:run
cd microservicios/visit-service && ./mvnw spring-boot:run

# Frontend
cd frontend && npm install && npm run dev
```

## 👤 Usuarios de Prueba

| Email | Password | Rol |
|-------|----------|-----|
| admin@skynet.com | 123456 | ADMIN |
| supervisor@skynet.com | 123456 | SUPERVISOR |
| tecnico@skynet.com | 123456 | TECNICO |

## 🛠️ Tecnologías

### Backend
- Java 21
- Spring Boot 3.1.0
- Spring Data JPA
- PostgreSQL 15
- Maven

### Frontend
- React 18
- Vite 4
- Tailwind CSS 3
- Axios
- React Router

### DevOps
- Docker & Docker Compose
- PostgreSQL Alpine
- Nginx

## 📚 Documentación

- [Migración a PostgreSQL](documentacion/POSTGRESQL-MIGRATION.md)
- [Despliegue en Railway](documentacion/RAILWAY-DEPLOYMENT.md)
- [Guía Docker](documentacion/DOCKER-DEPLOYMENT-GUIDE.md)
- [Proyecto Completo](documentacion/PROYECTO-COMPLETO.md)

## 🌐 Despliegue en Producción

### Railway (Recomendado)

1. Crear cuenta en [Railway](https://railway.app)
2. Conectar repositorio de GitHub
3. Crear PostgreSQL database
4. Desplegar los 4 servicios
5. Configurar variables de entorno

**Guía completa:** [RAILWAY-DEPLOYMENT.md](documentacion/RAILWAY-DEPLOYMENT.md)

**Costo:** $3-4/mes (dentro del plan gratuito de $5)

## 🧪 Testing

```bash
# Probar todos los servicios
./scripts/test-all.sh

# Probar APIs individuales
./scripts/test-auth-api.sh
./scripts/test-client-api.sh
./scripts/test-visit-workflow.sh
```

## 📊 Servicios

### Auth Service (8081)
- Login/Logout
- Gestión de usuarios
- Roles y permisos

### Client Service (8082)
- CRUD de clientes
- Geolocalización
- Búsqueda

### Visit Service (8083)
Gestión de visitas técnicas y seguimiento.

### Desarrollo

Para más información sobre el desarrollo y configuración, consulta la carpeta `documentacion/`.
