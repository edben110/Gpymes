# Guía de pruebas manuales: GPymes backend

Guía para verificar a mano, en Postman, todo lo que expone el backend (`layers/`).
Está escrita a partir del código actual de `controller`, `dto`, `domain`, `service` y `exception`.

- Backend: Spring Boot 4.1.1, Java 25, Maven Wrapper.
- Base de datos: PostgreSQL 18 **nativo** de este equipo (servicio `postgresql-x64-18`, puerto 5432). No se usa Docker.
- Colección lista para importar: [`GPymes.postman_collection.json`](GPymes.postman_collection.json).

---

## 1. Prender el backend en Windows

### 1.1 Comprobar que PostgreSQL está corriendo

PowerShell:

```powershell
Get-Service postgresql-x64-18
```

`Status` debe ser `Running`. Si está `Stopped`, abre PowerShell **como administrador** y ejecuta:

```powershell
Start-Service postgresql-x64-18
```

(También se puede iniciar desde `services.msc`.)

### 1.2 Comprobar que existe la base `gpymes` y crearla si falta

`psql` no está en el PATH; se usa con ruta completa. Pedirá la clave del usuario `postgres` de forma interactiva (no queda escrita en ningún archivo).

**PowerShell** (recomendado para `psql`):

```powershell
$psql = "C:\Program Files\PostgreSQL\18\bin\psql.exe"

# Devuelve 1 si la base existe; no devuelve nada si no existe
& $psql -U postgres -h localhost -tAc "SELECT 1 FROM pg_database WHERE datname = 'gpymes';"

# Solo si la anterior no devolvió nada:
& $psql -U postgres -h localhost -c "CREATE DATABASE gpymes;"
```

**Git Bash** (el prompt de clave de `psql` necesita `winpty` en Git Bash):

```bash
PSQL="/c/Program Files/PostgreSQL/18/bin/psql.exe"

winpty "$PSQL" -U postgres -h localhost -tAc "SELECT 1 FROM pg_database WHERE datname = 'gpymes';"

# Solo si la anterior no devolvió nada:
winpty "$PSQL" -U postgres -h localhost -c "CREATE DATABASE gpymes;"
```

Alternativa gráfica: pgAdmin 4 → *Servers* → *PostgreSQL 18* → *Databases* → clic derecho → *Create* → *Database…* → nombre `gpymes`.

No hace falta crear tablas: con `spring.jpa.hibernate.ddl-auto=update` Hibernate crea `pymes`, `empleados` y `gastos` al arrancar.

### 1.3 Variables de conexión

`layers/src/main/resources/application.properties` lee estas variables de entorno (entre paréntesis, el valor por defecto):

| Variable | Defecto |
|---|---|
| `DB_HOST` | `localhost` |
| `DB_PORT` | `5432` |
| `DB_NAME` | `gpymes` |
| `DB_USER` | `postgres` |
| `DB_PASSWORD` | `postgres` |

Si tu clave de `postgres` **no** es `postgres`, defínela **solo en la terminal actual**, sin escribirla en ningún archivo. Las formas siguientes la piden sin mostrarla en pantalla ni dejarla en el historial:

PowerShell:

```powershell
$clave = Read-Host "Clave de postgres" -AsSecureString
$env:DB_PASSWORD = [System.Net.NetworkCredential]::new("", $clave).Password
Remove-Variable clave
```

Git Bash:

```bash
read -s -p "Clave de postgres: " DB_PASSWORD; echo; export DB_PASSWORD
```

La variable desaparece al cerrar la terminal. No la pongas en `application.properties`, `.env`, perfiles de shell ni variables de sistema.

### 1.4 Usar el JDK 25 solo en esta terminal y arrancar

El JDK 25 está en `C:\Users\sebastian\AppData\Local\Programs\Eclipse Adoptium\jdk-25.0.4.101-hotspot`.
El `JAVA_HOME` global sigue apuntando al 17; estos comandos solo afectan a la terminal donde se ejecutan.

**PowerShell:**

```powershell
$env:JAVA_HOME = "C:\Users\sebastian\AppData\Local\Programs\Eclipse Adoptium\jdk-25.0.4.101-hotspot"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
java -version          # debe decir 25.0.4

cd C:\Users\sebastian\Documents\POO\Gpymes\layers
.\mvnw.cmd -v          # la línea "Java version" debe decir 25
.\mvnw.cmd spring-boot:run
```

