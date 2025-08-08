package com.codepuppeteer.sistema_gastos_clientes.model.entity;

import com.codepuppeteer.sistema_gastos_clientes.model.enums.Frecuencia;
import com.codepuppeteer.sistema_gastos_clientes.model.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "gastos_recurrentes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GastoRecurrente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    private Double monto;

    @Enumerated(EnumType.STRING)
    private Frecuencia frecuencia;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}