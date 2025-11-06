# 🐳 SkyNet Microservices - Docker Deployment Guide

## Prerequisites

- Docker 20.10+
- Docker Compose 1.29+
- 4GB RAM minimum
- 10GB disk space

---

## Quick Start

### Option 1: Using Deploy Script (Recommended)

```bash
./scripts/deploy.sh
```

### Option 2: Manual Deployment

```bash
# Build images
docker-compose build

# Start services
docker-compose up -d

# View logs
docker-compose logs -f

# Check status
docker-compose ps
```

---

## Services

| Service | Port | URL | Description |
|---------|------|-----|-------------|
| Frontend | 3000 | http://localhost:3000 | React UI |
| Auth Service | 8081 | http://localhost:8081 | Authentication |
| Client Service | 8082 | http://localhost:8082 | Client Management |
| Visit Service | 8083 | http://localhost:8083 | Visit Management |
| PostgreSQL | 5432 | localhost:5432 | Database |

---

## Database Configuration

**Connection Details:**
- Host: `localhost` (or `postgres` from within containers)
- Port: `5432`
- Database: `skynet`
- Username: `admin`
- Password: `password`

**JDBC URL:**
```
jdbc:postgresql://postgres:5432/skynet
```

---

## Docker Commands

### Start Services
```bash
docker-compose up -d
```

### Stop Services
```bash
docker-compose down
```

### Stop and Remove Volumes (Delete Data)
```bash
docker-compose down -v
```

### View Logs
```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f auth-service
docker-compose logs -f frontend
```

### Rebuild Services
```bash
# Rebuild all
docker-compose build

# Rebuild specific service
docker-compose build auth-service

# Rebuild and restart
docker-compose up -d --build
```

### Check Service Status
```bash
docker-compose ps
```

### Execute Commands in Container
```bash
# Access PostgreSQL
docker-compose exec postgres psql -U admin -d skynet

# Access service shell
docker-compose exec auth-service sh
```

---

## Architecture

```
┌─────────────────────────────────────────────────────┐
│                    Docker Network                    │
│                                                      │
│  ┌──────────┐    ┌──────────┐    ┌──────────┐     │
│  │  Auth    │    │  Client  │    │  Visit   │     │
│  │ Service  │    │ Service  │    │ Service  │     │
│  │  :8081   │    │  :8082   │    │  :8083   │     │
│  └────┬─────┘    └────┬─────┘    └────┬─────┘     │
│       │               │               │            │
│       └───────────────┼───────────────┘            │
│                       │                            │
│                  ┌────▼─────┐                      │
│                  │PostgreSQL│                      │
│                  │  :5432   │                      │
│                  └──────────┘                      │
│                                                     │
│  ┌──────────────────────────────────────────┐     │
│  │           Frontend (Nginx)               │     │
│  │              :3000                        │     │
│  └──────────────────────────────────────────┘     │
└─────────────────────────────────────────────────────┘
```

---

## Environment Variables

### Auth Service
```env
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/skynet
SPRING_DATASOURCE_USERNAME=admin
SPRING_DATASOURCE_PASSWORD=password
```

### Client Service
```env
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/skynet
SPRING_DATASOURCE_USERNAME=admin
SPRING_DATASOURCE_PASSWORD=password
```

### Visit Service
```env
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/skynet
SPRING_DATASOURCE_USERNAME=admin
SPRING_DATASOURCE_PASSWORD=password
```

---

## Volumes

### PostgreSQL Data
```yaml
postgres_data:
  # Persists database data
  # Location: /var/lib/docker/volumes/
```

**To backup:**
```bash
docker-compose exec postgres pg_dump -U admin skynet > backup.sql
```

**To restore:**
```bash
docker-compose exec -T postgres psql -U admin skynet < backup.sql
```

---

## Troubleshooting

### Services Won't Start

**Check logs:**
```bash
docker-compose logs
```

**Check if ports are in use:**
```bash
lsof -i :3000
lsof -i :8081
lsof -i :8082
lsof -i :8083
lsof -i :5432
```

### Database Connection Issues

**Verify PostgreSQL is running:**
```bash
docker-compose ps postgres
```

**Check database logs:**
```bash
docker-compose logs postgres
```

**Test connection:**
```bash
docker-compose exec postgres psql -U admin -d skynet -c "SELECT 1;"
```

### Frontend Can't Connect to Backend

**Check Nginx configuration:**
```bash
docker-compose exec frontend cat /etc/nginx/nginx.conf
```

**Verify service names resolve:**
```bash
docker-compose exec frontend ping auth-service
```

### Rebuild Everything

```bash
# Stop and remove everything
docker-compose down -v

# Remove images
docker-compose down --rmi all

# Rebuild from scratch
docker-compose build --no-cache
docker-compose up -d
```

---

## Production Considerations

### Security

1. **Change default passwords:**
   ```yaml
   POSTGRES_PASSWORD: <strong-password>
   ```

2. **Use secrets management:**
   - Docker Secrets
   - Environment variable files
   - Vault integration

3. **Enable HTTPS:**
   - Add SSL certificates
   - Configure Nginx for HTTPS
   - Use Let's Encrypt

### Performance

1. **Resource limits:**
   ```yaml
   services:
     auth-service:
       deploy:
         resources:
           limits:
             cpus: '1'
             memory: 512M
   ```

2. **Health checks:**
   ```yaml
   healthcheck:
     test: ["CMD", "curl", "-f", "http://localhost:8081/health"]
     interval: 30s
     timeout: 10s
     retries: 3
   ```

3. **Scaling:**
   ```bash
   docker-compose up -d --scale visit-service=3
   ```

### Monitoring

1. **Add logging driver:**
   ```yaml
   logging:
     driver: "json-file"
     options:
       max-size: "10m"
       max-file: "3"
   ```

2. **Integrate monitoring tools:**
   - Prometheus
   - Grafana
   - ELK Stack

---

## CI/CD Integration

### GitHub Actions Example

```yaml
name: Deploy

on:
  push:
    branches: [main]

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Build and Deploy
        run: |
          docker-compose build
          docker-compose up -d
```

---

## Useful Scripts

### Deploy
```bash
./scripts/deploy.sh
```

### Stop
```bash
./scripts/stop.sh
```

### View All Logs
```bash
docker-compose logs -f --tail=100
```

### Restart Single Service
```bash
docker-compose restart auth-service
```

---

## Support

For issues or questions:
1. Check logs: `docker-compose logs`
2. Verify configuration: `docker-compose config`
3. Review documentation in `/documentacion`

---

**Status:** ✅ Production Ready with Docker Compose