**Git Bash:**

```bash
export JAVA_HOME="/c/Users/sebastian/AppData/Local/Programs/Eclipse Adoptium/jdk-25.0.4.101-hotspot"
export PATH="$JAVA_HOME/bin:$PATH"
java -version          # debe decir 25.0.4

cd /c/Users/sebastian/Documents/POO/Gpymes/layers
./mvnw -v              # "Java version: 25..."
./mvnw spring-boot:run
```

La primera vez el wrapper descarga Maven y las dependencias (requiere internet).

### 1.5 Cómo saber que arrancó bien

Busca en el log, en este orden:

| Línea | Qué confirma |
|---|---|
| `HikariPool-1 - Start completed.` | Conectó a PostgreSQL |
| `Hibernate: create table pymes (...)`, `create table empleados`, `create table gastos` | Creó las tablas (solo la primera vez; luego no aparecen porque ya existen) |
| `Tomcat started on port 8080 (http)` | El servidor HTTP escucha |
| **`Started LayersApplication in X seconds`** | **Arranque completo: ya se puede probar** |

Comprobación rápida en otra terminal:

```powershell
curl.exe http://localhost:8080/api/pymes
```

Debe devolver `[]` (o la lista de pymes existentes).

Comprobar las tablas (opcional):

```powershell
& "C:\Program Files\PostgreSQL\18\bin\psql.exe" -U postgres -h localhost -d gpymes -c "\dt"
```

### 1.6 Cómo detenerlo

- En la terminal donde corre: **Ctrl + C** (si pregunta *¿Desea terminar el trabajo por lotes (S/N)?*, responde `S`).
- Si cerraste la terminal y sigue ocupando el puerto:

  ```powershell
  Get-NetTCPConnection -LocalPort 8080 -State Listen | Select-Object OwningProcess
  Stop-Process -Id <PID> -Confirm:$false
  ```

Detener el backend **no** detiene PostgreSQL (es un servicio de Windows y debe quedarse corriendo).

---

## 2. Configurar Postman

### Opción A: importar la colección (recomendado)

1. Postman → **Import** → arrastra `GPymes.postman_collection.json` (en la raíz del repo).
2. Abre la colección **GPymes** → pestaña **Variables**. Ya trae:

   | Variable | Valor inicial | Se llena sola |
   |---|---|---|
   | `baseUrl` | `http://localhost:8080` | no |
   | `pymeId` | (vacío) | sí, en *Crear pyme* |
   | `empleadoId` | (vacío) | sí, en *Crear empleado* |
   | `gastoId` | (vacío) | sí, en *Crear gasto* |
   | `nominaId` | (vacío) | sí, en *Crear nómina* |
   | `idInexistente` | `00000000-0000-0000-0000-000000000000` | no |

3. Ejecuta las peticiones **en orden** (carpeta 1 → 5), una por una, o con **Run collection**. Cada petición trae tests (pestaña *Test Results*) que comprueban el código HTTP y los campos clave.

### Opción B: configurarla a mano

1. **New → Collection** → nombre `GPymes`.
2. Pestaña **Variables** de la colección: crea `baseUrl = http://localhost:8080` y las variables vacías `pymeId`, `empleadoId`, `gastoId`, `nominaId`, más `idInexistente = 00000000-0000-0000-0000-000000000000`. Guarda (Ctrl+S).
3. En cada petición con body: pestaña **Headers** → `Content-Type: application/json`; pestaña **Body** → *raw* → *JSON*.
4. URLs con variables: `{{baseUrl}}/api/pymes/{{pymeId}}`.
5. Para guardar el id de la respuesta, en la pestaña **Scripts → Post-response** (en versiones antiguas, **Tests**) de la petición de creación:

   ```javascript
   pm.test("201 Created", () => pm.response.to.have.status(201));
   pm.collectionVariables.set("pymeId", pm.response.json().id);
   ```

   Igual en *Crear empleado* (`empleadoId`), *Crear gasto* (`gastoId`) y *Crear nómina* (`nominaId`).

> Si una URL sale con `{{pymeId}}` literal o vacío, la variable no se llenó: vuelve a ejecutar la petición de creación correspondiente.

---

## 3. Peticiones en orden

Convenciones:

