package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.dto.configuracion.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Configuracion;

import java.util.List;
import java.util.Optional;

public interface ConfiguracionService {

    Configuracion crearConfiguracion(ConfiguracionSave dto);

    Configuracion actualizarConfiguracion(Long id, ConfiguracionUpdate dto);

    void eliminarConfiguracion(Long id);

    Optional<Configuracion> obtenerPorId(Long id);

    List<Configuracion> obtenerTodas();
}
