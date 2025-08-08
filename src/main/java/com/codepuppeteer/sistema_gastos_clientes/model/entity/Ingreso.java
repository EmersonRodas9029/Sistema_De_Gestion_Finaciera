package com.codepuppeteer.sistema_gastos_clientes.model.entity;

import com.codepuppeteer.sistema_gastos_clientes.model.enums.TipoIngreso;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ingresos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ingreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private LocalDateTime fecha;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    private TipoIngreso tipo;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}