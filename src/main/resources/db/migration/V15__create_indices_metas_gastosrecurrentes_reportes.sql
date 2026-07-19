CREATE INDEX idx_metas_cliente_fecha_limite ON metas_financieras(cliente_id, fecha_limite);
CREATE INDEX idx_gastos_recurrentes_cliente_activo ON gastos_recurrentes(cliente_id, activo);
CREATE INDEX idx_reportes_cliente_fecha ON reportes(cliente_id, fecha_generacion);
