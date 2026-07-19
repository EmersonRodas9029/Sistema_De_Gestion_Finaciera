package com.codepuppeteer.sistema_gastos_clientes.scheduler;

import com.codepuppeteer.sistema_gastos_clientes.entity.GastoRecurrente;
import com.codepuppeteer.sistema_gastos_clientes.enums.Frecuencia;
import com.codepuppeteer.sistema_gastos_clientes.repository.GastoRecurrenteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.GastoRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class GastoRecurrenteSchedulerTest {

    private final GastoRecurrenteScheduler scheduler =
            new GastoRecurrenteScheduler(mock(GastoRecurrenteRepository.class), mock(GastoRepository.class));

    private GastoRecurrente gasto(Frecuencia frecuencia, LocalDate fechaInicio, Integer diaMes, Integer diaSemana) {
        return GastoRecurrente.builder()
                .monto(BigDecimal.TEN)
                .frecuencia(frecuencia)
                .fechaInicio(fechaInicio)
                .diaMes(diaMes)
                .diaSemana(diaSemana)
                .build();
    }

    @Test
    void diario_siempreVence() {
        var gr = gasto(Frecuencia.DIARIO, LocalDate.of(2026, 1, 1), null, null);
        assertTrue(scheduler.vence(gr, LocalDate.of(2026, 7, 19)));
    }

    @Test
    void semanal_venceSoloElDiaConfigurado() {
        // 2026-07-19 es domingo (ISO day-of-week = 7)
        var gr = gasto(Frecuencia.SEMANAL, LocalDate.of(2026, 1, 1), null, 7);
        assertTrue(scheduler.vence(gr, LocalDate.of(2026, 7, 19)));
        assertFalse(scheduler.vence(gr, LocalDate.of(2026, 7, 20)));
    }

    @Test
    void mensual_caeAlUltimoDiaSiElConfiguradoNoExisteEnElMes() {
        // día 31 configurado, pero febrero 2026 solo tiene 28 días
        var gr = gasto(Frecuencia.MENSUAL, LocalDate.of(2026, 1, 31), 31, null);
        assertTrue(scheduler.vence(gr, LocalDate.of(2026, 2, 28)));
        assertFalse(scheduler.vence(gr, LocalDate.of(2026, 2, 27)));
    }

    @Test
    void anual_venceEnElAniversarioDeFechaInicio() {
        var gr = gasto(Frecuencia.ANUAL, LocalDate.of(2025, 3, 15), null, null);
        assertTrue(scheduler.vence(gr, LocalDate.of(2026, 3, 15)));
        assertFalse(scheduler.vence(gr, LocalDate.of(2026, 3, 16)));
    }
}
