package com.skynet.service;

import com.skynet.model.Visita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired
    private PdfService pdfService;
    
    public void enviarReporteVisita(Visita visita) {
        // En desarrollo, solo imprimimos el email en consola
        // En producción, se integraría con un servicio de email real (JavaMailSender)
        
        String asunto = "📋 Reporte de Visita Técnica - SkyNet S.A.";
        String contenido = generarContenidoEmail(visita);
        
        // Generar PDF para adjuntar
        byte[] pdfBytes = pdfService.generarReporteVisita(visita);
        
        System.out.println("═══════════════════════════════════════");
        System.out.println("📧 EMAIL ENVIADO (Simulación):");
        System.out.println("Para: " + visita.getClienteEmail());
        System.out.println("Asunto: " + asunto);
        System.out.println("Adjunto: reporte-visita-" + visita.getId() + ".pdf (" + pdfBytes.length + " bytes)");
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
