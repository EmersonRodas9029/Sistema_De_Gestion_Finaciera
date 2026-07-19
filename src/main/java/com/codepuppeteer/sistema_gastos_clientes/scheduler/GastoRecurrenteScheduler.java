package com.codepuppeteer.sistema_gastos_clientes.scheduler;

import com.codepuppeteer.sistema_gastos_clientes.entity.Gasto;
import com.codepuppeteer.sistema_gastos_clientes.entity.GastoRecurrente;
import com.codepuppeteer.sistema_gastos_clientes.repository.GastoRecurrenteRepository;
import com.codepuppeteer.sistema_gastos_clientes.repository.GastoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;

// Genera automáticamente el Gasto del día para cada GastoRecurrente activo que "vence" hoy.
// Corre una vez al día; si el proceso estuvo caído, los días saltados no se recuperan
// retroactivamente (ponytail: alcance suficiente para uso normal; un backfill por rango
// de fechas sería el siguiente paso si eso llega a importar).
@Component
@RequiredArgsConstructor
public class GastoRecurrenteScheduler {

    private static final Logger log = LoggerFactory.getLogger(GastoRecurrenteScheduler.class);

    private final GastoRecurrenteRepository gastoRecurrenteRepository;
    private final GastoRepository gastoRepository;

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void procesarGastosRecurrentes() {
        LocalDate hoy = LocalDate.now();
        int generados = 0;

        for (GastoRecurrente gr : gastoRecurrenteRepository.findByActivoTrue()) {
            if (gr.getFechaFin() != null && hoy.isAfter(gr.getFechaFin())) {
                gr.setActivo(false);
                gastoRecurrenteRepository.save(gr);
                continue;
            }
            if (hoy.isBefore(gr.getFechaInicio())) continue;
            if (hoy.equals(gr.getUltimoProcesamiento())) continue;
            if (!vence(gr, hoy)) continue;

            Gasto gasto = Gasto.builder()
                    .cliente(gr.getCliente())
                    .categoria(gr.getCategoria())
                    .monto(gr.getMonto())
                    .fecha(hoy)
                    .descripcion(gr.getDescripcion())
                    .esRecurrente(true)
                    .frecuencia(gr.getFrecuencia())
                    .build();
            gastoRepository.save(gasto);

            gr.setUltimoProcesamiento(hoy);
            gastoRecurrenteRepository.save(gr);
            generados++;
        }

        if (generados > 0) {
            log.info("Gastos recurrentes procesados: {} gasto(s) generado(s) para {}", generados, hoy);
        }
    }

    // package-private para poder probarla directamente sin mockear repositorios
    boolean vence(GastoRecurrente gr, LocalDate hoy) {
        return switch (gr.getFrecuencia()) {
            case DIARIO -> true;
            case SEMANAL -> gr.getDiaSemana() != null && gr.getDiaSemana().equals(hoy.getDayOfWeek().getValue());
            case MENSUAL -> gr.getDiaMes() != null && hoy.getDayOfMonth() == diaEfectivoDelMes(gr.getDiaMes(), hoy);
            case ANUAL -> hoy.getMonthValue() == gr.getFechaInicio().getMonthValue()
                    && hoy.getDayOfMonth() == diaEfectivoDelMes(gr.getFechaInicio().getDayOfMonth(), hoy);
        };
    }

    // Si el día configurado no existe en el mes actual (ej. 31 en febrero), cae al último día del mes.
    private int diaEfectivoDelMes(int diaConfigurado, LocalDate hoy) {
        int ultimoDia = YearMonth.from(hoy).lengthOfMonth();
        return Math.min(diaConfigurado, ultimoDia);
    }
}
