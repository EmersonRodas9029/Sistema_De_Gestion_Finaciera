package com.codepuppeteer.sistema_gastos_clientes.model.entity;

import com.codepuppeteer.sistema_gastos_clientes.model.enums.PrioridadMeta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "metas_financieras")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MetaFinanciera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private String nombre;

    private Double montoObjetivo;
    private Double montoAhorrado;

    @Enumerated(EnumType.STRING)
    private PrioridadMeta prioridad;

    private LocalDateTime fechaLimite;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}