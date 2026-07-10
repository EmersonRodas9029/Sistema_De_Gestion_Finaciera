package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.reporte.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.entity.Reporte;
import com.codepuppeteer.sistema_gastos_clientes.entity.Usuario;
import com.codepuppeteer.sistema_gastos_clientes.enums.TipoReporte;
import com.codepuppeteer.sistema_gastos_clientes.exception.ResourceNotFoundException;
import com.codepuppeteer.sistema_gastos_clientes.mapper.ReporteMapper;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.ReporteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.UsuarioRepository;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.ReporteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository repository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ReporteMapper mapper;
    private final ObjectMapper objectMapper;

    @Override
    public Reporte crearReporte(ReporteSave dto) {
        Reporte reporte = mapper.toEntity(dto);

        Cliente cliente = clienteRepository.findById(Objects.requireNonNull(dto.clienteId()))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        reporte.setCliente(cliente);

        Long contadorId = dto.contadorId();
        if (contadorId != null) {
            Usuario contador = usuarioRepository.findById(contadorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Contador no encontrado"));
            reporte.setContador(contador);
        }

        return repository.save(reporte);
    }

    @Override
    public Reporte actualizarReporte(long id, ReporteUpdate dto) {
        Reporte existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado"));

        mapper.updateFromDto(dto, existente);

        Cliente cliente = clienteRepository.findById(Objects.requireNonNull(dto.clienteId()))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        existente.setCliente(cliente);

        Long contadorId = dto.contadorId();
        if (contadorId != null) {
            Usuario contador = usuarioRepository.findById(contadorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Contador no encontrado"));
            existente.setContador(contador);
        } else {
            existente.setContador(null);
        }

        return repository.save(existente);
    }

    @Override
    public void eliminarReporte(long id) {
        Reporte reporte = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado"));
        repository.delete(reporte);
    }

    @Override
    public Optional<Reporte> obtenerReportePorId(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Reporte> obtenerTodosLosReportes() {
        return repository.findAll();
    }

    @Override
    public List<Reporte> obtenerReportesPorCliente(long clienteId) {
        return repository.findByClienteId(clienteId);
    }

    // Paleta alineada al frontend: rosa de marca (#F05984), secundario (#BC455F).
    private static final Color BRAND = new Color(240, 89, 132);
    private static final Color BRAND_DARK = new Color(188, 69, 95);
    private static final Color TEXT_DARK = new Color(45, 45, 45);
    private static final Color TEXT_GRAY = new Color(130, 130, 130);
    private static final Color ROW_STRIPE = new Color(250, 240, 243);
    private static final Color BORDER_LIGHT = new Color(235, 225, 230);
    private static final DateTimeFormatter FECHA_FMT = DateTimeFormatter.ofPattern("d MMM yyyy", new Locale("es", "ES"));
    private static final DateTimeFormatter FECHA_HORA_FMT = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm", new Locale("es", "ES"));

    @Override
    public byte[] generarPdf(long id) {
        Reporte reporte = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 40, 40, 40, 40);
        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.WHITE);
            Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(255, 225, 235));
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13, BRAND_DARK);
            Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, TEXT_GRAY);
            Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 11, TEXT_DARK);
            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8, TEXT_GRAY);

            document.add(headerBanner(reporte, titleFont, subtitleFont));
            document.add(new Paragraph(" "));
            document.add(metaTable(reporte, labelFont, valueFont));
            document.add(new Paragraph(" "));

            LineSeparator divider = new LineSeparator();
            divider.setLineColor(BRAND);
            divider.setLineWidth(1.2f);
            document.add(divider);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Detalle del reporte", sectionFont));
            document.add(new Paragraph(" "));
            renderContenido(document, reporte.getContenido());

            document.add(new Paragraph(" "));
            LineSeparator footerDivider = new LineSeparator();
            footerDivider.setLineColor(BORDER_LIGHT);
            footerDivider.setLineWidth(0.75f);
            document.add(footerDivider);

            Paragraph footer = new Paragraph("Generado automáticamente el " + reporte.getFechaGeneracion().format(FECHA_HORA_FMT), footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(6);
            document.add(footer);
        } catch (DocumentException e) {
            throw new IllegalStateException("Error generando PDF del reporte " + id, e);
        } finally {
            document.close();
        }
        return out.toByteArray();
    }

    private PdfPTable headerBanner(Reporte reporte, Font titleFont, Font subtitleFont) throws DocumentException {
        PdfPTable header = new PdfPTable(1);
        header.setWidthPercentage(100);
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BRAND);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(18);
        cell.addElement(new Paragraph(reporte.getNombre(), titleFont));
        Paragraph subtitle = new Paragraph("Sistema de Gestión Financiera · " + tipoLabel(reporte.getTipoReporte()), subtitleFont);
        subtitle.setSpacingBefore(4);
        cell.addElement(subtitle);
        header.addCell(cell);
        return header;
    }

    private PdfPTable metaTable(Reporte reporte, Font labelFont, Font valueFont) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 1});
        addMetaCell(table, "CLIENTE", reporte.getCliente().getNombreCompleto(), labelFont, valueFont);
        addMetaCell(table, "CONTADOR", reporte.getContador() != null ? reporte.getContador().getUsername() : "Sin asignar", labelFont, valueFont);
        addMetaCell(table, "PERÍODO", reporte.getPeriodoInicio().format(FECHA_FMT) + "  –  " + reporte.getPeriodoFin().format(FECHA_FMT), labelFont, valueFont);
        addMetaCell(table, "GENERADO", reporte.getFechaGeneracion().format(FECHA_HORA_FMT), labelFont, valueFont);
        return table;
    }

    private void addMetaCell(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(6);
        cell.addElement(new Paragraph(label, labelFont));
        Paragraph v = new Paragraph(value, valueFont);
        v.setSpacingBefore(2);
        cell.addElement(v);
        table.addCell(cell);
    }

    private String tipoLabel(TipoReporte tipo) {
        if (tipo == null) return "Personalizado";
        return switch (tipo) {
            case GASTOS_MENSUAL -> "Gastos Mensual";
            case GASTOS_ANUAL -> "Gastos Anual";
            case INGRESOS_MENSUAL -> "Ingresos Mensual";
            case INGRESOS_ANUAL -> "Ingresos Anual";
            case PRESUPUESTO -> "Presupuesto";
            case PERSONALIZADO -> "Personalizado";
        };
    }

    private void renderContenido(Document document, String contenidoJson) throws DocumentException {
        Font emptyFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, TEXT_GRAY);
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10, TEXT_DARK);
        Font summaryLabelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, TEXT_GRAY);
        Font summaryValueFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15, BRAND_DARK);
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, TEXT_DARK);

        if (contenidoJson == null || contenidoJson.isBlank()) {
            document.add(new Paragraph("Este reporte no tiene contenido adicional.", emptyFont));
            return;
        }

        JsonNode root;
        try {
            root = objectMapper.readTree(contenidoJson);
        } catch (JsonProcessingException e) {
            document.add(new Paragraph(contenidoJson, cellFont));
            return;
        }

        if (root == null || !root.isObject()) {
            document.add(new Paragraph(root == null ? "(sin datos)" : root.toPrettyString(), cellFont));
            return;
        }

        List<Map.Entry<String, JsonNode>> scalarFields = new ArrayList<>();
        List<Map.Entry<String, JsonNode>> complexFields = new ArrayList<>();
        root.fields().forEachRemaining(e -> {
            if (e.getValue().isObject() || e.getValue().isArray()) complexFields.add(e);
            else scalarFields.add(e);
        });

        if (!scalarFields.isEmpty()) {
            PdfPTable summary = new PdfPTable(scalarFields.size());
            summary.setWidthPercentage(100);
            summary.setSpacingAfter(14);
            for (Map.Entry<String, JsonNode> e : scalarFields) {
                PdfPCell cell = new PdfPCell();
                cell.setBackgroundColor(ROW_STRIPE);
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setPadding(10);
                cell.addElement(new Paragraph(prettify(e.getKey()).toUpperCase(), summaryLabelFont));
                Paragraph value = new Paragraph(formatValue(e.getValue()), summaryValueFont);
                value.setSpacingBefore(4);
                cell.addElement(value);
                summary.addCell(cell);
            }
            document.add(summary);
        }

        for (Map.Entry<String, JsonNode> e : complexFields) {
            Paragraph subtitle = new Paragraph(prettify(e.getKey()), subtitleFont);
            subtitle.setSpacingBefore(4);
            subtitle.setSpacingAfter(6);
            document.add(subtitle);

            if (e.getValue().isObject()) {
                document.add(keyValueTable(e.getValue(), headerFont, cellFont));
            } else {
                document.add(arrayTable(e.getValue(), headerFont, cellFont));
            }
        }
    }

    private PdfPTable keyValueTable(JsonNode obj, Font headerFont, Font cellFont) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{2, 1});
        table.setSpacingAfter(12);
        addHeaderCell(table, "Concepto", headerFont);
        addHeaderCell(table, "Valor", headerFont);
        boolean stripe = false;
        Iterator<Map.Entry<String, JsonNode>> it = obj.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> f = it.next();
            Color bg = stripe ? ROW_STRIPE : Color.WHITE;
            addBodyCell(table, prettify(f.getKey()), cellFont, bg, Element.ALIGN_LEFT);
            addBodyCell(table, formatValue(f.getValue()), cellFont, bg, Element.ALIGN_RIGHT);
            stripe = !stripe;
        }
        return table;
    }

    private PdfPTable arrayTable(JsonNode array, Font headerFont, Font cellFont) throws DocumentException {
        if (array.isEmpty()) {
            PdfPTable empty = new PdfPTable(1);
            empty.setWidthPercentage(100);
            empty.setSpacingAfter(12);
            addBodyCell(empty, "(sin datos)", cellFont, Color.WHITE, Element.ALIGN_LEFT);
            return empty;
        }

        JsonNode first = array.get(0);
        PdfPTable table;
        boolean stripe = false;

        if (first.isObject()) {
            List<String> keys = new ArrayList<>();
            first.fieldNames().forEachRemaining(keys::add);
            table = new PdfPTable(keys.size());
            table.setWidthPercentage(100);
            table.setSpacingAfter(12);
            for (String k : keys) addHeaderCell(table, prettify(k), headerFont);
            for (JsonNode item : array) {
                Color bg = stripe ? ROW_STRIPE : Color.WHITE;
                for (String k : keys) {
                    addBodyCell(table, item.has(k) ? formatValue(item.get(k)) : "—", cellFont, bg, Element.ALIGN_LEFT);
                }
                stripe = !stripe;
            }
        } else {
            table = new PdfPTable(1);
            table.setWidthPercentage(100);
            table.setSpacingAfter(12);
            for (JsonNode item : array) {
                Color bg = stripe ? ROW_STRIPE : Color.WHITE;
                addBodyCell(table, "• " + formatValue(item), cellFont, bg, Element.ALIGN_LEFT);
                stripe = !stripe;
            }
        }
        return table;
    }

    private void addHeaderCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(BRAND_DARK);
        cell.setPadding(8);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
    }

    private void addBodyCell(PdfPTable table, String text, Font font, Color background, int align) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(background);
        cell.setPadding(7);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderColor(BORDER_LIGHT);
        cell.setBorderWidth(0.5f);
        cell.setHorizontalAlignment(align);
        table.addCell(cell);
    }

    private String prettify(String key) {
        if (key == null || key.isBlank()) return key;
        String spaced = key.replaceAll("([a-z])([A-Z])", "$1 $2").replace('_', ' ');
        return Character.toUpperCase(spaced.charAt(0)) + spaced.substring(1);
    }

    private String formatValue(JsonNode node) {
        if (node.isNumber()) {
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
            nf.setMinimumFractionDigits(2);
            nf.setMaximumFractionDigits(2);
            return "$" + nf.format(node.doubleValue());
        }
        if (node.isBoolean()) return node.booleanValue() ? "Sí" : "No";
        if (node.isNull()) return "—";
        return node.asText();
    }
}
