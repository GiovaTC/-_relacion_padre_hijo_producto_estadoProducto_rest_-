# -_relacion_padre_hijo_producto_estadoProducto_rest_- :.
# Servicio REST en Java 21 con Spring Boot 3 y MySQL:

<img width="1254" height="1254" alt="image" src="https://github.com/user-attachments/assets/8b4af2dc-ae92-47c6-a298-15fe330ede9b" />    

<img width="2555" height="1079" alt="image" src="https://github.com/user-attachments/assets/62330541-23d5-4521-9eec-d3fb1fe8a523" />    

<img width="2559" height="1006" alt="image" src="https://github.com/user-attachments/assets/a27fe816-dd76-44ce-b298-7de2ef5014cb" />    

```

## Relación Padre-Hijo (Producto → EstadoProducto)

Proyecto desarrollado con **Java 21** utilizando tecnologías actuales y compatibles con **IntelliJ IDEA**,
donde un **Producto (tabla padre)** posee uno o varios **Estados de Producto (tabla hija)**.

Toda la información se almacena en **MySQL** mediante **un único método POST**, enviando la estructura completa en formato **JSON** desde **Postman**.

---

# Tecnologías

- Java 21
- IntelliJ IDEA
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL 8
- Maven
- Postman

---

# Estructura del Proyecto

```text
ProductoREST
│
├── pom.xml
├── script.sql
│
└── src
    └── main
        ├── java
        │
        └── com.ejemplo.producto
            │
            ├── ProductoRestApplication.java
            │
            ├── controller
            │      ProductoController.java
            │
            ├── entity
            │      Producto.java
            │      EstadoProducto.java
            │
            ├── repository
            │      ProductoRepository.java
            │
            ├── service
            │      ProductoService.java
            │
            └── dto
                   ProductoDTO.java
                   EstadoProductoDTO.java
```

---

# Base de Datos

## Tabla Padre

### Producto

| Campo | Tipo |
|--------|------|
| id | BIGINT |
| nombre | VARCHAR(100) |
| descripcion | VARCHAR(150) |
| precio | DECIMAL |
| categoria | VARCHAR(80) |

---

## Tabla Hija

### EstadoProducto

| Campo | Tipo |
|--------|------|
| id | BIGINT |
| estado | VARCHAR(40) |
| fechaRegistro | DATE |
| observacion | VARCHAR(150) |
| responsable | VARCHAR(80) |

Cada estado pertenece a un producto.

---

# Relación

```text
Producto
      |
      | 1
      |
      |---------
                N
      EstadoProducto
```

---

# Endpoint REST

## Método

```http
POST
```

## URL

```text
http://localhost:8080/api/productos
```

---

# JSON Recibido

```json
{
  "nombre": "Computador Gamer",
  "descripcion": "Core i7 32GB",
  "precio": 5800.50,
  "categoria": "Tecnología",
  "estados": [
    {
      "estado": "Disponible",
      "fechaRegistro": "2026-07-02",
      "observacion": "Ingreso a inventario",
      "responsable": "Juan"
    },
    {
      "estado": "En promoción",
      "fechaRegistro": "2026-07-03",
      "observacion": "Descuento del 10%",
      "responsable": "María"
    }
  ]
}
```

---

# Respuesta

```json
{
  "mensaje": "Producto registrado correctamente"
}
```

---

# Script MySQL

```sql
CREATE DATABASE productosdb;

USE productosdb;

CREATE TABLE producto(

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    nombre VARCHAR(100),

    descripcion VARCHAR(150),

    precio DECIMAL(10,2),

    categoria VARCHAR(80)

);

CREATE TABLE estado_producto(

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    estado VARCHAR(40),

    fecha_registro DATE,

    observacion VARCHAR(150),

    responsable VARCHAR(80),

    producto_id BIGINT,

    FOREIGN KEY(producto_id)
    REFERENCES producto(id)

);
```

---

# Entidad Producto

```java
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    private Double precio;

    private String categoria;

    @OneToMany(
            mappedBy = "producto",
            cascade = CascadeType.ALL
    )
    private List<EstadoProducto> estados = new ArrayList<>();

}
```

---

# Entidad EstadoProducto

```java
@Entity
@Table(name = "estado_producto")
public class EstadoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String estado;

    private LocalDate fechaRegistro;

    private String observacion;

    private String responsable;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

}
```

---

# Repository

```java
public interface ProductoRepository
        extends JpaRepository<Producto, Long> {

}
```

---

# Service

```java
@Service
public class ProductoService {

    @Autowired
    ProductoRepository repository;

    public Producto guardar(Producto producto) {

        producto.getEstados().forEach(e -> {
            e.setProducto(producto);
        });

        return repository.save(producto);

    }

}
```

---

# Controller

```java
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    ProductoService service;

    @PostMapping
    public ResponseEntity<String> guardar(
            @RequestBody Producto producto) {

        service.guardar(producto);

        return ResponseEntity.ok(
                "Producto registrado correctamente");

    }

}
```

---

# application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/productosdb

spring.datasource.username=root

spring.datasource.password=123456

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true
```

---

# Dependencias Maven

```xml
<dependencies>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
    </dependency>

</dependencies>
```

---

# Prueba en Postman

## Método

```text
POST
```

## URL

```text
http://localhost:8080/api/productos
```

## Headers

```text
Content-Type: application/json
```

## Body

Seleccionar:

```text
raw
```

Formato:

```text
JSON
```

Pegar:

```json
{
  "nombre": "Portátil Lenovo",
  "descripcion": "Ryzen 7 16GB",
  "precio": 4200,
  "categoria": "Computadores",
  "estados": [
    {
      "estado": "Disponible",
      "fechaRegistro": "2026-07-02",
      "observacion": "Nuevo",
      "responsable": "Pedro"
    },
    {
      "estado": "Reservado",
      "fechaRegistro": "2026-07-03",
      "observacion": "Cliente 105",
      "responsable": "Laura"
    }
  ]
}
```

---

# Resultado en MySQL

## Tabla: producto

| id | nombre | descripcion | precio | categoria |
|----|---------|-------------|--------:|-----------|
| 1 | Portátil Lenovo | Ryzen 7 16GB | 4200 | Computadores |

---

## Tabla: estado_producto

| id | estado | fecha_registro | observacion | responsable | producto_id |
|----|---------|----------------|-------------|-------------|------------:|
| 1 | Disponible | 2026-07-02 | Nuevo | Pedro | 1 |
| 2 | Reservado | 2026-07-03 | Cliente 105 | Laura | 1 |

---

# Características del Proyecto

- Arquitectura REST con Spring Boot 3.
- Java 21.
- Relación padre-hijo (**Producto → EstadoProducto**) mediante `@OneToMany` y `@ManyToOne`.
- Persistencia automática de la entidad padre y sus entidades hijas utilizando `CascadeType.ALL`.
- Almacenamiento de la información en MySQL.
- Prueba sencilla mediante una solicitud **POST** desde **Postman**.
- Registro del producto y todos sus estados asociados mediante un único documento JSON.
- Compatible con IntelliJ IDEA.
- Código organizado siguiendo una arquitectura por capas (**Controller, Service, Repository, Entity y DTO**) .
- :. . / .
