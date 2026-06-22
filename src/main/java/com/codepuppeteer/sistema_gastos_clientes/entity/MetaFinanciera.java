package com.codepuppeteer.sistema_gastos_clientes.entity;

import com.codepuppeteer.sistema_gastos_clientes.enums.Prioridad;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "metas_financieras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetaFinanciera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false, length = 255)
    private String nombre;

    private String descripcion;

    @Column(name = "monto_objetivo", nullable = false)
    private BigDecimal montoObjetivo;

    @Column(name = "monto_actual", nullable = false)
    @Builder.Default
    private BigDecimal montoActual = BigDecimal.ZERO;

    private LocalDate fechaLimite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Prioridad prioridad = Prioridad.MEDIA;

    @Builder.Default
    private Boolean activa = true;

    @Builder.Default
    private Boolean completada = false;

    private LocalDate fechaCompletada;

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime fechaModificacion = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        if (montoActual == null) montoActual = BigDecimal.ZERO;
        if (prioridad == null) prioridad = Prioridad.MEDIA;
        if (activa == null) activa = true;
        if (completada == null) completada = false;
        if (fechaCreacion == null) fechaCreacion = LocalDateTime.now();
        if (fechaModificacion == null) fechaModificacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaModificacion = LocalDateTime.now();
    }
}
