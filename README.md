# 💰 Sistema de Gestión Financiera

Sistema backend para la gestión financiera **Contador–Cliente**: clientes, cuentas bancarias, gastos, ingresos, presupuestos, metas financieras, gastos recurrentes, reportes (con exportación a PDF), notificaciones y configuraciones — todo expuesto como una API REST segura.

[![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue)](LICENSE)

---

## ✨ Características

- 🔐 **Autenticación JWT** con rate limiting por request
- 🧾 **Gestión completa** de clientes, gastos, ingresos, presupuestos, metas y cuentas bancarias
- 🔁 Soporte para **gastos recurrentes** con frecuencia configurable
- 📊 **Reportes financieros** con generación de **PDF** (OpenPDF)
- 🔔 Sistema de **notificaciones** (presupuesto excedido, recordatorios, progreso de metas, etc.)
- 🗃️ Migraciones de base de datos versionadas con **Flyway**
- 🧩 Mapeo DTO ↔ Entidad con **MapStruct**

## 🛠️ Stack tecnológico

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 17 |
| Framework | Spring Boot 3 (Web, Data JPA, Security, Validation) |
| Base de datos | MySQL 8.0 |
| Migraciones | Flyway |
| Autenticación | JWT (jjwt) |
| Mapeo DTO | MapStruct |
| Reportes PDF | OpenPDF |
| Infraestructura | Docker / Docker Compose |
| Build | Maven |

## 🏗️ Arquitectura

```
Controller → Service (interface) → ServiceImpl → Repository (JPA) → MySQL
```

```
src/main/java/com/codepuppeteer/sistema_gastos_clientes/
├── controller/api/     # Controladores REST (/api/**)
├── service/
│   ├── interfaces/     # Contratos de servicio
│   └── impl/           # Implementaciones
├── repository/         # Repositorios JPA
├── entity/              # Entidades JPA
├── dto/                 # *Save, *Update, *Response, *List por entidad
├── mapper/               # Interfaces MapStruct
├── security/             # JWT filter, rate limiter, SecurityConfig
├── util/                 # JwtUtil, DateUtils, FileUtils, EncryptionUtils...
└── enums/                # Rol, Frecuencia, MetodoPago, Prioridad...
```

**Módulos disponibles:** Usuarios · Clientes · Categorías · Gastos · Ingresos · Presupuestos · Metas financieras · Gastos recurrentes · Cuentas bancarias · Reportes · Notificaciones · Configuraciones

## 🚀 Puesta en marcha

### Requisitos previos

- [Docker](https://www.docker.com/) y [Docker Compose](https://docs.docker.com/compose/)
- [Java 17+](https://adoptium.net/)
- Git

### Pasos

```bash
# 1. Levantar MySQL (puerto 3307)
docker-compose up -d

# 2. Ejecutar la aplicación
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

### Build y tests

```bash
./mvnw clean package
./mvnw test
./mvnw test -Dtest=ClassName#methodName   # un test puntual
```

## 📖 Documentación de la API

El detalle completo de endpoints, cuerpos de petición y tipos de datos está en [`API.md`](./API.md).

## 🔒 Seguridad

Autenticación vía JWT (`JwtAuthenticationFilter`) con `RateLimitFilter` corriendo antes del filtro de autenticación. Solo `/api/auth/**` es público (`permitAll()`); el resto de `/api/**` exige JWT válido (`anyRequest().authenticated()`). La autorización por dueño del recurso (IDOR) se aplica dentro de cada `ServiceImpl` vía `SecurityUtils.checkOwnership(...)`, con el rol `CONTADOR` viendo todos los recursos y `CLIENTE` limitado a los suyos.

## ⚙️ Variables de entorno en producción

Todas tienen un default apto solo para desarrollo local; en producción hay que sobreescribirlas:

| Variable | Uso | Default (dev) |
|---|---|---|
| `PORT` | Puerto HTTP del servidor | `8080` |
| `DB_HOST` | Host de MySQL | `localhost` |
| `DB_PORT` | Puerto de MySQL | `3307` |
| `DB_NAME` | Nombre de la base de datos | `sistema_gastos_clientes` |
| `DB_USERNAME` | Usuario de MySQL | `root` |
| `DB_PASSWORD` | Password de MySQL | *(hardcodeado en dev — cambiar siempre en prod)* |
| `JWT_SECRET` | Clave HMAC para firmar los JWT (Base64, mínimo 256 bits) | valor de ejemplo ya público en el historial del repo — **generar uno nuevo** |
| `JWT_EXPIRATION` | Expiración del JWT en ms | `86400000` (24h) |
| `CORS_ALLOWED_ORIGINS` | Orígenes permitidos por CORS, separados por coma | `http://localhost:5173,http://localhost:4173` |
| `RATELIMIT_MAX_REQUESTS` | Límite general de requests por IP/minuto | `200` |
| `RATELIMIT_WINDOW_MS` | Ventana del límite general en ms | `60000` |
| `RATELIMIT_AUTH_MAX_REQUESTS` | Límite estricto para `/api/auth/login` y cambio de contraseña | `10` |
| `RATELIMIT_AUTH_WINDOW_MS` | Ventana del límite estricto en ms | `60000` |

## 📄 Licencia

Distribuido bajo licencia [Apache 2.0](LICENSE).

---

Desarrollado por **Equipo Code Puppeteer** 🐙
