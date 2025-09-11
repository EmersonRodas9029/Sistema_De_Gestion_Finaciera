CREATE TABLE categorias (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            cliente_id BIGINT NOT NULL,
                            nombre VARCHAR(255) NOT NULL,
                            descripcion TEXT,
                            color VARCHAR(7) DEFAULT '#3498db',
                            icono VARCHAR(50) DEFAULT 'category',
                            presupuesto_mensual DECIMAL(10,2) DEFAULT 0,
                            activa BOOLEAN DEFAULT TRUE,
                            fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
                            UNIQUE KEY unique_categoria_cliente (cliente_id, nombre)
);