- `<uuid>` = un UUID generado por el backend; las fechas son ISO-8601 (`2026-10-06T10:15:30.123`).
- Todos los números decimales del backend son `Double`, así que en las respuestas salen como `5000.0`, `2700.0`, etc.
- El campo `estado` del empleado devuelve el **nombre de la clase del estado**: `EstadoLaborando`, `EstadoPermiso`, `EstadoIncapacitado` o `EstadoDespedido`.

Formato de error del `GlobalExceptionHandler` (404, 400 de validación/dominio, 409):

```json
{
  "status": 404,
  "error": "Not Found",
  "mensaje": "Pyme no encontrada con ID: 00000000-0000-0000-0000-000000000000",
  "fecha": "2026-10-06T10:15:30.123"
}
```

Datos de prueba que se usan: pyme **Panaderia** con ganancias 5000, gasto ARRIENDO de **1200** y nómina de **1500**, así que **total = 2700.0**.

### 3.1 Crear datos

#### 1. Crear pyme

`POST {{baseUrl}}/api/pymes`

```json
{
  "nombre": "Panaderia",
  "ganancias": 5000
}
```

Esperado: **201 Created**

```json
{
  "id": "<uuid>",
  "nombre": "Panaderia",
  "ganancias": 5000.0,
  "totalGastos": 0.0,
  "empleados": []
}
```

Guardar `id` → `pymeId`.

#### 2. Crear empleado

`POST {{baseUrl}}/api/empleados`

```json
{
  "nombre": "Ana Torres",
  "documento": 1012345678,
  "salarioBase": 1500,
  "pymeId": "{{pymeId}}"
}
```

Esperado: **201 Created**

```json
{
  "id": "<uuid>",
  "nombre": "Ana Torres",
  "documento": 1012345678,
  "salarioBase": 1500.0,
  "horasExtra": 0,
  "estado": "EstadoLaborando"
}
```

Guardar `id` → `empleadoId`. Todo empleado nuevo empieza en `EstadoLaborando`.

#### 3. Crear gasto

Categorías válidas (`CategoriasGasto`): `ARRIENDO`, `SERVICIOSPUBLICOS`, `INTERNET`, `MERCANCIA`, `MANTENIMIENTO`, `TRANSPORTE`, `OTROS`. (`NOMINA` existe, pero está **prohibida** aquí.)

`POST {{baseUrl}}/api/gastos`

```json
{
  "pymeId": "{{pymeId}}",
  "montoTotal": 1200,
  "categoria": "ARRIENDO",
  "fechaPago": "2026-10-01T09:00:00"
}
```

Esperado: **201 Created**

```json
{
  "id": "<uuid>",
  "pymeId": "<pymeId>",
  "nombrePyme": "Panaderia",
  "montoTotal": 1200.0,
  "fechaPago": "2026-10-01T09:00:00",
  "categoria": "ARRIENDO",
  "empleadoId": null
}
```

Guardar `id` → `gastoId`. `fechaPago` es opcional: si se omite, se usa la fecha y hora actuales.

#### 4. Crear nómina

`POST {{baseUrl}}/api/nominas`

```json
{
  "empleadoId": "{{empleadoId}}",
  "montoTotal": 1500,
  "deducciones": 120,
  "fechaPago": "2026-10-05T18:00:00"
}
```

Esperado: **201 Created**

```json
{
  "id": "<uuid>",
  "empleadoId": "<empleadoId>",
  "nombreEmpleado": "Ana Torres",
  "montoTotal": 1500.0,
  "deducciones": 120.0,
  "fechaPago": "2026-10-05T18:00:00",
  "categoria": "NOMINA"
}
```

Guardar `id` → `nominaId`. La nómina toma la pyme **del empleado** y la categoría `NOMINA` automáticamente. `deducciones` y `fechaPago` son opcionales (`deducciones` por defecto vale `0.0`). Las deducciones **no** restan del total de gastos: al total suma `montoTotal`.

### 3.2 Pyme y total de gastos

#### 5. Obtener pyme por id

`GET {{baseUrl}}/api/pymes/{{pymeId}}`

Esperado: **200 OK**

```json
{
  "id": "<pymeId>",
  "nombre": "Panaderia",
  "ganancias": 5000.0,
  "totalGastos": 2700.0,
  "empleados": [
    { "id": "<empleadoId>", "nombre": "Ana Torres", "documento": 1012345678,
      "salarioBase": 1500.0, "horasExtra": 0, "estado": "EstadoLaborando" }
  ]
}
```

