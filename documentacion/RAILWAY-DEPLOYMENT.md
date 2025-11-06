# 🚂 Despliegue en Railway - SkyNet Microservices

## 🎯 Guía Completa de Despliegue

Railway es la plataforma recomendada para desplegar SkyNet Microservices por su facilidad de uso y soporte nativo para Spring Boot + PostgreSQL.

---

## 📋 Requisitos Previos

- ✅ Cuenta de GitHub con el repositorio
- ✅ Cuenta en Railway (https://railway.app)
- ✅ Código migrado a PostgreSQL (completado)

---

## 🚀 Paso 1: Crear Cuenta en Railway

1. Ir a https://railway.app
2. Click en "Start a New Project"
3. Conectar con GitHub
4. Autorizar Railway para acceder a tus repositorios

---

## 🗄️ Paso 2: Crear Base de Datos PostgreSQL

1. En Railway Dashboard, click "New Project"
2. Seleccionar "Provision PostgreSQL"
3. Railway creará automáticamente:
   - Base de datos PostgreSQL
   - Variable `DATABASE_URL`
   - Credenciales de acceso

4. **Copiar las variables** (las necesitarás después):
   - `DATABASE_URL`
   - `PGHOST`
   - `PGPORT`
   - `PGUSER`
   - `PGPASSWORD`
   - `PGDATABASE`

---

## 🔧 Paso 3: Desplegar Auth Service

### 3.1 Agregar Servicio

1. En tu proyecto Railway, click "+ New"
2. Seleccionar "GitHub Repo"
3. Elegir `MrBiacroft/skynet-microservices`
4. Railway detectará automáticamente el proyecto

### 3.2 Configurar Build

En Settings → Build:

```bash
# Root Directory
microservicios/auth-service

# Build Command
mvn clean package -DskipTests

# Start Command
java -Dserver.port=$PORT -jar target/*.jar
```

### 3.3 Variables de Entorno

En Settings → Variables:

```bash
SPRING_PROFILE=prod
DATABASE_URL=${{Postgres.DATABASE_URL}}
PORT=8080
```

### 3.4 Configurar Dominio

1. Settings → Networking
2. Click "Generate Domain"
3. Copiar URL (ej: `auth-service-production.up.railway.app`)

---

## 🔧 Paso 4: Desplegar Client Service

Repetir el proceso del Paso 3 con estos cambios:

### Build Configuration

```bash
# Root Directory
microservicios/client-service

# Build Command
mvn clean package -DskipTests

# Start Command
java -Dserver.port=$PORT -jar target/*.jar
```

### Variables de Entorno

```bash
SPRING_PROFILE=prod
DATABASE_URL=${{Postgres.DATABASE_URL}}
PORT=8080
```

---

## 🔧 Paso 5: Desplegar Visit Service

Repetir el proceso con:

### Build Configuration

```bash
# Root Directory
microservicios/visit-service

# Build Command
mvn clean package -DskipTests

# Start Command
java -Dserver.port=$PORT -jar target/*.jar
```

### Variables de Entorno

```bash
SPRING_PROFILE=prod
DATABASE_URL=${{Postgres.DATABASE_URL}}
PORT=8080
```

---

## 🎨 Paso 6: Desplegar Frontend

### 6.1 Agregar Servicio

1. "+ New" → "GitHub Repo"
2. Seleccionar el mismo repositorio

### 6.2 Configurar Build

```bash
# Root Directory
frontend

# Build Command
npm install && npm run build

# Start Command
npx serve -s dist -l $PORT
```

### 6.3 Variables de Entorno

```bash
VITE_API_AUTH_URL=https://auth-service-production.up.railway.app
VITE_API_CLIENT_URL=https://client-service-production.up.railway.app
VITE_API_VISIT_URL=https://visit-service-production.up.railway.app
```

**⚠️ Importante:** Reemplazar las URLs con las generadas en los pasos anteriores.

### 6.4 Actualizar Frontend

Necesitas actualizar el código del frontend para usar variables de entorno:

**frontend/src/config.js** (crear este archivo):

```javascript
export const API_URLS = {
  AUTH: import.meta.env.VITE_API_AUTH_URL || 'http://localhost:8081',
  CLIENT: import.meta.env.VITE_API_CLIENT_URL || 'http://localhost:8082',
  VISIT: import.meta.env.VITE_API_VISIT_URL || 'http://localhost:8083'
};
```

Luego actualizar los componentes para usar estas URLs.

---

## 🔐 Paso 7: Configurar CORS

Los servicios backend necesitan permitir requests desde el frontend en Railway.

Agregar a cada servicio (auth, client, visit):

**src/main/java/com/skynet/config/CorsConfig.java:**

```java
package com.skynet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
```

---

## 📊 Paso 8: Verificar Despliegue

### 8.1 Health Checks

Verificar que cada servicio responda:

```bash
curl https://auth-service-production.up.railway.app/health
curl https://client-service-production.up.railway.app/health
curl https://visit-service-production.up.railway.app/health
```

### 8.2 Probar Login

```bash
curl -X POST https://auth-service-production.up.railway.app/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"tecnico@skynet.com","password":"123456"}'
```

### 8.3 Verificar Frontend

Abrir la URL del frontend en el navegador y probar el login.

---

## 💰 Costos y Límites

### Plan Gratuito (Hobby)

- **Crédito:** $5/mes
- **Servicios:** Ilimitados
- **PostgreSQL:** 1GB storage
- **Bandwidth:** 100GB/mes
- **Build Time:** 500 horas/mes

### Estimación de Uso

Para SkyNet Microservices:
- 4 servicios (auth, client, visit, frontend)
- 1 PostgreSQL
- **Costo estimado:** $3-4/mes (dentro del plan gratuito)

---

## 🔄 CI/CD Automático

Railway despliega automáticamente cuando haces push a GitHub:

```bash
git add .
git commit -m "Update: nueva funcionalidad"
git push origin main
```

Railway detectará el cambio y redesplegar automáticamente.

---

## 📊 Monitoreo

### Ver Logs en Tiempo Real

1. Ir a tu servicio en Railway
2. Click en "Deployments"
3. Click en el deployment activo
4. Ver logs en tiempo real

### Métricas

Railway proporciona:
- CPU usage
- Memory usage
- Network traffic
- Request count

---

## 🐛 Troubleshooting

### Error: "Build failed"

```bash
# Verificar logs de build en Railway
# Común: falta de memoria

# Solución: Agregar en railway.json
{
  "build": {
    "builder": "NIXPACKS",
    "buildCommand": "mvn clean package -DskipTests -Dmaven.test.skip=true"
  }
}
```

### Error: "Service crashed"

```bash
# Ver logs del servicio
# Común: DATABASE_URL incorrecta

# Verificar variables de entorno
# Asegurar que DATABASE_URL esté configurada
```

### Error: "Connection timeout"

```bash
# Verificar que los servicios estén en el mismo proyecto Railway
# Los servicios en el mismo proyecto pueden comunicarse internamente
```

### Frontend no conecta con Backend

```bash
# Verificar CORS en backend
# Verificar variables VITE_API_* en frontend
# Verificar que las URLs sean HTTPS
```

---

## 🔐 Seguridad en Producción

### 1. Variables de Entorno Sensibles

Nunca commitear:
- Contraseñas de base de datos
- API keys
- Tokens secretos

Usar variables de entorno en Railway.

### 2. HTTPS

Railway proporciona HTTPS automáticamente para todos los servicios.

### 3. Rate Limiting

Considerar agregar rate limiting en producción:

```xml
<dependency>
    <groupId>com.github.vladimir-bukhtoyarov</groupId>
    <artifactId>bucket4j-core</artifactId>
    <version>7.6.0</version>
</dependency>
```

---

## 📈 Escalamiento

### Horizontal Scaling

Railway permite escalar horizontalmente:

1. Settings → Scaling
2. Aumentar "Replicas"
3. Railway distribuirá la carga automáticamente

### Vertical Scaling

Aumentar recursos por servicio:

1. Settings → Resources
2. Ajustar CPU y Memory

---

## 🔄 Rollback

Si algo sale mal:

1. Ir a "Deployments"
2. Seleccionar deployment anterior
3. Click "Redeploy"

---

## 📚 Recursos Adicionales

- [Railway Documentation](https://docs.railway.app/)
- [Railway Discord](https://discord.gg/railway)
- [Railway Blog](https://blog.railway.app/)
- [Spring Boot on Railway](https://docs.railway.app/guides/spring-boot)

---

## ✅ Checklist de Despliegue

- [ ] Crear cuenta en Railway
- [ ] Conectar GitHub
- [ ] Crear PostgreSQL database
- [ ] Desplegar auth-service
- [ ] Desplegar client-service
- [ ] Desplegar visit-service
- [ ] Configurar variables de entorno
- [ ] Agregar configuración CORS
- [ ] Desplegar frontend
- [ ] Configurar URLs del frontend
- [ ] Probar health checks
- [ ] Probar login
- [ ] Probar funcionalidad completa
- [ ] Configurar dominio personalizado (opcional)
- [ ] Configurar monitoreo
- [ ] Documentar URLs de producción

---

## 🎯 Próximos Pasos

1. **Dominio Personalizado:**
   - Comprar dominio
   - Configurar DNS en Railway
   - Ejemplo: `api.skynet.com`

2. **Monitoreo Avanzado:**
   - Integrar Sentry para error tracking
   - Configurar alertas
   - Dashboards personalizados

3. **Backups:**
   - Configurar backups automáticos de PostgreSQL
   - Exportar datos regularmente

4. **Performance:**
   - Agregar caché con Redis
   - Optimizar queries
   - CDN para frontend

---

**Estado:** ✅ **LISTO PARA DESPLEGAR**

**Plataforma:** Railway

**Costo Estimado:** $3-4/mes (dentro del plan gratuito)
