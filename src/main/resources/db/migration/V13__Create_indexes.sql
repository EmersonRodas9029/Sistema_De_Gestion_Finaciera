-- Gastos
CREATE INDEX idx_gastos_cliente_fecha ON gastos (cliente_id, fecha);
CREATE INDEX idx_gastos_categoria ON gastos (categoria_id);

-- Ingresos
CREATE INDEX idx_ingresos_cliente_fecha ON ingresos (cliente_id, fecha);

-- Presupuestos
CREATE INDEX idx_presupuestos_cliente_periodo ON presupuestos (cliente_id, mes, anio);

-- Metas financieras
CREATE INDEX idx_metas_cliente_prioridad ON metas_financieras (cliente_id, prioridad);

-- Notificaciones
CREATE INDEX idx_notificaciones_cliente_leido ON notificaciones (cliente_id, leido);

-- Reportes
CREATE INDEX idx_reportes_cliente_fecha ON reportes (cliente_id, fecha_generacion);