Comprobar: `totalGastos = 1200 + 1500 = 2700.0` y `ganancias = 5000.0`.

#### 6. Total de gastos de la pyme

`GET {{baseUrl}}/api/pymes/{{pymeId}}/gastos/total`

Esperado: **200 OK**

```json
{
  "pymeId": "<pymeId>",
  "nombre": "Panaderia",
  "totalGastos": 2700.0,
  "resumen": "El total de gastos es 2700.0 y es menor a las ganancias"
}
```

Regla del `resumen` (`Pyme.getTotalGastos()`): si `total < ganancias` dice *"y es menor a las ganancias"*; si no, *"y es mayor o igual a las ganancias, ojo"*. Si la pyme no tiene ganancias, se toman como 0.

### 3.3 Listados y búsquedas

| # | Método y URL | Esperado | Comprobar |
|---|---|---|---|
| 7 | `GET {{baseUrl}}/api/pymes` | 200 | Array; contiene `pymeId` con su lista `empleados` y `totalGastos` (sin errores de carga perezosa) |
| 8 | `GET {{baseUrl}}/api/pymes/buscar?nombre=Panaderia` | 200 | Objeto con `id = pymeId`. Búsqueda **exacta** y sensible a mayúsculas |
| 9 | `GET {{baseUrl}}/api/empleados` | 200 | Array; contiene `empleadoId` |
| 10 | `GET {{baseUrl}}/api/empleados/{{empleadoId}}` | 200 | `nombre = "Ana Torres"` |
| 11 | `GET {{baseUrl}}/api/empleados/pyme/{{pymeId}}` | 200 | Array con 1 empleado |
| 12 | `GET {{baseUrl}}/api/gastos` | 200 | Array con **el gasto y la nómina** (la tabla `gastos` es única; la nómina sale con `categoria: "NOMINA"` y `empleadoId` lleno) |
| 13 | `GET {{baseUrl}}/api/gastos/{{gastoId}}` | 200 | `categoria = "ARRIENDO"`, `empleadoId = null` |
| 14 | `GET {{baseUrl}}/api/gastos/{{nominaId}}` | 200 | La nómina vista como gasto: `categoria = "NOMINA"`, `empleadoId = empleadoId` |
| 15 | `GET {{baseUrl}}/api/gastos/pyme/{{pymeId}}` | 200 | 2 elementos; la suma de `montoTotal` es 2700.0 |
| 16 | `GET {{baseUrl}}/api/gastos/categoria/ARRIENDO` | 200 | Todos con `categoria = "ARRIENDO"` |
| 17 | `GET {{baseUrl}}/api/gastos/categoria/NOMINA` | 200 | Contiene `nominaId` (listar NOMINA sí está permitido; crear no) |
| 18 | `GET {{baseUrl}}/api/nominas` | 200 | Array; contiene `nominaId` con `nombreEmpleado` |
| 19 | `GET {{baseUrl}}/api/nominas/{{nominaId}}` | 200 | `nombreEmpleado = "Ana Torres"`, `deducciones = 120.0` |
| 20 | `GET {{baseUrl}}/api/nominas/empleado/{{empleadoId}}` | 200 | Array con 1 nómina |

> Los listados filtrados (`/empleados/pyme/{id}`, `/gastos/pyme/{id}`, `/nominas/empleado/{id}`) con un id inexistente devuelven **200 con `[]`**, no 404.

### 3.4 Cambios de estado del empleado

Todos son `PUT` **sin body**. Transiciones del patrón State: desde cualquier estado se puede ir a cualquier **otro** (incluso desde Despedido); repetir el estado actual da 400.

