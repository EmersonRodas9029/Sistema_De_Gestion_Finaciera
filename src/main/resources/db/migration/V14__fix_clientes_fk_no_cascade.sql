ALTER TABLE clientes DROP FOREIGN KEY clientes_ibfk_1;
ALTER TABLE clientes ADD CONSTRAINT fk_cliente_usuario
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE RESTRICT;
