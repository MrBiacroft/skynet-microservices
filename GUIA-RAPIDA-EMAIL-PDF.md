# 🚀 Guía Rápida: Email y PDF en SkyNet

## ✅ ¿Qué está implementado?

### 1. **Botón de Descarga de PDF** 📄
- ✅ Aparece en el dashboard del técnico
- ✅ Solo visible para visitas **COMPLETADAS**
- ✅ Descarga directa del reporte en PDF
- ✅ Incluye toda la información de la visita

### 2. **Envío Automático de Email** 📧
- ✅ Se envía automáticamente al completar una visita
- ✅ Incluye PDF adjunto
- ✅ Modo simulación por defecto (logs en consola)
- ✅ Modo real configurable (Gmail, SendGrid, Mailtrap)

---

## 🎯 Cómo Ver el Botón de PDF

### Requisito: Tener una visita COMPLETADA

El botón **solo aparece** para visitas con estado `COMPLETADA`. Para ver el botón:

1. **Inicia sesión como técnico**:
   - Email: `tecnico@skynet.com`
   - Password: `123456`

2. **Verifica que haya visitas completadas**:
   - El dashboard muestra "Visitas de Hoy"
   - Debe haber al menos una visita con estado "COMPLETADA"

3. **Si no hay visitas completadas**, crea una:

```bash
# Paso 1: Crear visita para hoy
curl -X POST https://visit.up.railway.app/api/visitas \
  -H "Content-Type: application/json" \
  -d '{
    "clienteId": 1,
    "clienteNombre": "Empresa ABC S.A.",
    "clienteEmail": "contacto@empresaabc.com",
    "clienteDireccion": "15 Calle 1-25, Zona 10",
    "clienteLatitud": 14.6038,
    "clienteLongitud": -90.5069,
    "tecnicoId": 3,
    "tecnicoNombre": "Carlos López Técnico",
    "supervisorId": 2,
    "fechaPlanificada": "2025-11-08",
    "horaPlanificada": "10:00:00"
  }'

# Paso 2: Registrar ingreso (reemplaza {ID})
curl -X POST https://visit.up.railway.app/api/visitas/{ID}/registrar-ingreso \
  -H "Content-Type: application/json" \
  -d '{"latitud": 14.6040, "longitud": -90.5070}'

# Paso 3: Completar visita
curl -X POST https://visit.up.railway.app/api/visitas/{ID}/registrar-egreso \
  -H "Content-Type: application/json" \
  -d '{"reporte": "Visita completada exitosamente. Equipo funcionando correctamente."}'
```

4. **Recarga el dashboard del técnico**:
   - Verás la visita con estado "COMPLETADA"
   - Aparecerá el botón rojo "Descargar PDF"

---

## 📧 Cómo Habilitar Email Real

### Actualmente: Modo Simulación
Por defecto, el sistema imprime los emails en los logs de Railway (no envía emails reales).

### Para Habilitar Email Real:

#### Opción 1: Gmail (Más Fácil)