| # | Petición | Esperado | `estado` / `mensaje` |
|---|---|---|---|
| 21 | `PUT {{baseUrl}}/api/empleados/{{empleadoId}}/reactivar` (ya está laborando) | **400** | `"el estado ya esta en laborando"` |
| 22 | `PUT {{baseUrl}}/api/empleados/{{empleadoId}}/permiso` | **200** | `EstadoPermiso` |
| 23 | `PUT .../permiso` otra vez | **400** | `"el estado ya esta en Permiso"` |
| 24 | `PUT {{baseUrl}}/api/empleados/{{empleadoId}}/incapacitar` | **200** | `EstadoIncapacitado` |
| 25 | `PUT .../incapacitar` otra vez | **400** | `"el estado ya esta en Incapacitado"` |
| 26 | `PUT {{baseUrl}}/api/empleados/{{empleadoId}}/despedir` | **200** | `EstadoDespedido` |
| 27 | `PUT .../despedir` otra vez | **400** | `"el estado ya esta en Despedido"` |
| 28 | `PUT {{baseUrl}}/api/empleados/{{empleadoId}}/reactivar` | **200** | `EstadoLaborando` |
| 29 | `GET {{baseUrl}}/api/empleados/{{empleadoId}}` | 200 | `EstadoLaborando` (el estado quedó guardado) |

Ejemplo de 400 por estado repetido:

```json
{ "status": 400, "error": "Bad Request", "mensaje": "el estado ya esta en Despedido", "fecha": "..." }
```

### 3.5 Actualizar pyme (`PUT /api/pymes/{id}`)

`nombre` es **obligatorio** en el PUT (`@NotBlank`); `ganancias` es opcional y solo se cambia si viene en el body.

#### 30. Con ganancias nuevas

`PUT {{baseUrl}}/api/pymes/{{pymeId}}`

```json
{
  "nombre": "Panaderia",
  "ganancias": 2000
}
```

Esperado: **200 OK**, `ganancias: 2000.0`, `totalGastos: 2700.0`.

#### 31. Total tras bajar las ganancias

`GET {{baseUrl}}/api/pymes/{{pymeId}}/gastos/total`

Esperado: **200**, `resumen: "El total de gastos es 2700.0 y es mayor o igual a las ganancias, ojo"`.

#### 32. Sin ganancias

`PUT {{baseUrl}}/api/pymes/{{pymeId}}`

```json
{
  "nombre": "Panaderia Central"
}
```

Esperado: **200 OK**, `nombre: "Panaderia Central"` y **`ganancias: 2000.0` (no se borra)**.

#### 33. Confirmar

`GET {{baseUrl}}/api/pymes/{{pymeId}}`: `nombre = "Panaderia Central"`, `ganancias = 2000.0`, `totalGastos = 2700.0`.

### 3.6 Casos de error

| # | Petición | Body | Esperado | `mensaje` aproximado |
|---|---|---|---|---|
| 34 | `POST /api/pymes` (nombre repetido) | `{"nombre":"Panaderia Central","ganancias":100}` | **409** | `El registro viola una restriccion de la base de datos (dato duplicado o relacionado)` |
| 35 | `POST /api/empleados` (documento repetido) | `{"nombre":"Otro","documento":1012345678,"salarioBase":1000,"pymeId":"{{pymeId}}"}` | **409** | igual que arriba |
| 36 | `GET /api/pymes/{{idInexistente}}` | (ninguno) | **404** | `Pyme no encontrada con ID: 0000...` |
| 37 | `GET /api/empleados/{{idInexistente}}` | (ninguno) | **404** | `Empleado no encontrado con ID: ...` |
| 38 | `GET /api/gastos/{{idInexistente}}` | (ninguno) | **404** | `Gasto no encontrado con ID: ...` |
| 39 | `GET /api/nominas/{{idInexistente}}` | (ninguno) | **404** | `Nomina no encontrada con ID: ...` |
| 40 | `GET /api/pymes/buscar?nombre=NoExiste` | (ninguno) | **404** | `Pyme no encontrada con nombre: NoExiste` |
| 41 | `PUT /api/pymes/{{idInexistente}}` | `{"nombre":"X"}` | **404** | `Pyme no encontrada con ID: ...` |
| 42 | `POST /api/gastos` (monto negativo) | `{"pymeId":"{{pymeId}}","montoTotal":-50,"categoria":"OTROS"}` | **400** | `montoTotal: El monto total debe ser positivo` |
| 43 | `POST /api/gastos` (monto cero) | `{"pymeId":"{{pymeId}}","montoTotal":0,"categoria":"OTROS"}` | **400** | `montoTotal: El monto total debe ser positivo` |
| 44 | `POST /api/gastos` (categoría NOMINA) | `{"pymeId":"{{pymeId}}","montoTotal":100,"categoria":"NOMINA"}` | **400** | `La categoria NOMINA solo se genera desde ServicioNomina, que ademas exige el empleado` |
| 45 | `POST /api/gastos` (pyme inexistente) | `{"pymeId":"{{idInexistente}}","montoTotal":100,"categoria":"OTROS"}` | **404** | `Pyme no encontrada con ID: ...` |
| 46 | `POST /api/pymes` (sin nombre) | `{"ganancias":100}` | **400** | `nombre: El nombre es obligatorio` |
| 47 | `POST /api/pymes` (ganancias negativas) | `{"nombre":"Tienda X","ganancias":-1}` | **400** | `ganancias: Las ganancias no pueden ser negativas` |
| 48 | `POST /api/empleados` (salario negativo) | `{"nombre":"Luis","documento":2222,"salarioBase":-10,"pymeId":"{{pymeId}}"}` | **400** | `salarioBase: El salario debe ser positivo` |
| 49 | `POST /api/empleados` (pyme inexistente) | `{"nombre":"Luis","documento":3333,"salarioBase":1000,"pymeId":"{{idInexistente}}"}` | **404** | `Pyme no encontrada con ID: ...` |
| 50 | `POST /api/nominas` (empleado inexistente) | `{"empleadoId":"{{idInexistente}}","montoTotal":100}` | **404** | `Empleado no encontrado con ID: ...` |
| 51 | `POST /api/gastos` (categoría que no existe) | `{"pymeId":"{{pymeId}}","montoTotal":100,"categoria":"COMIDA"}` | **400** | Formato **por defecto de Spring** (ver nota) |
| 52 | `GET /api/pymes/no-es-un-uuid` | (ninguno) | **400** | Formato **por defecto de Spring** (ver nota) |

