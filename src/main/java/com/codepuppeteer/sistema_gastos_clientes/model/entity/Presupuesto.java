package com.codepuppeteer.sistema_gastos_clientes.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "presupuestos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Presupuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private Double montoTotal;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}