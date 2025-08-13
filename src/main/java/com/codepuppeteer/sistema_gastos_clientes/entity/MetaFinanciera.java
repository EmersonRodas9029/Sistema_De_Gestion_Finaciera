package com.codepuppeteer.sistema_gastos_clientes.entity;

import com.codepuppeteer.sistema_gastos_clientes.enums.Prioridad;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "metas_financieras")
public class MetaFinanciera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Lob
    private String descripcion;

    @Column(name = "monto_objetivo", nullable = false)
    private Double montoObjetivo;

    @Column(name = "monto_actual")
    private Double montoActual = 0.0;

    private LocalDate fechaLimite;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Prioridad prioridad = Prioridad.MEDIA;

    @Column(nullable = false)
    private Boolean activa = true;

    @Column(nullable = false)
    private Boolean completada = false;

    private LocalDate fechaCompletada;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private LocalDateTime fechaModificacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaModificacion = fechaCreacion;
    }

    @PreUpdate
    protected void onUpdate() {
        fechaModificacion = LocalDateTime.now();
    }

}
