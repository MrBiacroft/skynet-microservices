# 📧 Configurar Email Real para Enviar a bryancano14@hotmail.com

## 🎯 Objetivo
Configurar el sistema para que envíe emails reales cuando se completen visitas.

---

## ✅ Lo que ya está listo

1. ✅ **Visita de prueba creada** (ID: 6)
   - Cliente: Bryan Cano - Prueba Email
   - Email destino: bryancano14@hotmail.com
   - Estado: COMPLETADA
   - PDF generado: 2.4 KB

2. ✅ **Sistema configurado** para enviar emails
   - Código implementado con JavaMailSender
   - PDF se adjunta automáticamente
   - Solo falta habilitar el envío real

3. ✅ **Actualmente en modo simulación**
   - El email se imprime en logs de Railway
   - No se envía realmente (por seguridad)

---

## 🚀 Pasos para Habilitar Email Real

### Opción 1: Usar Gmail (Recomendado - 5 minutos)

#### Paso 1: Crear App Password en Gmail

1. **Ve a tu cuenta de Gmail** (usa cualquier cuenta Gmail que tengas)
   - URL: [https://myaccount.google.com/security](https://myaccount.google.com/security)

2. **Habilita la verificación en 2 pasos**:
   - Busca "Verificación en 2 pasos"
   - Click en "Activar"
   - Sigue los pasos (te pedirá tu teléfono)

3. **Genera una contraseña de aplicación**:
   - Vuelve a [https://myaccount.google.com/security](https://myaccount.google.com/security)
   - Busca "Contraseñas de aplicaciones" (al final de la página)
   - Click en "Contraseñas de aplicaciones"
   - Selecciona "Correo" y "Otro (nombre personalizado)"
   - Escribe: "SkyNet System"
   - Click en "Generar"
   - **Copia la contraseña de 16 caracteres** (ej: `abcd efgh ijkl mnop`)

#### Paso 2: Configurar en Railway

1. **Ve a Railway**:
   - URL: [https://railway.app](https://railway.app)
   - Login con tu cuenta

2. **Selecciona el proyecto SkyNet**

3. **Selecciona el servicio `visit-service`**

4. **Ve a la pestaña "Variables"**

5. **Agrega estas 3 variables**:

   **Variable 1:**
   ```
   Nombre: EMAIL_ENABLED
   Valor: true
   ```

   **Variable 2:**
   ```
   Nombre: MAIL_USERNAME
   Valor: tu-email@gmail.com
   ```
   ⚠️ Reemplaza con tu email de Gmail real

   **Variable 3:**
   ```
   Nombre: MAIL_PASSWORD
   Valor: abcd efgh ijkl mnop
   ```
   ⚠️ Reemplaza con la contraseña de 16 caracteres que copiaste

6. **Guarda los cambios**

7. **Railway redesplegará automáticamente** (tarda ~2 minutos)

#### Paso 3: Probar el Envío

Una vez que Railway termine de redesplegar:

**Opción A: Desde el Dashboard (Recomendado)**

1. Login como técnico en [https://frontend-production-69af.up.railway.app](https://frontend-production-69af.up.railway.app)
   - Email: `tecnico@skynet.com`
   - Password: `123456`

2. Verás la visita "Bryan Cano - Prueba Email" con estado COMPLETADA

3. Ya se envió el email cuando se completó, pero puedes crear una nueva:
   - Crea una nueva visita con tu email
   - Complétala
   - El email se enviará automáticamente

**Opción B: Desde la API (Más Rápido)**

```bash
# 1. Crear nueva visita
curl -X POST https://visit.up.railway.app/api/visitas \
  -H "Content-Type: application/json" \
  -d '{
    "clienteId": 99,
    "clienteNombre": "Bryan Cano - Test 2",
    "clienteEmail": "bryancano14@hotmail.com",
    "clienteDireccion": "Guatemala City",
    "clienteLatitud": 14.6038,
    "clienteLongitud": -90.5069,
    "tecnicoId": 3,
    "tecnicoNombre": "Carlos López",
    "supervisorId": 2,
    "fechaPlanificada": "2025-11-08",
    "horaPlanificada": "16:00:00"
  }'

# Anota el ID que te devuelve (ej: 7)

# 2. Registrar ingreso
curl -X POST https://visit.up.railway.app/api/visitas/7/registrar-ingreso \
  -H "Content-Type: application/json" \
  -d '{"latitud": 14.6040, "longitud": -90.5070}'

# 3. Completar visita (ESTO ENVIARÁ EL EMAIL)
curl -X POST https://visit.up.railway.app/api/visitas/7/registrar-egreso \
  -H "Content-Type: application/json" \
  -d '{"reporte": "Prueba de envío de email desde SkyNet"}'
```

#### Paso 4: Verificar el Email

1. **Revisa tu bandeja de entrada**: bryancano14@hotmail.com

2. **Busca un email de**: tu-email@gmail.com (el que configuraste)

3. **Asunto**: "Reporte de Visita Técnica - SkyNet S.A."

4. **Contenido**:
   - Información de la visita
   - Reporte del técnico
   - **Adjunto**: `reporte-visita-X.pdf`

5. **Si no llega**:
   - Revisa la carpeta de SPAM
   - Espera 1-2 minutos
   - Verifica los logs de Railway

---

### Opción 2: Usar Mailtrap (Solo para Testing)

Si solo quieres probar sin enviar emails reales:

1. **Crea cuenta en Mailtrap**:
   - URL: [https://mailtrap.io](https://mailtrap.io)
   - Signup gratis

2. **Obtén credenciales**:
   - Ve a tu inbox
   - Copia las credenciales SMTP

3. **Configura en Railway**:
   ```
   EMAIL_ENABLED=true
   MAIL_HOST=smtp.mailtrap.io
   MAIL_PORT=2525
   MAIL_USERNAME=tu-username-mailtrap
   MAIL_PASSWORD=tu-password-mailtrap
   ```

⚠️ **Nota**: Mailtrap NO envía emails reales, solo los captura para que los veas en su interfaz.

---

## 🔍 Verificar que Funcionó

### En Railway (Logs)

1. Ve al servicio `visit-service` en Railway
2. Click en "Logs"
3. Busca:

**Si funcionó**:
```
✅ Email enviado exitosamente a: bryancano14@hotmail.com
```

**Si falló**:
```
❌ Error enviando email: [mensaje de error]
```

**Si está en simulación**:
```
📧 EMAIL (Simulación - email.enabled=false):
Para: bryancano14@hotmail.com
```

---

## ❌ Problemas Comunes

### "Authentication failed"
**Causa**: Contraseña incorrecta o no es App Password

**Solución**:
1. Verifica que usaste la contraseña de 16 caracteres (App Password)
2. NO uses tu contraseña normal de Gmail
3. Asegúrate de haber habilitado verificación en 2 pasos

### "Connection timeout"
**Causa**: Variables mal configuradas

**Solución**:
1. Verifica que `MAIL_USERNAME` sea tu email completo
2. Verifica que `EMAIL_ENABLED=true` (no "True" ni "TRUE")
3. Redeploy el servicio manualmente

### Email no llega
**Causa**: En carpeta de spam o email no enviado

**Solución**:
1. Revisa SPAM en Hotmail
2. Verifica logs de Railway
3. Espera 2-3 minutos
4. Intenta con otro email de prueba

---

## 📊 Resumen

### Lo que tienes ahora:
- ✅ Visita de prueba creada (ID: 6)
- ✅ Email destino: bryancano14@hotmail.com
- ✅ PDF generado (2.4 KB)
- ⚙️ Sistema en modo simulación

### Lo que necesitas hacer:
1. ⚙️ Crear App Password en Gmail (5 min)
2. ⚙️ Configurar 3 variables en Railway (2 min)
3. ⚙️ Esperar redespliegue (2 min)
4. ✅ Probar y recibir el email

### Tiempo total: ~10 minutos

---

## 🆘 ¿Necesitas Ayuda?

Si tienes problemas:
1. Revisa los logs de Railway
2. Verifica que las variables estén bien escritas
3. Asegúrate de usar App Password (no contraseña normal)
4. Prueba primero con Mailtrap para debugging

---

**Una vez configurado, TODOS los emails futuros se enviarán automáticamente cuando se completen visitas.** 📧✅