Todas las URLs van precedidas de `{{baseUrl}}`, y todos los `POST`/`PUT` con body llevan `Content-Type: application/json`.

> **Nota sobre 51 y 52:** un JSON que no se puede leer (enum inválido, tipo incorrecto, JSON mal formado) o un id que no es UUID **no** pasan por el `GlobalExceptionHandler`; Spring responde 400 con su cuerpo por defecto (`timestamp`, `status`, `error`, `path`), no con `mensaje`/`fecha`.

### 3.7 Tabla resumen

| # | Petición | Código | Qué comprobar |
|---|---|---|---|
| 1 | POST /api/pymes | 201 | `ganancias 5000.0`, `totalGastos 0.0`; guardar `pymeId` |
| 2 | POST /api/empleados | 201 | `estado EstadoLaborando`; guardar `empleadoId` |
| 3 | POST /api/gastos (ARRIENDO) | 201 | `empleadoId null`; guardar `gastoId` |
| 4 | POST /api/nominas | 201 | `categoria NOMINA`; guardar `nominaId` |
| 5 | GET /api/pymes/{id} | 200 | `totalGastos 2700.0`, `ganancias 5000.0`, 1 empleado |
| 6 | GET /api/pymes/{id}/gastos/total | 200 | `resumen` "...menor a las ganancias" |
| 7 | GET /api/pymes | 200 | Lista con empleados, sin error |
| 8 | GET /api/pymes/buscar?nombre=Panaderia | 200 | `id = pymeId` |
| 9–11 | GET empleados (todos, por id, por pyme) | 200 | Contienen `empleadoId` |
| 12–17 | GET gastos (todos, por id, nómina por id, por pyme, por categoría) | 200 | Gasto y nómina en la misma tabla; suma 2700.0 |
| 18–20 | GET nóminas (todas, por id, por empleado) | 200 | `nombreEmpleado "Ana Torres"` |
| 21 | PUT reactivar (ya laborando) | 400 | "ya esta en laborando" |
| 22–23 | PUT permiso ×2 | 200 / 400 | `EstadoPermiso` / "ya esta en Permiso" |
| 24–25 | PUT incapacitar ×2 | 200 / 400 | `EstadoIncapacitado` / "ya esta en Incapacitado" |
| 26–27 | PUT despedir ×2 | 200 / 400 | `EstadoDespedido` / "ya esta en Despedido" |
| 28–29 | PUT reactivar, GET empleado | 200 | `EstadoLaborando` persistido |
| 30 | PUT pyme con ganancias 2000 | 200 | `ganancias 2000.0` |
| 31 | GET total | 200 | `resumen` "...mayor o igual a las ganancias, ojo" |
| 32–33 | PUT pyme sin ganancias, GET | 200 | Nombre cambia, `ganancias` sigue en 2000.0 |
| 34–35 | Nombre de pyme / documento repetido | 409 | Mensaje de restricción |
| 36–41 | Ids o nombre inexistentes | 404 | `mensaje` "... no encontrado(a) ..." |
| 42–43 | Gasto con monto negativo o cero | 400 | `montoTotal: ...positivo` |
| 44 | Gasto con categoría NOMINA | 400 | "La categoria NOMINA solo se genera desde ServicioNomina..." |
| 45, 49, 50 | Crear con pyme/empleado inexistente | 404 | `mensaje` de no encontrado |
| 46–48 | Pyme sin nombre / ganancias negativas / salario negativo | 400 | `campo: mensaje` de validación |
| 51–52 | Categoría inválida / id no UUID | 400 | Cuerpo por defecto de Spring |

