package com.codepuppeteer.sistema_gastos_clientes.entity;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoNotificacion;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotificacion tipo;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(nullable = false)
    private Boolean leida = false;

    private LocalDateTime fechaProgramada;

    private LocalDateTime fechaEnviada;

    @Column(nullable = false)
    private Boolean activa = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        fechaCreacion = LocalDateTime.now();
        if (leida == null) leida = false;
        if (activa == null) activa = true;
    }
}
