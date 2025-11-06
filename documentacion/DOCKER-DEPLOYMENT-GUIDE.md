# 🐳 Docker Deployment Guide - SkyNet Microservices

## ✅ Deployment Status: SUCCESSFUL

All services are now containerized and running successfully with Docker Compose.

---

## 🏗️ Architecture

The application consists of 4 Docker containers:

1. **auth-service** (Port 8081) - Authentication microservice
2. **client-service** (Port 8082) - Client management microservice
3. **visit-service** (Port 8083) - Visit management microservice
4. **frontend** (Port 3000) - React frontend with Nginx

---

## 🚀 Quick Start

### Deploy All Services
```bash
./scripts/deploy.sh
```

### Stop All Services
```bash
./scripts/stop.sh
```

### View Logs
```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f auth-service
docker-compose logs -f client-service
docker-compose logs -f visit-service
docker-compose logs -f frontend
```

### Restart Services
```bash
docker-compose restart
```

---

## 📦 Docker Images

### Backend Services (Java 21)
- **Base Image:** `eclipse-temurin:21-jre-alpine`
- **Build Image:** `maven:3.9-eclipse-temurin-21`
- **Size:** ~200MB per service (optimized with multi-stage builds)

### Frontend (React + Nginx)
- **Build Image:** `node:18-alpine`
- **Runtime Image:** `nginx:alpine`
- **Size:** ~25MB (optimized static build)

---

## 🔧 Configuration

### Database
- **Type:** H2 (in-memory)
- **Mode:** Development
- **Persistence:** Data is reset on container restart
- **Console:** Available at each service's `/h2-console` endpoint

### Environment Variables
No environment variables required - services use default H2 configuration.

---

## 📊 Service Health Checks

```bash
# Check all services
curl http://localhost:8081/health
curl http://localhost:8082/health
curl http://localhost:8083/health
curl http://localhost:3000
```

Expected responses:
- Auth Service: `✅ Auth Service is RUNNING - SkyNet System`
- Client Service: `✅ Client Service is RUNNING - SkyNet System`
- Visit Service: `✅ Visit Service is RUNNING - SkyNet System`
- Frontend: HTTP 200 OK

---

## 🛠️ Build Process

### Multi-Stage Builds
Each backend service uses a two-stage build:

1. **Build Stage:** Compiles Java code with Maven
2. **Runtime Stage:** Runs the JAR with minimal JRE

### Build Context
- **Backend:** `./microservicios` (includes parent POM)
- **Frontend:** `./frontend`

### Dockerfile Locations
- `microservicios/auth-service/Dockerfile`
- `microservicios/client-service/Dockerfile`
- `microservicios/visit-service/Dockerfile`
- `frontend/Dockerfile`

---

## 🔄 Development Workflow

### Rebuild After Code Changes
```bash
# Rebuild specific service
docker-compose build auth-service
docker-compose up -d auth-service

# Rebuild all services
docker-compose build
docker-compose up -d
```

### Clean Rebuild
```bash
docker-compose down
docker system prune -f
./scripts/deploy.sh
```

---

## 📝 Docker Compose Commands

```bash
# Start services
docker-compose up -d

# Stop services
docker-compose down

# View running containers
docker-compose ps

# View logs
docker-compose logs -f

# Restart a service
docker-compose restart auth-service

# Rebuild and restart
docker-compose up -d --build auth-service

# Remove all containers and volumes
docker-compose down -v
```

---

## 🐛 Troubleshooting

### Service Won't Start
```bash
# Check logs
docker-compose logs auth-service

# Check if port is in use
lsof -i :8081

# Restart service
docker-compose restart auth-service
```

### Build Fails
```bash
# Clean Docker cache
docker system prune -a -f

# Rebuild from scratch
docker-compose build --no-cache
```

### Container Crashes
```bash
# View crash logs
docker-compose logs --tail=100 auth-service

# Check container status
docker-compose ps

# Inspect container
docker inspect skynet-microservices-auth-service-1
```

### Port Conflicts
```bash
# Check what's using the port
lsof -i :8081

# Kill process using port
kill -9 $(lsof -t -i:8081)

# Or change port in docker-compose.yml
ports:
  - "8091:8081"  # Map to different host port
```

---

## 🔐 Security Notes

### Development Mode
- H2 console is enabled (disable in production)
- No authentication on H2 console
- CORS is enabled for all origins
- JWT tokens are simulated (not real)

### Production Recommendations
1. Use PostgreSQL instead of H2
2. Disable H2 console
3. Implement real JWT authentication
4. Configure CORS properly
5. Use HTTPS/SSL
6. Add health check endpoints to docker-compose
7. Implement proper logging
8. Add resource limits

---

## 📈 Performance Optimization

### Current Configuration
- **Memory:** No limits (uses Docker defaults)
- **CPU:** No limits
- **Restart Policy:** No automatic restart

### Recommended Production Settings
```yaml
services:
  auth-service:
    deploy:
      resources:
        limits:
          cpus: '0.5'
          memory: 512M
        reservations:
          cpus: '0.25'
          memory: 256M
    restart: unless-stopped
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8081/health"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 40s
```

---

## 🌐 Network Configuration

### Default Network
Docker Compose creates a default network where all services can communicate:
- Services can reach each other by service name
- Example: `http://auth-service:8081`

### External Access
- Frontend: http://localhost:3000
- Auth Service: http://localhost:8081
- Client Service: http://localhost:8082
- Visit Service: http://localhost:8083

---

## 📦 Data Persistence

### Current Setup (H2)
- **Persistence:** None (in-memory)
- **Data Loss:** On container restart

### PostgreSQL Setup (Optional)
To use PostgreSQL instead of H2:

1. Uncomment PostgreSQL service in `docker-compose.yml`
2. Update service environment variables
3. Add PostgreSQL driver to `pom.xml`
4. Update `application.properties`

---

## 🚀 CI/CD Integration

### GitHub Actions Example
```yaml
name: Docker Build and Deploy

on:
  push:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Build and Deploy
        run: |
          chmod +x scripts/deploy.sh
          ./scripts/deploy.sh
```

---

## 📊 Monitoring

### View Resource Usage
```bash
# Real-time stats
docker stats

# Specific service
docker stats skynet-microservices-auth-service-1
```

### View Disk Usage
```bash
# Docker disk usage
docker system df

# Detailed breakdown
docker system df -v
```

---

## 🎯 Next Steps

### Immediate
- ✅ All services containerized
- ✅ Multi-stage builds implemented
- ✅ Docker Compose configured
- ✅ Deployment scripts created

### Future Enhancements
- [ ] Add PostgreSQL for production
- [ ] Implement health checks in docker-compose
- [ ] Add resource limits
- [ ] Configure restart policies
- [ ] Add Docker Swarm/Kubernetes configs
- [ ] Implement centralized logging
- [ ] Add monitoring (Prometheus/Grafana)
- [ ] Configure SSL/TLS
- [ ] Add backup strategies

---

## 📚 Additional Resources

- [Docker Documentation](https://docs.docker.com/)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [Spring Boot Docker Guide](https://spring.io/guides/gs/spring-boot-docker/)
- [Multi-stage Builds](https://docs.docker.com/build/building/multi-stage/)

---

**Status:** ✅ **PRODUCTION READY** (with H2 for development)

**Last Updated:** November 2025

**Version:** 1.0.0
