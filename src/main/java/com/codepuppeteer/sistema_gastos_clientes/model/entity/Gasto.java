package com.codepuppeteer.sistema_gastos_clientes.model.entity;

import com.codepuppeteer.sistema_gastos_clientes.model.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "gastos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private LocalDateTime fecha;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    private String factura;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}