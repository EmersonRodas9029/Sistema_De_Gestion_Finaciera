package com.codepuppeteer.sistema_gastos_clientes.model.entity;

import com.codepuppeteer.sistema_gastos_clientes.model.enums.TipoDocumento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "clientes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String nombreCompleto;

    private String telefono;
    private String email;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String documentoIdentidad;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    private Boolean activo = true;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Categoria> categorias;
}