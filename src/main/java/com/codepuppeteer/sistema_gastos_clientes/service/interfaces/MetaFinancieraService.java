package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.entity.MetaFinanciera;

import java.util.List;
import java.util.Optional;

public interface MetaFinancieraService {

    MetaFinanciera crearMeta(MetaFinanciera meta, long clienteId);

    MetaFinanciera actualizarMeta(long id, MetaFinanciera meta);

    void eliminarMeta(long id);

    Optional<MetaFinanciera> obtenerMetaPorId(long id);

    List<MetaFinanciera> obtenerTodasLasMetas();

    List<MetaFinanciera> obtenerMetasPorCliente(long clienteId);
}
