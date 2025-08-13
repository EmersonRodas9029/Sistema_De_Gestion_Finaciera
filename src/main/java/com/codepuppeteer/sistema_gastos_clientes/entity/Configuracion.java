package com.codepuppeteer.sistema_gastos_clientes.entity;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoD;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "configuraciones", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cliente_id", "clave"})
})
public class Configuracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false, length = 100)
    private String clave;

    @Lob
    @Column(nullable = false)
    private String valor;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private TipoD tipo = TipoD.STRING;

    @Lob
    private String descripcion;

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

