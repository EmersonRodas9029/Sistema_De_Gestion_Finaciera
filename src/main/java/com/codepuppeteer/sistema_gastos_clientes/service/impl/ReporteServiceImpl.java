package com.codepuppeteer.sistema_gastos_clientes.service.impl;

import com.codepuppeteer.sistema_gastos_clientes.dto.reporte.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Cliente;
import com.codepuppeteer.sistema_gastos_clientes.entity.Reporte;
import com.codepuppeteer.sistema_gastos_clientes.entity.Usuario;
import com.codepuppeteer.sistema_gastos_clientes.exception.ResourceNotFoundException;
import com.codepuppeteer.sistema_gastos_clientes.mapper.ReporteMapper;
import com.codepuppeteer.sistema_gastos_clientes.repository.ClienteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.ReporteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.UsuarioRepository;
import com.codepuppeteer.sistema_gastos_clientes.service.interfaces.ReporteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.List;
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

    @Override
    public byte[] generarPdf(long id) {
        Reporte reporte = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            document.add(new Paragraph(reporte.getNombre(), titleFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Cliente: " + reporte.getCliente().getNombreCompleto(), labelFont));
            document.add(new Paragraph("Tipo de reporte: " + reporte.getTipoReporte(), labelFont));
            document.add(new Paragraph("Periodo: " + reporte.getPeriodoInicio() + " a " + reporte.getPeriodoFin(), labelFont));
            document.add(new Paragraph("Generado: " + reporte.getFechaGeneracion(), labelFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Contenido", labelFont));
            document.add(new Paragraph(formatearContenido(reporte.getContenido()), bodyFont));
        } catch (DocumentException e) {
            throw new IllegalStateException("Error generando PDF del reporte " + id, e);
        } finally {
            document.close();
        }
        return out.toByteArray();
    }

    private String formatearContenido(String contenido) {
        if (contenido == null || contenido.isBlank()) return "(sin datos)";
        try {
            Object json = objectMapper.readValue(contenido, Object.class);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (JsonProcessingException e) {
            return contenido;
        }
    }
}
