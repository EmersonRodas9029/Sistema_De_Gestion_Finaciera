# 💰 Sistema de Gestión Financiera (Backend)

API REST para la gestión financiera **Contador–Cliente**: clientes, cuentas bancarias, gastos, ingresos, presupuestos, metas financieras, gastos recurrentes, reportes (con exportación a PDF), notificaciones y configuraciones. Sirve a [BudgEase](https://github.com/EmersonRodas9029/sistema_de_gestion_financiera_frontend), el frontend en React del mismo sistema.

[![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)](https://www.mysql.com/)
[![Tests](https://img.shields.io/badge/tests-passing-success?logo=apachemaven)](#-tests)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue)](#-licencia)

---

## ✨ Características

- 🔐 **Autenticación JWT** con rate limiting general y uno más estricto para login/cambio de contraseña
- 🛡️ **Autorización por dueño del recurso (IDOR)** en cada servicio: un `CLIENTE` solo ve/edita lo suyo, `CONTADOR` ve todo
- 🧾 **Gestión completa** de usuarios, clientes, gastos, ingresos, presupuestos, metas y cuentas bancarias
- 🔁 **Gastos recurrentes** con frecuencia configurable y un **scheduler diario** que genera automáticamente el gasto del día
- 📊 **Reportes financieros** con generación de **PDF** (OpenPDF)
- 🔔 Sistema de **notificaciones** (presupuesto excedido, recordatorios, progreso de metas, etc.)
- 📚 **Documentación interactiva** con Swagger UI / OpenAPI
- 🗃️ Migraciones de base de datos versionadas con **Flyway**
- 🧩 Mapeo DTO ↔ Entidad con **MapStruct**
- ✅ **Tests** con Spring Security real (no mocks) para las reglas de autorización

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
| Documentación API | springdoc-openapi (Swagger UI) |
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
├── scheduler/            # Jobs programados (ej. generación diaria de gastos recurrentes)
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
# 1. Configurar variables de entorno (no se commitea)
cp .env.example .env
# completar DB_PASSWORD y generar un JWT_SECRET:
openssl rand -base64 48

# 2. Levantar MySQL (puerto 3307)
docker-compose up -d

# 3. Ejecutar la aplicación (carga .env en el entorno del shell)
set -a && source .env && set +a
./mvnw spring-boot:run
```

> No hay valores por defecto para `DB_PASSWORD` ni `JWT_SECRET` — la app falla al iniciar si no están seteados. Es intencional: antes tenían un default hardcodeado que terminó público en el historial del repo.

La API queda disponible en `http://localhost:8080`.

### Build

```bash
./mvnw clean package
```

## ✅ Tests

```bash
./mvnw test
./mvnw test -Dtest=ClassName#methodName   # un test puntual
```

Los tests de servicios (`ClienteServiceImplTest`, `UsuarioServiceImplTest`) corren sobre un `SecurityContextHolder` real de Spring Security en vez de mockear la autorización, para que un bypass de `checkOwnership` falle en el test y no solo en producción.

## 📖 Documentación de la API

- **Interactiva:** Swagger UI en `http://localhost:8080/swagger-ui.html` (JSON crudo en `/v3/api-docs`) una vez la app está corriendo.
- **Referencia completa:** endpoints, cuerpos de petición y tipos de datos en [`API.md`](./API.md).

## 🔒 Seguridad

- **Autenticación:** JWT (`JwtAuthenticationFilter`) con `RateLimitFilter` corriendo antes del filtro de autenticación. Solo `/api/auth/**` y la documentación Swagger son públicos (`permitAll()`); el resto de `/api/**` exige JWT válido (`anyRequest().authenticated()`).
- **Autorización (IDOR):** cada `ServiceImpl` valida dueño del recurso vía `SecurityUtils.checkOwnership(...)` antes de leer, editar o borrar — incluyendo `UsuarioServiceImpl` y `ClienteServiceImpl`. El rol `CONTADOR` ve todos los recursos; `CLIENTE` solo los suyos, y no puede cambiar su propio rol ni reactivar/desactivar su cuenta.
- **Validación:** todos los DTOs de entrada (`*Save`, `*Update`) usan Bean Validation (`@Valid`, `@NotBlank`, `@Email`, etc.) en el controller.
- **Rate limiting:** límite general por IP y uno más estricto y separado para `/api/auth/login` y cambio de contraseña (mitiga fuerza bruta / credential stuffing).

## ⚙️ Variables de entorno

Se configuran en un `.env` local (ver [`.env.example`](./.env.example), no se commitea) o directamente en el entorno de producción:

| Variable | Uso | Default |
|---|---|---|
| `PORT` | Puerto HTTP del servidor | `8080` |
| `DB_HOST` | Host de MySQL | `localhost` |
| `DB_PORT` | Puerto de MySQL | `3307` |
| `DB_NAME` | Nombre de la base de datos | `sistema_gastos_clientes` |
| `DB_USERNAME` | Usuario de MySQL | `root` |
| `DB_PASSWORD` | Password de MySQL | **obligatoria, sin default** |
| `JWT_SECRET` | Clave HMAC para firmar los JWT (Base64, mínimo 256 bits) — generar con `openssl rand -base64 48` | **obligatoria, sin default** |
| `JWT_EXPIRATION` | Expiración del JWT en ms | `86400000` (24h) |
| `CORS_ALLOWED_ORIGINS` | Orígenes permitidos por CORS, separados por coma | `http://localhost:5173,http://localhost:4173` |
| `RATELIMIT_MAX_REQUESTS` | Límite general de requests por IP/minuto | `200` |
| `RATELIMIT_WINDOW_MS` | Ventana del límite general en ms | `60000` |
| `RATELIMIT_AUTH_MAX_REQUESTS` | Límite estricto para `/api/auth/login` y cambio de contraseña | `10` |
| `RATELIMIT_AUTH_WINDOW_MS` | Ventana del límite estricto en ms | `60000` |

## 📄 Licencia

Distribuido bajo licencia Apache 2.0 (pendiente de agregar el archivo `LICENSE` al repositorio).

---

Desarrollado por **Equipo Code Puppeteer** 🐙 · Frontend: [BudgEase](https://github.com/EmersonRodas9029/sistema_de_gestion_financiera_frontend)
