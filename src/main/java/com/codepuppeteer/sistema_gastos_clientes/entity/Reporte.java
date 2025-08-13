package com.codepuppeteer.sistema_gastos_clientes.entity;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoReporte;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "contador_id")
    private Usuario contador;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_reporte", nullable = false, length = 20)
    private TipoReporte tipoReporte;

    @Column(name = "periodo_inicio", nullable = false)
    private LocalDate periodoInicio;

    @Column(name = "periodo_fin", nullable = false)
    private LocalDate periodoFin;

    @Lob
    private String contenido; // JSON almacenado como texto

    @Column(name = "ruta_archivo", length = 500)
    private String rutaArchivo;

    @Column(name = "fecha_generacion", nullable = false)
    private LocalDateTime fechaGeneracion;

    @PrePersist
    protected void onCreate() {
        fechaGeneracion = LocalDateTime.now();
    }

}


