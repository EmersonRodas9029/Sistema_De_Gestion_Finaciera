package com.codepuppeteer.sistema_gastos_clientes.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "categorias",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_categoria_cliente", columnNames = {"cliente_id", "nombre"})
        },
        indexes = {
                @Index(name = "idx_categorias_nombre", columnList = "nombre")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_categoria_cliente"))
    private Cliente cliente;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 7, nullable = false)
    private String color = "#3498db";

    @Column(length = 50, nullable = false)
    private String icono = "category";

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal presupuestoMensual = BigDecimal.ZERO;

    @Column(nullable = false)
    private Boolean activa = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime fechaModificacion = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        fechaModificacion = LocalDateTime.now();
    }
}
