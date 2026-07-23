package com.codepuppeteer.sistema_gastos_clientes.entity;

import com.codepuppeteer.sistema_gastos_clientes.enums.Rol;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios",
        indexes = {
                @Index(name = "idx_usuarios_email", columnList = "email")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    // Contador que creó este usuario. Null solo para cuentas CONTADOR sembradas
    // directamente en BD (no hay registro propio); todo lo creado vía crearUsuario() lo trae.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contador_id", foreignKey = @ForeignKey(name = "fk_usuario_contador"))
    private Usuario contador;

    // Solo aplica cuando rol = CONTADOR: máximo de usuarios que puede crear. Ajustable por sudo.
    @Column(nullable = false)
    @Builder.Default
    private Integer limiteUsuarios = 5;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;

    private LocalDateTime ultimoAcceso;

    @Column(nullable = false)
    @Builder.Default
    private Integer intentosFallidos = 0;

    private LocalDateTime bloqueadoHasta;

    private String tokenRecuperacion;

    private LocalDateTime fechaExpiracionToken;

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime fechaModificacion = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        if (activo == null) activo = true;
        if (limiteUsuarios == null) limiteUsuarios = 5;
        if (intentosFallidos == null) intentosFallidos = 0;
        if (fechaCreacion == null) fechaCreacion = LocalDateTime.now();
        if (fechaModificacion == null) fechaModificacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaModificacion = LocalDateTime.now();
    }
}