1. **Crear App Password**:
   - Ve a [https://myaccount.google.com/security](https://myaccount.google.com/security)
   - Habilita "Verificación en 2 pasos"
   - Busca "Contraseñas de aplicaciones"
   - Genera una contraseña para "Correo"
   - Copia la contraseña de 16 caracteres

2. **Configurar en Railway**:
   - Ve al servicio `visit-service`
   - Sección "Variables"
   - Agrega:
   ```
   EMAIL_ENABLED=true
   MAIL_USERNAME=tu-email@gmail.com
   MAIL_PASSWORD=xxxx xxxx xxxx xxxx
   ```

3. **Redeploy**:
   - Railway detectará los cambios automáticamente

#### Opción 2: SendGrid (Recomendado para Producción)

1. Crea cuenta en [SendGrid](https://sendgrid.com)
2. Genera API Key
3. Configura en Railway:
   ```
   EMAIL_ENABLED=true
   MAIL_HOST=smtp.sendgrid.net
   MAIL_USERNAME=apikey
   MAIL_PASSWORD=tu-sendgrid-api-key
   ```

#### Opción 3: Mailtrap (Solo Testing)

1. Crea cuenta en [Mailtrap](https://mailtrap.io)
2. Obtén credenciales SMTP
3. Configura en Railway:
   ```
   EMAIL_ENABLED=true
   MAIL_HOST=smtp.mailtrap.io
   MAIL_PORT=2525
   MAIL_USERNAME=tu-username
   MAIL_PASSWORD=tu-password
   ```

⚠️ **Nota**: Mailtrap NO envía emails reales, solo los captura para testing.

---

## 🧪 Probar el Sistema Completo

### Flujo Completo:

1. **Login como técnico** en [https://frontend-production-69af.up.railway.app](https://frontend-production-69af.up.railway.app)

2. **Ver visitas del día** en el dashboard

3. **Iniciar una visita**:
   - Click en "Iniciar Visita" (botón verde)
   - Estado cambia a "EN_CURSO"

4. **Completar la visita**:
   - Click en "Completar" (botón azul)
   - Ingresa el reporte
   - Estado cambia a "COMPLETADA"
   - 📧 **Email se envía automáticamente**

5. **Descargar PDF**:
   - Aparece botón rojo "Descargar PDF"
   - Click para descargar el reporte

6. **Verificar email**:
   - Si `EMAIL_ENABLED=false`: Ver logs de Railway
   - Si `EMAIL_ENABLED=true`: Revisar bandeja de entrada

---

## 📊 Estado Actual del Sistema

### ✅ Implementado y Funcionando

| Funcionalidad | Estado | Ubicación |
|---------------|--------|-----------|
| **Generación de PDF** | ✅ ACTIVO | Backend automático |
| **Descarga de PDF** | ✅ ACTIVO | Dashboard técnico |
| **Email simulado** | ✅ ACTIVO | Logs de Railway |
| **Email real** | ⚙️ CONFIGURABLE | Requiere variables |

### 📍 Visitas en el Sistema

Actualmente hay **5 visitas** en la base de datos:
- ID 1-2: PLANIFICADA (fechas anteriores)
- ID 3: EN_CURSO (fecha anterior)
- ID 4: COMPLETADA (fecha anterior)
- ID 5: COMPLETADA (hoy) ← **Esta tiene botón de PDF**

---

## 🎯 Resumen para el Usuario

### Para ver el botón de PDF:
1. ✅ Login como técnico
2. ✅ Debe haber visitas completadas para hoy
3. ✅ El botón aparece automáticamente

### Para habilitar email real:
1. ⚙️ Configura variables en Railway
2. ⚙️ Usa Gmail, SendGrid o Mailtrap
3. ⚙️ Sigue la guía en `documentacion/CONFIGURACION-EMAIL.md`

### El sistema ya está funcionando:
- ✅ PDF se genera automáticamente
- ✅ Email se envía al completar visita (simulado)
- ✅ Botón de descarga disponible
- ✅ Todo listo para producción

---

## 📚 Documentación Completa

Para más detalles, consulta:
- **Email**: `documentacion/CONFIGURACION-EMAIL.md`
- **Estado del Sistema**: `ESTADO-SISTEMA.md`
- **README**: `README.md`

---

## 🆘 Problemas Comunes

### "No veo el botón de PDF"
**Causa**: No hay visitas completadas para hoy

**Solución**: 
1. Verifica que estés logueado como técnico
2. Crea una visita para hoy y compĺetala
3. O usa los comandos curl arriba

### "El email no se envía"
**Causa**: `EMAIL_ENABLED=false` (modo simulación)

**Solución**:
1. Configura variables en Railway
2. O revisa los logs para ver el email simulado

### "Error al descargar PDF"
**Causa**: Visita sin datos completos

**Solución**:
1. Verifica que la visita tenga reporte
2. Prueba el endpoint directamente: `GET /api/visitas/{id}/reporte-pdf`

---

**Última actualización**: 2025-11-08  
**Versión**: v1.1-complete  
**Estado**: ✅ SISTEMA OPERATIVO
