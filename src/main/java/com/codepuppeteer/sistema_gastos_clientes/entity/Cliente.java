package com.codepuppeteer.sistema_gastos_clientes.entity;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoDocumento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
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

    @Column(name = "nombre_completo", nullable = false, length = 255)
    private String nombreCompleto;

    @Column(length = 20)
    private String telefono;

    @Column(length = 255)
    private String email;

    private LocalDate fechaNacimiento;

    @Column(columnDefinition = "TEXT")
    private String direccion;

    @Column(name = "documento_identidad", length = 50)
    private String documentoIdentidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento = TipoDocumento.DNI;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaModificacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaModificacion = LocalDateTime.now();
    }
}