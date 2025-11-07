package com.skynet.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.skynet.model.Visita;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public byte[] generarReporteVisita(Visita visita) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);
            
            // Título
            Paragraph titulo = new Paragraph("REPORTE DE VISITA TÉCNICA")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(titulo);
            
            document.add(new Paragraph("\n"));
            
            // Información de la visita
            document.add(new Paragraph("ID de Visita: " + visita.getId())
                    .setFontSize(12)
                    .setBold());
            
            document.add(new Paragraph("Estado: " + visita.getEstado())
                    .setFontSize(12));
            
            document.add(new Paragraph("\n"));
            
            // Tabla de información del cliente
            document.add(new Paragraph("INFORMACIÓN DEL CLIENTE")
                    .setFontSize(14)
                    .setBold());
            
            Table clienteTable = new Table(UnitValue.createPercentArray(new float[]{30, 70}))
                    .useAllAvailableWidth();
            
            clienteTable.addCell("Nombre:");
            clienteTable.addCell(visita.getClienteNombre() != null ? visita.getClienteNombre() : "N/A");
            
            clienteTable.addCell("Email:");
            clienteTable.addCell(visita.getClienteEmail() != null ? visita.getClienteEmail() : "N/A");
            
            clienteTable.addCell("Dirección:");
            clienteTable.addCell(visita.getClienteDireccion() != null ? visita.getClienteDireccion() : "N/A");
            
            if (visita.getClienteLatitud() != null && visita.getClienteLongitud() != null) {
                clienteTable.addCell("Coordenadas:");
                clienteTable.addCell(String.format("%.6f, %.6f", 
                        visita.getClienteLatitud(), visita.getClienteLongitud()));
            }
            
            document.add(clienteTable);
            document.add(new Paragraph("\n"));
            
            // Tabla de información de la visita
            document.add(new Paragraph("DETALLES DE LA VISITA")
                    .setFontSize(14)
                    .setBold());
            
            Table visitaTable = new Table(UnitValue.createPercentArray(new float[]{30, 70}))
                    .useAllAvailableWidth();
            
            visitaTable.addCell("Técnico:");
            visitaTable.addCell(visita.getTecnicoNombre() != null ? visita.getTecnicoNombre() : "N/A");
            
            visitaTable.addCell("Fecha Planificada:");
            visitaTable.addCell(visita.getFechaPlanificada() != null ? 
                    visita.getFechaPlanificada().format(DATE_FORMATTER) : "N/A");
            
            visitaTable.addCell("Hora Planificada:");
            visitaTable.addCell(visita.getHoraPlanificada() != null ? 
                    visita.getHoraPlanificada().format(TIME_FORMATTER) : "N/A");
            
            if (visita.getFechaIngreso() != null) {
                visitaTable.addCell("Fecha/Hora Ingreso:");
                visitaTable.addCell(visita.getFechaIngreso().format(DATETIME_FORMATTER));
            }
            
            if (visita.getFechaEgreso() != null) {
                visitaTable.addCell("Fecha/Hora Egreso:");
                visitaTable.addCell(visita.getFechaEgreso().format(DATETIME_FORMATTER));
            }
            
            if (visita.getLatitudIngreso() != null && visita.getLongitudIngreso() != null) {
                visitaTable.addCell("Ubicación Ingreso:");
                visitaTable.addCell(String.format("%.6f, %.6f", 
                        visita.getLatitudIngreso(), visita.getLongitudIngreso()));
            }
            
            document.add(visitaTable);
            document.add(new Paragraph("\n"));
            
            // Reporte del técnico
            if (visita.getReporte() != null && !visita.getReporte().isEmpty()) {
                document.add(new Paragraph("REPORTE DEL TÉCNICO")
                        .setFontSize(14)
                        .setBold());
                
                document.add(new Paragraph(visita.getReporte())
                        .setFontSize(11));
                
                document.add(new Paragraph("\n"));
            }
            
            // Pie de página
            document.add(new Paragraph("_".repeat(80))
                    .setTextAlignment(TextAlignment.CENTER));
            
            document.add(new Paragraph("Documento generado el: " + 
                    java.time.LocalDateTime.now().format(DATETIME_FORMATTER))
                    .setFontSize(9)
                    .setTextAlignment(TextAlignment.CENTER));
            
            document.add(new Paragraph("SkyNet - Sistema de Gestión de Visitas Técnicas")
                    .setFontSize(9)
                    .setTextAlignment(TextAlignment.CENTER));
            
            document.close();
            
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF: " + e.getMessage(), e);
        }
        
        return baos.toByteArray();
    }
}
