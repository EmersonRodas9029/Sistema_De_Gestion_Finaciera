package com.codepuppeteer.sistema_gastos_clientes.model.entity;

import com.codepuppeteer.sistema_gastos_clientes.model.enums.TipoCuenta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cuentas_bancarias")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CuentaBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;

    private String banco;
    private String numeroCuenta;
    private Double saldo;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}