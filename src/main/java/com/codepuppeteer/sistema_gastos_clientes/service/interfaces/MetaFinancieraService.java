package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.entity.MetaFinanciera;

import java.util.List;
import java.util.Optional;

public interface MetaFinancieraService {

    MetaFinanciera crearMeta(MetaFinanciera meta, Long clienteId);

    MetaFinanciera actualizarMeta(Long id, MetaFinanciera meta);

    void eliminarMeta(Long id);

    Optional<MetaFinanciera> obtenerMetaPorId(Long id);

    List<MetaFinanciera> obtenerTodasLasMetas();

    List<MetaFinanciera> obtenerMetasPorCliente(Long clienteId);
}
