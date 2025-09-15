package com.codepuppeteer.sistema_gastos_clientes.entity;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoCuenta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cuentas_bancarias", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cliente_id", "banco", "numero_cuenta"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private String banco;

    @Column(name = "numero_cuenta", nullable = false)
    private String numeroCuenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta", nullable = false)
    private TipoCuenta tipoCuenta;

    @Column(name = "saldo_actual", nullable = false)
    private BigDecimal saldoActual;

    @Column(name = "fecha_actualizacion_saldo", nullable = false)
    private LocalDateTime fechaActualizacionSaldo;

    @Column(nullable = false)
    private Boolean activa;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion", nullable = false)
    private LocalDateTime fechaModificacion;

    @PrePersist
    public void prePersist() {
        if (saldoActual == null) saldoActual = BigDecimal.ZERO;
        if (activa == null) activa = true;
        fechaCreacion = LocalDateTime.now();
        fechaModificacion = LocalDateTime.now();
        fechaActualizacionSaldo = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaModificacion = LocalDateTime.now();
        fechaActualizacionSaldo = LocalDateTime.now();
    }
}
