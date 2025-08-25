CREATE INDEX idx_gastos_cliente_fecha ON gastos(cliente_id, fecha);
CREATE INDEX idx_gastos_categoria ON gastos(categoria_id);
CREATE INDEX idx_ingresos_cliente_fecha ON ingresos(cliente_id, fecha);
CREATE INDEX idx_notificaciones_cliente_fecha ON notificaciones(cliente_id, fecha_creacion);
CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_clientes_documento ON clientes(documento_identidad);
