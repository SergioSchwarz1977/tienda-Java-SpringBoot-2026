# Tienda API

API REST para la gestión de **productos** y **categorías** de una tienda, desarrollada con Spring Boot, JPA/Hibernate y MySQL.

## Stack

- Java 25
- Spring Boot 4.1.1 (Web MVC, Data JPA, Validation)
- MySQL (driver `mysql-connector-j`)
- Maven (wrapper incluido: `mvnw.cmd`)

## Configuración (`src/main/resources/application.properties`)

| Propiedad | Valor |
|---|---|
| `server.port` | `8080` |
| `spring.datasource.url` | `jdbc:mysql://localhost:3306/ecommerce` |
| `spring.datasource.username` | `root` |
| `spring.datasource.password` | (vacío) |
| `spring.jpa.hibernate.ddl-auto` | `validate` |

**URL base:** `http://localhost:8080`

> Importante: con `ddl-auto=validate` Hibernate **no crea ni modifica tablas**, solo comprueba que existan y que sus columnas coincidan con las entidades. Si falta una tabla o una columna, la aplicación no arranca.

## Base de datos

Base de datos MySQL `ecommerce` con dos tablas:

### `categoria`

| Columna | Tipo | Descripción |
|---|---|---|
| `id` | INT, PK, autoincrement | Identificador |
| `categoria` | VARCHAR | Nombre de la categoría (ej. "Electrónica") |

### `productos`

| Columna | Tipo | Descripción |
|---|---|---|
| `id` | INT, PK, autoincrement | Identificador |
| `nombre` | VARCHAR | Nombre del producto |
| `descripcion` | VARCHAR | Descripción |
| `precio` | DOUBLE | Precio |
| `stock` | INT | Unidades disponibles |
| `idcategoria` | INT, FK → `categoria.id` | Categoría del producto |

Relación: **muchos productos pertenecen a una categoría** (`@ManyToOne` en `Producto`, `@OneToMany` en `Categoria`).

> Nota: la API solo expone endpoints de productos. No existe un controlador de categorías, por lo que las categorías deben insertarse directamente en la base de datos (por ejemplo con SQL o desde MySQL Workbench).

```sql
INSERT INTO categoria (categoria) VALUES ('Electrónica');
```

## Endpoints

Todos cuelgan de la ruta base `/productos`.

| Método | Ruta | Descripción | Respuesta correcta |
|---|---|---|---|
| GET | `/productos/listar` | Lista todos los productos | `200 OK` |
| GET | `/productos/listarConCategoria` | Lista productos con el **nombre** de la categoría como texto | `200 OK` |
| GET | `/productos/{id}` | Devuelve un producto por su id | `200 OK` / `404 Not Found` |
| POST | `/productos/crear` | Crea un producto | `201 Created` |
| PUT | `/productos/actualizar/{id}` | Actualiza un producto existente | `200 OK` / `404 Not Found` |
| DELETE | `/productos/eliminar/{id}` | Elimina un producto | `204 No Content` |

> `listar` y `listarConCategoria` están declarados con `@RequestMapping` sin especificar el método HTTP, por lo que técnicamente aceptan cualquier verbo. Se deben consumir con **GET**.

---

### GET `/productos/listar`

Devuelve todos los productos. La **categoría se serializa como un objeto anidado** (es la entidad `Categoria`).

**Respuesta `200`:**

```json
[
  {
    "id": 1,
    "nombre": "Laptop",
    "descripcion": "Laptop gaming",
    "precio": 1500.0,
    "stock": 10,
    "categoria": {
      "id": 1,
      "categoria": "Electrónica"
    }
  }
]
```

---

### GET `/productos/listarConCategoria`

Devuelve los productos con el nombre de la categoría **como String (texto plano), no como objeto**.

Esto es así porque la consulta del repositorio proyecta directamente el resultado en el DTO `DTOProductoCategoria` (`SELECT new com.ecommerce.tienda.dto.DTOProductoCategoria(..., c.categoria)`), donde el campo `categoria` es un `String`.

**Respuesta `200`:**

```json
[
  {
    "id": 1,
    "nombre": "Laptop",
    "descripcion": "Laptop gaming",
    "precio": 1500.0,
    "stock": 10,
    "categoria": "Electrónica"
  }
]
```

