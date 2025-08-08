package com.codepuppeteer.sistema_gastos_clientes.model.entity;

import com.codepuppeteer.sistema_gastos_clientes.model.enums.TipoReporte;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private TipoReporte tipoReporte;

    private LocalDateTime fechaGeneracion;
    private String rutaArchivo;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}