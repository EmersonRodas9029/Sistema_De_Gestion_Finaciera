package com.codepuppeteer.sistema_gastos_clientes.entity;

import com.codepuppeteer.sistema_gastos_clientes.enums.Tipo;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private Tipo tipo;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Lob
    @Column(nullable = false)
    private String mensaje;

    @Column(nullable = false)
    private Boolean leida = false;

    private LocalDateTime fechaProgramada;

    private LocalDateTime fechaEnviada;

    @Column(nullable = false)
    private Boolean activa = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

}


