# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Run (requires MySQL on port 3307 — start with Docker first)
docker-compose up -d
./mvnw spring-boot:run

# Build
./mvnw clean package

# Tests
./mvnw test
./mvnw test -Dtest=ClassName#methodName   # single test
```

## Architecture

Spring Boot 3 REST API (Java 17). No frontend — Thymeleaf dependency exists but all active endpoints are JSON under `/api/**`.

**Layer flow:** `Controller → Service interface → ServiceImpl → Repository (JPA) → MySQL`

**DTO mapping:** MapStruct with `componentModel = "spring"` — all mappers are Spring beans injected via `@Autowired`/constructor, never via `Mappers.getMapper()`. Mappers live in `mapper/` and follow the pattern: `toResponse`, `toList`, `toEntity`, `updateFromDto`.

**Security:** JWT via `jjwt`. `JwtAuthenticationFilter` validates tokens per-request. `RateLimitFilter` runs before it. Currently all `/api/**` endpoints are `permitAll()` in `SecurityConfig` — JWT validation still runs but authorization is open.

**Database migrations:** Flyway, migrations in `src/main/resources/db/migration/`. Schema: usuarios → clientes → categorias → gastos/ingresos/presupuestos/metas/gastos_recurrentes/cuentas_bancarias/reportes/notificaciones/configuraciones.

**Key packages:**
- `entity/` — JPA entities (Lombok `@Data`/`@Builder`)
- `dto/` — one sub-package per entity with `*Save`, `*Update`, `*Response`, `*List` records/classes
- `mapper/` — MapStruct interfaces/abstract classes
- `service/interfaces/` + `service/impl/` — service contracts and implementations
- `controller/api/` — REST controllers
- `security/` — JWT filter, rate limiter, `UsuarioDetails`, `SecurityConfig`
- `util/` — `JwtUtil`, `DateUtils`, `FileUtils`, `EncryptionUtils`, `ValidationUtils`
- `enums/` — `Rol`, `Frecuencia`, `MetodoPago`, `MetodoRecepcion`, `Prioridad`

**DB connection:** MySQL on `localhost:3307` (Docker maps 3307→3306) by default. Overridable via env vars `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD` (falls back to local Docker Compose values when unset). `JWT_SECRET` and `PORT` are also env-overridable — see deployment notes below. Docker Compose also sets up the schema automatically.

**Deployment:** No env vars set → runs against local Docker MySQL with the same defaults as before. In production (e.g. Railway), set `DB_HOST`/`DB_PORT`/`DB_NAME`/`DB_USERNAME`/`DB_PASSWORD` from the managed MySQL instance, a freshly generated `JWT_SECRET` (never reuse the local default — it's public in git history), and let the platform set `PORT`.
