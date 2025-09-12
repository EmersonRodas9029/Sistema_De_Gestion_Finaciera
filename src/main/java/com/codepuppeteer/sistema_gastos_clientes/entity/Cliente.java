package com.codepuppeteer.sistema_gastos_clientes.entity;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoDocumento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "clientes",
        indexes = {
                @Index(name = "idx_clientes_documento", columnList = "documentoIdentidad")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cliente_usuario"))
    private Usuario usuario;

    @Column(nullable = false, length = 255)
    private String nombreCompleto;

    @Column(length = 20)
    private String telefono;

    @Column(length = 255)
    private String email;

    private LocalDate fechaNacimiento;

    @Lob
    private String direccion;

    @Column(length = 50)
    private String documentoIdentidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoDocumento tipoDocumento = TipoDocumento.DNI;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime fechaModificacion = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        fechaModificacion = LocalDateTime.now();
    }
}
