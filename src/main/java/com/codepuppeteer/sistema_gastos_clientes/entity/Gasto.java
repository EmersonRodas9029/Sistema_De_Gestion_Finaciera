package com.codepuppeteer.sistema_gastos_clientes.entity;

import com.codepuppeteer.sistema_gastos_clientes.enums.MetodoPago;
import com.codepuppeteer.sistema_gastos_clientes.enums.Frecuencia;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "gastos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(nullable = false)
    private LocalDate fecha;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPago metodoPago = MetodoPago.EFECTIVO;

    @Column(nullable = false)
    private Boolean esRecurrente = false;

    @Enumerated(EnumType.STRING)
    private Frecuencia frecuencia;

    private String adjunto;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime fechaModificacion = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        fechaModificacion = LocalDateTime.now();
    }
}
