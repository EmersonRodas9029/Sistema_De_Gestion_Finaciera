CREATE TABLE clientes (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          usuario_id BIGINT NOT NULL,
                          nombre_completo VARCHAR(255) NOT NULL,
                          telefono VARCHAR(20),
                          email VARCHAR(255),
                          fecha_nacimiento DATE,
                          direccion TEXT,
                          documento_identidad VARCHAR(50),
                          tipo_documento ENUM('DNI', 'CEDULA', 'PASAPORTE') DEFAULT 'DNI',
                          activo BOOLEAN DEFAULT TRUE,
                          fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);
