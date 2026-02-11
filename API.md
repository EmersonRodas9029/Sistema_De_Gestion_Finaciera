# API - Sistema de Gestion Financiera

Base URL (default): `http://localhost:8080`

Tipos de datos (referencia rapida):
- `Long`: entero (id)
- `BigDecimal`: numero decimal
- `LocalDate`: fecha `YYYY-MM-DD`
- `LocalDateTime`: fecha y hora `YYYY-MM-DDTHH:MM:SS`
- `Boolean`: `true` o `false`
- `Enum`: usar un valor valido del enum indicado

## Usuarios (`/api/usuarios`)
- GET `/api/usuarios`
- GET `/api/usuarios/{id}`
  - Path: `id` (Long)
- POST `/api/usuarios`
  - Body: `username` (String, requerido), `password` (String, requerido), `email` (String), `rol` (Rol, requerido)
- PUT `/api/usuarios/{id}`
  - Path: `id` (Long)
  - Body: `username` (String, requerido), `password` (String), `email` (String), `rol` (Rol), `activo` (Boolean)
- DELETE `/api/usuarios/{id}`
  - Path: `id` (Long)

## Clientes (`/api/clientes`)
- GET `/api/clientes`
- GET `/api/clientes/{id}`
  - Path: `id` (Long)
- POST `/api/clientes`
  - Body: `usuarioId` (Long, requerido), `nombreCompleto` (String, requerido), `telefono` (String), `email` (String), `fechaNacimiento` (LocalDate), `direccion` (String), `documentoIdentidad` (String), `tipoDocumento` (TipoDocumento)
- PUT `/api/clientes/{id}`
  - Path: `id` (Long)
  - Body: `nombreCompleto` (String), `telefono` (String), `email` (String), `fechaNacimiento` (LocalDate), `direccion` (String), `documentoIdentidad` (String), `tipoDocumento` (TipoDocumento), `activo` (Boolean)
- DELETE `/api/clientes/{id}`
  - Path: `id` (Long)

## Categorias (`/api/categorias`)
- POST `/api/categorias`
  - Body: `clienteId` (Long, requerido), `nombre` (String, requerido), `descripcion` (String), `color` (String), `icono` (String), `presupuestoMensual` (BigDecimal)
- PUT `/api/categorias/{id}`
  - Path: `id` (Long)
  - Body: `nombre` (String), `descripcion` (String), `color` (String), `icono` (String), `presupuestoMensual` (BigDecimal), `activa` (Boolean)
- DELETE `/api/categorias/{id}`
  - Path: `id` (Long)
- GET `/api/categorias/{id}`
  - Path: `id` (Long)
- GET `/api/categorias/cliente/{clienteId}`
  - Path: `clienteId` (Long)

## Gastos (`/api/gastos`)
- GET `/api/gastos`
- GET `/api/gastos/{id}`
  - Path: `id` (Long)
- POST `/api/gastos`
  - Body: `clienteId` (Long), `categoriaId` (Long), `monto` (BigDecimal), `fecha` (LocalDate), `descripcion` (String), `metodoPago` (MetodoPago), `esRecurrente` (Boolean), `frecuencia` (Frecuencia), `adjunto` (String), `activo` (Boolean)
- PUT `/api/gastos/{id}`
  - Path: `id` (Long)
  - Body: `categoriaId` (Long), `monto` (BigDecimal), `fecha` (LocalDate), `descripcion` (String), `metodoPago` (MetodoPago), `esRecurrente` (Boolean), `frecuencia` (Frecuencia), `adjunto` (String), `activo` (Boolean)
- DELETE `/api/gastos/{id}`
  - Path: `id` (Long)

## Gastos recurrentes (`/api/gastos-recurrentes`)
- POST `/api/gastos-recurrentes`
  - Body: `clienteId` (Long), `categoriaId` (Long), `monto` (BigDecimal), `descripcion` (String), `frecuencia` (Frecuencia), `fechaInicio` (LocalDate), `fechaFin` (LocalDate), `diaMes` (Integer), `diaSemana` (Integer)
- PUT `/api/gastos-recurrentes/{id}`
  - Path: `id` (Long)
  - Body: `monto` (BigDecimal), `descripcion` (String), `frecuencia` (Frecuencia), `fechaFin` (LocalDate), `diaMes` (Integer), `diaSemana` (Integer), `activo` (Boolean)
- DELETE `/api/gastos-recurrentes/{id}`
  - Path: `id` (Long)
- GET `/api/gastos-recurrentes/{id}`
  - Path: `id` (Long)
- GET `/api/gastos-recurrentes/cliente/{clienteId}`
  - Path: `clienteId` (Long)

## Reportes (`/api/reportes`)
- GET `/api/reportes`
- GET `/api/reportes/{id}`
  - Path: `id` (Long)
- POST `/api/reportes`
  - Body: `clienteId` (Long), `contadorId` (Long), `nombre` (String), `tipoReporte` (TipoReporte), `periodoInicio` (LocalDate), `periodoFin` (LocalDate), `contenido` (String), `rutaArchivo` (String)
