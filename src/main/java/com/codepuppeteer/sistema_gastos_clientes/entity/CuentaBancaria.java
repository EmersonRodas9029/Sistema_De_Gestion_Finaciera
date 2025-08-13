package com.codepuppeteer.sistema_gastos_clientes.entity;

import com.codepuppeteer.sistema_gastos_clientes.enums.TipoCuenta;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cuentas_bancarias", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cliente_id", "banco", "numero_cuenta"})
})
public class CuentaBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false, length = 255)
    private String banco;

    @Column(name = "numero_cuenta", nullable = false, length = 50)
    private String numeroCuenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta", nullable = false, length = 10)
    private TipoCuenta tipoCuenta;

    @Column(name = "saldo_actual")
    private Double saldoActual = 0.0;

    @Column(name = "fecha_actualizacion_saldo")
    private LocalDateTime fechaActualizacionSaldo;

    @Column(nullable = false)
    private Boolean activa = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private LocalDateTime fechaModificacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaModificacion = fechaCreacion;
        fechaActualizacionSaldo = fechaCreacion;
    }

    @PreUpdate
    protected void onUpdate() {
        fechaModificacion = LocalDateTime.now();
    }

}
