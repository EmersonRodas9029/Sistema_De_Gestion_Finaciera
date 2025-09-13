package com.codepuppeteer.sistema_gastos_clientes.entity;

import com.codepuppeteer.sistema_gastos_clientes.enums.Frecuencia;
import com.codepuppeteer.sistema_gastos_clientes.enums.MetodoRecepcion;
import com.codepuppeteer.sistema_gastos_clientes.enums.TipoIngreso;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ingresos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoIngreso tipo;

    private String fuente;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_recepcion", nullable = false)
    private MetodoRecepcion metodoRecepcion = MetodoRecepcion.TRANSFERENCIA;

    @Column(name = "es_recurrente")
    private Boolean esRecurrente = false;

    @Enumerated(EnumType.STRING)
    private Frecuencia frecuencia;

    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaModificacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }
}
