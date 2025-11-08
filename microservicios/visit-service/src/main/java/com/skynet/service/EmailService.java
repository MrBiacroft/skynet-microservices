package com.skynet.service;

import com.skynet.model.Visita;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired
    private PdfService pdfService;
    
    @Autowired(required = false)
    private JavaMailSender mailSender;
    
    @Value("${spring.mail.username:skynet@example.com}")
    private String fromEmail;
    
    @Value("${email.enabled:false}")
    private boolean emailEnabled;
    
    public void enviarReporteVisita(Visita visita) {
        String asunto = "Reporte de Visita Técnica - SkyNet S.A.";
        String contenido = generarContenidoEmail(visita);
        byte[] pdfBytes = pdfService.generarReporteVisita(visita);
        
        if (emailEnabled && mailSender != null) {
            try {
                enviarEmailReal(visita.getClienteEmail(), asunto, contenido, pdfBytes, visita.getId());
                System.out.println("✅ Email enviado exitosamente a: " + visita.getClienteEmail());
            } catch (Exception e) {
                System.err.println("❌ Error enviando email: " + e.getMessage());
                imprimirEmailSimulado(visita.getClienteEmail(), asunto, contenido, pdfBytes, visita.getId());
            }
        } else {
            imprimirEmailSimulado(visita.getClienteEmail(), asunto, contenido, pdfBytes, visita.getId());
        }
    }
    
    private void enviarEmailReal(String destinatario, String asunto, String contenido, 
                                  byte[] pdfBytes, Long visitaId) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setFrom(fromEmail);
        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setText(contenido, false);
        
        // Adjuntar PDF
        helper.addAttachment("reporte-visita-" + visitaId + ".pdf", 
                           new ByteArrayResource(pdfBytes));
        
        mailSender.send(message);
    }
    
    private void imprimirEmailSimulado(String destinatario, String asunto, String contenido, 
                                       byte[] pdfBytes, Long visitaId) {
        System.out.println("═══════════════════════════════════════");
        System.out.println("📧 EMAIL (Simulación - email.enabled=false):");
        System.out.println("De: " + fromEmail);
        System.out.println("Para: " + destinatario);
        System.out.println("Asunto: " + asunto);
        System.out.println("Adjunto: reporte-visita-" + visitaId + ".pdf (" + pdfBytes.length + " bytes)");
        System.out.println("Contenido:");
        System.out.println(contenido);
        System.out.println("═══════════════════════════════════════");
    }
    
    private String generarContenidoEmail(Visita visita) {
        return String.format(
            "Estimado/a Cliente: %s\n\n" +
            "Le informamos que se ha completado la visita técnica programada:\n\n" +
            "📅 Fecha de Visita: %s\n" +
            "⏰ Hora Programada: %s\n" +
            "👨‍💼 Técnico Asignado: %s\n" +
            "📍 Dirección Visitada: %s\n\n" +
            "📋 Reporte de la Visita:\n%s\n\n" +
            "⏱️ Horarios de Ejecución:\n" +
            "   • Ingreso: %s\n" +
            "   • Egreso: %s\n\n" +
            "📍 Ubicación Registrada:\n" +
            "   • Latitud: %s\n" +
            "   • Longitud: %s\n\n" +
            "Agradecemos su confianza en SkyNet S.A.\n" +
            "¡Quedamos a su disposición para cualquier consulta!\n\n" +
            "Atentamente,\nEl equipo de SkyNet S.A.",
            visita.getClienteNombre(),
            visita.getFechaPlanificada(),
            visita.getHoraPlanificada(),
            visita.getTecnicoNombre(),
            visita.getClienteDireccion(),
            visita.getReporte() != null ? visita.getReporte() : "No se proporcionó reporte específico",
            visita.getFechaIngreso(),
            visita.getFechaEgreso(),
            visita.getLatitudIngreso(),
            visita.getLongitudIngreso()
        );
    }
}
