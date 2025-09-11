CREATE TABLE usuarios (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          username VARCHAR(255) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          email VARCHAR(255) UNIQUE,
                          rol ENUM('CLIENTE', 'CONTADOR') NOT NULL,
                          activo BOOLEAN DEFAULT TRUE,
                          ultimo_acceso TIMESTAMP NULL,
                          intentos_fallidos INT DEFAULT 0,
                          bloqueado_hasta TIMESTAMP NULL,
                          token_recuperacion VARCHAR(255) NULL,
                          fecha_expiracion_token TIMESTAMP NULL,
                          fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          CONSTRAINT chk_intentos_fallidos CHECK (intentos_fallidos >= 0),
                          CONSTRAINT chk_intentos_max CHECK (intentos_fallidos <= 5)
);
