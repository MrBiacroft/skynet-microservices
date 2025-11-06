# 🚀 SkyNet Microservices System

## Sistema de Gestión de Visitas Técnicas

### Estructura del Proyecto

```
skynet-microservices/
├── frontend/                    # Aplicación React
│   └── src/
│       ├── components/         # Componentes reutilizables
│       └── pages/             # Páginas de la aplicación
├── microservicios/             # Servicios backend
│   ├── auth-service/          # Servicio de autenticación
│   ├── client-service/        # Servicio de gestión de clientes
│   └── visit-service/         # Servicio de gestión de visitas
├── documentacion/              # Documentación del proyecto
└── scripts/                    # Scripts de utilidad
```

### Tecnologías

- **Frontend**: React + Vite
- **Backend**: Java 21 + Spring Boot
- **Base de Datos**: PostgreSQL
- **Contenedores**: Docker

### Inicio Rápido

```bash
# Verificar configuración
./scripts/check-setup.sh

# Iniciar servicios
docker-compose up -d

# Acceder a la aplicación
# Frontend: http://localhost:3000
# Backend API: http://localhost:8080
```

### Microservicios

#### Auth Service (Puerto 8080)
Gestión de autenticación y autorización de usuarios.

#### Client Service (Puerto 8081)
Gestión de clientes y sus datos.

#### Visit Service (Porto 8082)
Gestión de visitas técnicas y seguimiento.

### Desarrollo

Para más información sobre el desarrollo y configuración, consulta la carpeta `documentacion/`.