---

## 4. Problemas comunes

| Síntoma (log o respuesta) | Causa | Solución |
|---|---|---|
| `Connection to localhost:5432 refused` / `HikariPool-1 - Exception during pool initialization` | PostgreSQL detenido | `Get-Service postgresql-x64-18`; si está `Stopped`: `Start-Service postgresql-x64-18` (PowerShell como administrador) |
| `FATAL: password authentication failed for user "postgres"` | La clave no es `postgres` | Define `DB_PASSWORD` en la terminal (sección 1.3) y vuelve a arrancar |
| `FATAL: database "gpymes" does not exist` | Falta la base | `CREATE DATABASE gpymes;` (sección 1.2) |
| `Web server failed to start. Port 8080 was already in use.` | Otro proceso usa el 8080 (a menudo, otra instancia del backend) | Busca el proceso: `Get-NetTCPConnection -LocalPort 8080 -State Listen` y detenlo con `Stop-Process -Id <PID>`. O arranca en otro puerto solo en esa terminal: `$env:SERVER_PORT = "8081"` (Git Bash: `export SERVER_PORT=8081`) y cambia `baseUrl` a `http://localhost:8081` |
| `release version 25 not supported` / `invalid target release: 25` / `UnsupportedClassVersionError` | La terminal usa el JDK 17 | Repite la sección 1.4 en **esa** terminal; `java -version` y `mvnw -v` deben decir 25 |
| `'psql' no se reconoce...` | `psql` no está en el PATH | Usa la ruta completa `C:\Program Files\PostgreSQL\18\bin\psql.exe` |
| `psql` en Git Bash no pide la clave / se queda colgado | Consola de Git Bash sin TTY de Windows | Antepón `winpty` o usa PowerShell |
| `415 Unsupported Media Type` | Falta `Content-Type: application/json` | Añade el header o elige *raw → JSON* en el body |
| 400 con `path` y sin `mensaje` en una URL con `{{pymeId}}` | La variable está vacía y la URL no es un UUID válido | Ejecuta antes la petición de creación que guarda ese id |
| Al repetir la colección, *Crear pyme* o *Crear empleado* dan **409** | `Panaderia` (o `Panaderia Central`) y el documento `1012345678` ya existen | Vacía los datos (con el backend detenido o no): `& "C:\Program Files\PostgreSQL\18\bin\psql.exe" -U postgres -h localhost -d gpymes -c "TRUNCATE gastos, empleados, pymes;"`, o cambia nombre y documento en el body |
| `LazyInitializationException` en el log | Acceso a una relación LAZY fuera de transacción | No debería ocurrir: los servicios mapean dentro de `@Transactional(readOnly = true)`. Si aparece, anota la petición y el stacktrace |

---

## 5. Observaciones del código relevantes para las pruebas

- El `estado` del empleado se devuelve como nombre de clase (`EstadoLaborando`, etc.), no como `Laborando`.
- Un monto de **0** en gastos o nóminas da 400 (`@Positive` en el DTO), aunque el constructor de `Gasto` aceptaría 0.
- `deducciones` en nómina no tiene validación: acepta valores negativos o mayores que el monto.
- Se puede crear una nómina para un empleado en `EstadoDespedido`: el código no lo impide.
- Los listados filtrados por un id que no existe devuelven `[]` con 200, no 404.
- Enum inválido, JSON mal formado o un id que no es UUID devuelven 400 con el cuerpo por defecto de Spring, no con `ErrorResponse`.
