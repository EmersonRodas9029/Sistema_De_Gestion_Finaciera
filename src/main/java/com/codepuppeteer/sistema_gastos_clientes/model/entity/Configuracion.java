package com.codepuppeteer.sistema_gastos_clientes.model.entity;

import com.codepuppeteer.sistema_gastos_clientes.model.enums.TipoConfiguracion;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "configuraciones")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Configuracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoConfiguracion tipo;

    private String clave;
    private String valor;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}