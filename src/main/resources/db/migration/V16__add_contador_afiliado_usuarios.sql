ALTER TABLE usuarios
    ADD COLUMN contador_id BIGINT NULL,
    ADD CONSTRAINT fk_usuario_contador FOREIGN KEY (contador_id) REFERENCES usuarios(id),
    ADD INDEX idx_usuarios_contador_id (contador_id);