> ⚠️ Ese `"categoria": "Electrónica"` es **solo de lectura**. Si reenvías ese JSON a `POST /productos/crear` obtendrás un `400 Bad Request`, porque en la entidad `Producto` la categoría es un objeto `Categoria`, no un texto. Para crear o actualizar hay que enviar `"categoria": { "id": 1 }`.

---

### GET `/productos/{id}`

**Respuesta `200`:** el producto (misma estructura que en `/listar`, con `categoria` como objeto).

**Respuesta `404`:** si no existe el producto, sin cuerpo.

---

### POST `/productos/crear`

**Request body:**

```json
{
  "nombre": "Laptop",
  "descripcion": "Laptop gaming",
  "precio": 1500.0,
  "stock": 10,
  "categoria": { "id": 1 }
}
```

Reglas del JSON:

- `categoria` debe ser un **objeto** con el `id` de una categoría que **ya exista** en la tabla `categoria`.
- **No** envíes `categoria` como texto (`"categoria": "Electrónica"`) → provoca `400 Bad Request`.
- **No** envíes el campo `id` del producto: lo genera la base de datos.
- Si el `id` de la categoría no existe → error de clave foránea (`500`).

**Respuesta `201 Created`:** el producto creado, con el `id` ya asignado.

```json
{
  "id": 7,
  "nombre": "Laptop",
  "descripcion": "Laptop gaming",
  "precio": 1500.0,
  "stock": 10,
  "categoria": {
    "id": 1,
    "categoria": "Electrónica"
  }
}
```

---

### PUT `/productos/actualizar/{id}`

Actualiza `nombre`, `descripcion`, `categoria`, `precio` y `stock`. El **body es igual al de crear**.

**Request body:**

```json
{
  "nombre": "Laptop Pro",
  "descripcion": "Laptop gaming 16GB",
  "precio": 1800.0,
  "stock": 5,
  "categoria": { "id": 2 }
}
```

**Respuesta `200`:** el producto actualizado. **Respuesta `404`:** si el `id` no existe.

---

### DELETE `/productos/eliminar/{id}`

**Respuesta `204 No Content`** (sin cuerpo).

---

## Ejemplos con curl

```bash
# Listar todos los productos
curl http://localhost:8080/productos/listar

# Listar con el nombre de la categoría como texto
curl http://localhost:8080/productos/listarConCategoria

# Obtener un producto por id
curl http://localhost:8080/productos/1

# Crear un producto
curl -X POST http://localhost:8080/productos/crear -H "Content-Type: application/json" -d "{\"nombre\":\"Laptop\",\"descripcion\":\"Laptop gaming\",\"precio\":1500.0,\"stock\":10,\"categoria\":{\"id\":1}}"

# Actualizar un producto
curl -X PUT http://localhost:8080/productos/actualizar/1 -H "Content-Type: application/json" -d "{\"nombre\":\"Laptop Pro\",\"descripcion\":\"Laptop gaming 16GB\",\"precio\":1800.0,\"stock\":5,\"categoria\":{\"id\":2}}"

# Eliminar un producto
curl -X DELETE http://localhost:8080/productos/eliminar/1
```

---

## Errores comunes

| Síntoma | Causa | Solución |
|---|---|---|
| `400 Bad Request` al crear | `categoria` enviada como texto (formato de `/listarConCategoria`) | Enviar `"categoria": { "id": 1 }` |
| `500` al crear | El `id` de categoría no existe, o se envió `categoria` sin `id` | Verificar que la categoría exista en la tabla `categoria` |
| `500` al crear | Se envió el campo `id` del producto copiado de un listado | Quitar `id` del body |
| La aplicación no arranca | `ddl-auto=validate` y falta alguna tabla/columna, o MySQL no está corriendo | Crear las tablas `productos` y `categoria` con las columnas indicadas |

## Estructura del proyecto

```
src/main/java/com/ecommerce/tienda/
├── TiendaApplication.java
├── controllers/ProductoControllers.java
├── service/ProductoService.java, CategoriaService.java
├── repository/ProductoRepository.java, CategoriaRepository.java
├── entity/Producto.java, Categoria.java
├── dto/DTOProductoCategoria.java
└── exception/GlobalExceptionHandle.java, ProductoNoEncontradoException.java
```
