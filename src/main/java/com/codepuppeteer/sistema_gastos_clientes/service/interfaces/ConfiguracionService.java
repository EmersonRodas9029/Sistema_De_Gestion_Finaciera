package com.codepuppeteer.sistema_gastos_clientes.service.interfaces;

import com.codepuppeteer.sistema_gastos_clientes.dto.configuracion.*;
import com.codepuppeteer.sistema_gastos_clientes.entity.Configuracion;

import java.util.List;
import java.util.Optional;

public interface ConfiguracionService {

    Configuracion crearConfiguracion(ConfiguracionSave dto);

    Configuracion actualizarConfiguracion(long id, ConfiguracionUpdate dto);

    void eliminarConfiguracion(long id);

    Optional<Configuracion> obtenerPorId(long id);

    List<Configuracion> obtenerTodas();
}