- PUT `/api/reportes/{id}`
  - Path: `id` (Long)
  - Body: `clienteId` (Long), `contadorId` (Long), `nombre` (String), `tipoReporte` (TipoReporte), `periodoInicio` (LocalDate), `periodoFin` (LocalDate), `contenido` (String), `rutaArchivo` (String)
- DELETE `/api/reportes/{id}`
  - Path: `id` (Long)

## Metas financieras (`/api/metas`)
- GET `/api/metas`
- GET `/api/metas/{id}`
  - Path: `id` (Long)
- POST `/api/metas`
  - Body: `clienteId` (Long), `nombre` (String), `descripcion` (String), `montoObjetivo` (BigDecimal), `montoActual` (BigDecimal), `fechaLimite` (LocalDate), `prioridad` (Prioridad), `activa` (Boolean), `completada` (Boolean), `fechaCompletada` (LocalDate)
- PUT `/api/metas/{id}`
  - Path: `id` (Long)
  - Body: `nombre` (String), `descripcion` (String), `montoObjetivo` (BigDecimal), `montoActual` (BigDecimal), `fechaLimite` (LocalDate), `prioridad` (Prioridad), `activa` (Boolean), `completada` (Boolean), `fechaCompletada` (LocalDate)
- DELETE `/api/metas/{id}`
  - Path: `id` (Long)

## Cuentas bancarias (`/api/cuentas`)
- GET `/api/cuentas`
- GET `/api/cuentas/{id}`
  - Path: `id` (Long)
- POST `/api/cuentas`
  - Body: `clienteId` (Long), `banco` (String), `numeroCuenta` (String), `tipoCuenta` (TipoCuenta), `saldoActual` (BigDecimal), `activa` (Boolean)
- PUT `/api/cuentas/{id}`
  - Path: `id` (Long)
  - Body: `clienteId` (Long), `banco` (String), `numeroCuenta` (String), `tipoCuenta` (TipoCuenta), `saldoActual` (BigDecimal), `activa` (Boolean)
- DELETE `/api/cuentas/{id}`
  - Path: `id` (Long)

## Configuraciones (`/api/configuraciones`)
- GET `/api/configuraciones`
- GET `/api/configuraciones/{id}`
  - Path: `id` (Long)
- POST `/api/configuraciones`
  - Body: `clienteId` (Long), `clave` (String), `valor` (String), `tipo` (TipoConfiguracion), `descripcion` (String)
- PUT `/api/configuraciones/{id}`
  - Path: `id` (Long)
  - Body: `clave` (String), `valor` (String), `tipo` (TipoConfiguracion), `descripcion` (String)
- DELETE `/api/configuraciones/{id}`
  - Path: `id` (Long)

## Ingresos (`/api/ingresos`)
- POST `/api/ingresos`
  - Body: `clienteId` (Long), `monto` (BigDecimal), `fecha` (LocalDate), `tipo` (TipoIngreso), `fuente` (String), `descripcion` (String), `metodoRecepcion` (MetodoRecepcion), `esRecurrente` (Boolean), `frecuencia` (Frecuencia)
- PUT `/api/ingresos/{id}`
  - Path: `id` (Long)
  - Body: `monto` (BigDecimal), `fecha` (LocalDate), `tipo` (TipoIngreso), `fuente` (String), `descripcion` (String), `metodoRecepcion` (MetodoRecepcion), `esRecurrente` (Boolean), `frecuencia` (Frecuencia), `activo` (Boolean)
- DELETE `/api/ingresos/{id}`
  - Path: `id` (Long)
- GET `/api/ingresos/{id}`
  - Path: `id` (Long)
- GET `/api/ingresos`

## Notificaciones (`/api/notificaciones`)
- GET `/api/notificaciones`
- GET `/api/notificaciones/{id}`
  - Path: `id` (Long)
- POST `/api/notificaciones`
  - Body: `clienteId` (Long), `tipo` (TipoNotificacion), `titulo` (String), `mensaje` (String), `leida` (Boolean), `fechaProgramada` (LocalDateTime), `fechaEnviada` (LocalDateTime), `activa` (Boolean)
- PUT `/api/notificaciones/{id}`
  - Path: `id` (Long)
  - Body: `tipo` (TipoNotificacion), `titulo` (String), `mensaje` (String), `leida` (Boolean), `fechaProgramada` (LocalDateTime), `fechaEnviada` (LocalDateTime), `activa` (Boolean)
- DELETE `/api/notificaciones/{id}`
  - Path: `id` (Long)

## Presupuestos (`/api/presupuestos`)
- GET `/api/presupuestos`
- GET `/api/presupuestos/{id}`
  - Path: `id` (Long)
- POST `/api/presupuestos`
  - Body: `clienteId` (Long), `categoriaId` (Long), `montoPresupuestado` (BigDecimal), `mes` (Integer), `anio` (Integer), `activo` (Boolean)
- PUT `/api/presupuestos/{id}`
  - Path: `id` (Long)
  - Body: `categoriaId` (Long), `montoPresupuestado` (BigDecimal), `mes` (Integer), `anio` (Integer), `activo` (Boolean)
- DELETE `/api/presupuestos/{id}`
  - Path: `id` (Long)

