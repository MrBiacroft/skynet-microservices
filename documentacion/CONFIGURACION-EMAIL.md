# 📧 Configuración de Email en SkyNet

## 🎯 Descripción

El sistema SkyNet envía automáticamente un email con el reporte PDF adjunto cuando se completa una visita técnica.

Por defecto, el sistema funciona en **modo simulación** (imprime en consola). Para habilitar el envío real de emails, sigue esta guía.

---

## 🔧 Configuración en Railway

### Opción 1: Gmail (Recomendado para pruebas)

1. **Crear App Password en Gmail**:
   - Ve a [https://myaccount.google.com/security](https://myaccount.google.com/security)
   - Habilita "Verificación en 2 pasos"
   - Ve a "Contraseñas de aplicaciones"
   - Genera una nueva contraseña para "Correo"
   - Copia la contraseña de 16 caracteres

2. **Configurar variables en Railway**:
   - Ve al servicio `visit-service` en Railway
   - Sección "Variables"
   - Agrega las siguientes variables:

   ```
   EMAIL_ENABLED=true
   MAIL_HOST=smtp.gmail.com
   MAIL_PORT=587
   MAIL_USERNAME=tu-email@gmail.com
   MAIL_PASSWORD=xxxx xxxx xxxx xxxx
   ```

3. **Redeploy el servicio**:
   - Railway detectará los cambios automáticamente
   - O haz click en "Redeploy"

---

### Opción 2: SendGrid (Recomendado para producción)

1. **Crear cuenta en SendGrid**:
   - Ve a [https://sendgrid.com](https://sendgrid.com)
   - Crea una cuenta gratuita (100 emails/día)
   - Verifica tu email

2. **Crear API Key**:
   - Settings → API Keys
   - Create API Key
   - Copia la API key

3. **Configurar variables en Railway**:
   ```
   EMAIL_ENABLED=true
   MAIL_HOST=smtp.sendgrid.net
   MAIL_PORT=587
   MAIL_USERNAME=apikey
   MAIL_PASSWORD=tu-sendgrid-api-key
   ```

---

### Opción 3: Mailtrap (Solo para testing)

1. **Crear cuenta en Mailtrap**:
   - Ve a [https://mailtrap.io](https://mailtrap.io)
   - Crea una cuenta gratuita
   - Crea un inbox

2. **Obtener credenciales SMTP**:
   - En tu inbox, ve a "SMTP Settings"
   - Copia las credenciales

3. **Configurar variables en Railway**:
   ```
   EMAIL_ENABLED=true
   MAIL_HOST=smtp.mailtrap.io
   MAIL_PORT=2525
   MAIL_USERNAME=tu-username-mailtrap
   MAIL_PASSWORD=tu-password-mailtrap
   ```

⚠️ **Nota**: Mailtrap NO envía emails reales, solo los captura para testing.

---

## 🧪 Probar el Envío de Email

### 1. Completar una visita

```bash
# 1. Crear visita
curl -X POST https://visit.up.railway.app/api/visitas \
  -H "Content-Type: application/json" \
  -d '{
    "clienteId": 1,
    "clienteNombre": "Test Cliente",
    "clienteEmail": "tu-email@gmail.com",
    "clienteDireccion": "Calle Test 123",
    "clienteLatitud": 14.6,
    "clienteLongitud": -90.5,
    "tecnicoId": 3,
    "tecnicoNombre": "Carlos López",
    "supervisorId": 2,
    "fechaPlanificada": "2025-11-09",
    "horaPlanificada": "10:00:00"
  }'

# 2. Registrar ingreso (reemplaza {ID} con el ID de la visita)
curl -X POST https://visit.up.railway.app/api/visitas/{ID}/registrar-ingreso \
  -H "Content-Type: application/json" \
  -d '{"latitud": 14.6, "longitud": -90.5}'

# 3. Completar visita (esto enviará el email)
curl -X POST https://visit.up.railway.app/api/visitas/{ID}/registrar-egreso \
  -H "Content-Type: application/json" \
  -d '{"reporte": "Visita completada exitosamente"}'
```

### 2. Verificar logs en Railway

- Ve al servicio `visit-service` en Railway
- Click en "Logs"
- Busca el mensaje de email enviado

**Si email.enabled=false** (simulación):
```
📧 EMAIL (Simulación - email.enabled=false):
Para: cliente@example.com
Adjunto: reporte-visita-5.pdf (1900 bytes)
```

**Si email.enabled=true** (real):
```
✅ Email enviado exitosamente a: cliente@example.com
```

---

## 📋 Contenido del Email

El email incluye:

### Asunto
```
Reporte de Visita Técnica - SkyNet S.A.
```

### Cuerpo
```
Estimado/a Cliente: [Nombre]

Le informamos que se ha completado la visita técnica programada:

📅 Fecha de Visita: 2025-11-09
⏰ Hora Programada: 10:00:00
👨‍💼 Técnico Asignado: Carlos López
📍 Dirección Visitada: Calle Test 123

📋 Reporte de la Visita:
[Reporte del técnico]

⏱️ Horarios de Ejecución:
   • Ingreso: 2025-11-09 10:05:00
   • Egreso: 2025-11-09 11:30:00

📍 Ubicación Registrada:
   • Latitud: 14.6
   • Longitud: -90.5

Agradecemos su confianza en SkyNet S.A.
¡Quedamos a su disposición para cualquier consulta!

Atentamente,
El equipo de SkyNet S.A.
```

### Adjunto
- **Archivo**: `reporte-visita-{ID}.pdf`
- **Tamaño**: ~1.7-2.0 KB
- **Contenido**: Reporte completo de la visita en formato PDF

---

## 🔒 Seguridad

### Gmail App Passwords
- ✅ Más seguro que usar tu contraseña real
- ✅ Puedes revocar el acceso en cualquier momento
- ✅ No expone tu contraseña principal

### Variables de Entorno
- ✅ Las credenciales se almacenan de forma segura en Railway
- ✅ No se exponen en el código fuente
- ✅ No se incluyen en los logs

### Recomendaciones
- 🔐 Nunca compartas tus credenciales SMTP
- 🔐 Usa App Passwords, no contraseñas reales
- 🔐 Revoca credenciales si sospechas compromiso
- 🔐 Usa SendGrid en producción (más confiable)

---

## ❌ Solución de Problemas

### Error: "Authentication failed"
**Causa**: Credenciales incorrectas o App Password no configurado

**Solución**:
1. Verifica que `MAIL_USERNAME` sea correcto
2. Verifica que `MAIL_PASSWORD` sea el App Password (no tu contraseña normal)
3. Asegúrate de haber habilitado "Verificación en 2 pasos" en Gmail

### Error: "Connection timeout"
**Causa**: Puerto bloqueado o host incorrecto

**Solución**:
1. Verifica `MAIL_HOST` y `MAIL_PORT`
2. Para Gmail: `smtp.gmail.com:587`
3. Para SendGrid: `smtp.sendgrid.net:587`

### Email no llega
**Causa**: Email en spam o credenciales incorrectas

**Solución**:
1. Revisa la carpeta de spam
2. Verifica los logs de Railway para errores
3. Prueba con Mailtrap primero para debugging

### Email se envía pero sin PDF
**Causa**: Error en generación de PDF

**Solución**:
1. Verifica que la visita tenga todos los datos
2. Revisa logs para errores de iText7
3. Prueba descargar el PDF manualmente: `GET /api/visitas/{id}/reporte-pdf`

---

## 📊 Límites de Envío

| Proveedor | Plan Gratuito | Límite Diario |
|-----------|---------------|---------------|
| **Gmail** | Gratis | 500 emails/día |
| **SendGrid** | Gratis | 100 emails/día |
| **Mailtrap** | Gratis | Ilimitado (solo testing) |

---

## 🚀 Modo Producción

Para producción, se recomienda:

1. **Usar SendGrid** (más confiable que Gmail)
2. **Configurar dominio verificado** en SendGrid
3. **Habilitar tracking** de emails
4. **Configurar templates** HTML personalizados
5. **Implementar cola de emails** (Redis + Bull)

---

## 📝 Ejemplo Completo

### Variables en Railway (Gmail)
```bash
EMAIL_ENABLED=true
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=skynet.notificaciones@gmail.com
MAIL_PASSWORD=abcd efgh ijkl mnop
```

### Resultado
Cuando un técnico completa una visita:
1. ✅ Sistema genera PDF automáticamente
2. ✅ Email se envía al cliente con PDF adjunto
3. ✅ Log confirma envío exitoso
4. ✅ Cliente recibe email en su bandeja

---

## 🆘 Soporte

Si tienes problemas configurando el email:
1. Revisa los logs de Railway
2. Verifica las variables de entorno
3. Prueba primero con Mailtrap
4. Contacta al equipo de desarrollo

---

**Última actualización**: 2025-11-08  
**Versión**: v1.1-complete
