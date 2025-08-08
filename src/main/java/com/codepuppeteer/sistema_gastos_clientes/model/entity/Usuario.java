package com.codepuppeteer.sistema_gastos_clientes.model.entity;

import com.codepuppeteer.sistema_gastos_clientes.model.enums.RolUsuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
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
    private RolUsuario rol;

    private Boolean activo = true;
    private LocalDateTime ultimoAcceso;
    private Integer intentosFallidos = 0;
    private LocalDateTime bloqueadoHasta;
    private String tokenRecuperacion;
    private LocalDateTime fechaExpiracionToken;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}