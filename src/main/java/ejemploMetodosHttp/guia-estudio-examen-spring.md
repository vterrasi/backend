# 📚 Guía de Estudio Completa: Conexión de Monstruomon con Spring Boot y MySQL
## (Explicado para que lo entienda cualquiera de forma ultra sencilla)

¡Hola, Velia! Esta es tu guía definitiva de cara al examen de fin de curso. Olvídate de los tecnicismos raros por un momento; aquí vamos a explicar cómo se conecta tu proyecto de **Monstruomon** a una base de datos **MySQL** usando **Spring Boot** utilizando metáforas sencillas de la vida real (como un restaurante).

---

## 🗺️ 1. El Gran Esquema: La Metáfora del Restaurante

Cuando entras a una página web o pides datos desde el navegador, tu programa trabaja en **Capas** independientes. Imagina que tu proyecto es un restaurante de alta cocina:
```
[ 📱 El Cliente / Navegador ]  (Introduce la URL en su pantalla)
│
▼
┌────────────────────────────────────────────────────────┐
│ 1. Capa CONTROLLER (El Camarero)                       │
│    - Atiende al cliente en la mesa (recibe la URL).    │
│    - Traduce lo que pide el cliente a la cocina.       │
└─────────────────────────┬──────────────────────────────┘
│
▼
┌────────────────────────────────────────────────────────┐
│ 2. Capa SERVICE (El Chef de Cocina)                    │
│    - Conoce las reglas y recetas (Daño, Árbitro...).   │
│    - Procesa la comida antes de servirla.              │
└─────────────────────────┬──────────────────────────────┘
│
▼
┌────────────────────────────────────────────────────────┐
│ 3. Capa REPOSITORY (El Pinche de Cocina / Almacenero)  │
│    - Va a la despensa a buscar los ingredientes puros. │
│    - Sabe usar las herramientas automáticas de carga.  │
└─────────────────────────┬──────────────────────────────┘
│
▼
┌────────────────────────────────────────────────────────┐
│ 4. BASE DE DATOS - MySQL (La Despensa Permanente)      │
│    - El lugar físico donde están guardados los datos   │
│      en cajas llamadas "Tablas" (Monstruos, Ataques).  │
└────────────────────────────────────────────────────────┘

```

---

## 🔑 2. Los Conceptos Clave del Examen

Antes de ver el código, tienes que saber definir tres palabras mágicas que le encantan a los profesores:

1. **Persistencia:** Es la capacidad de los datos de "sobrevivir". Si apagas el ordenador y los monstruos siguen guardados en el disco duro (en MySQL) al encenderlo, tus datos son *persistentes*.
2. **ORM (Mapeo Objeto-Relational):** Es un "traductor". MySQL entiende de filas y tablas cuadradas. Java entiende de Objetos con atributos y listas. El ORM (que en nuestro caso es una herramienta llamada *Hibernate*) se encarga de convertir de forma automática una fila de la base de datos en un objeto Java vivo.
3. **Inyección de Dependencias:** En lugar de crear tú los objetos a mano escribiendo la palabra `new MiClase()`, le dejas ese trabajo aburrido a Spring. Spring fabrica el objeto por detrás y te lo "inyecta" (te lo pone en la mano) justo cuando lo necesitas.

---

## 🛠️ 3. Los Archivos Clave y sus Anotaciones (Paso a Paso)

Vamos a analizar el código exacto de tu proyecto y qué significa cada etiqueta con arroba (`@`).

### 📦 PASO 1: Preparar la Ficha del Dato (La Capa `model`)
El primer archivo clave es **`Monstruomon.java`**. Es una clase abstracta porque ningún bicho es "un monstruo genérico", sino que pertenecen a un tipo (Fuego, Agua...).

```java
@Entity
@Table(name = "monstruomones")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class Monstruomon implements Atacable {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", insertable = false, updatable = false)
    private TipoMonstruo tipo;

    @Column(name = "vida_maxima", nullable = false)
    private int vidaMaxima;

    @Transient
    private int vidaActual; // ¡Esta no se guarda!

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "monstruomones_ataques",
        joinColumns = @JoinColumn(name = "monstruomon_id"),
        inverseJoinColumns = @JoinColumn(name = "ataque_id")
    )
    private List<Ataque> ataques;
}

```

#### 💡 ¿Qué significan estas anotaciones para el examen?

* **`@Entity`:** Le avisa a Spring: *"Oye, esta clase no es una clase cualquiera; representa una fila de la base de datos"*.
* **`@Table(name = "monstruomones")`:** Le dice a Hibernate el nombre exacto de la tabla en MySQL.
* **`@Inheritance(strategy = InheritanceType.SINGLE_TABLE)`:** Como tenemos hijos (`MonstruomonFuego`, `MonstruomonAgua`...), esta estrategia le dice a MySQL: *"Mete al padre y a todos los hijos en una única tabla gigante llamada monstruomones"*. Es muy eficiente.
* **`@DiscriminatorColumn(name = "tipo")`:** Es el nombre de la columna donde MySQL escribe con texto de qué tipo es (si pone 'FUEGO', Spring creará un objeto de la clase `MonstruomonFuego` automáticamente).
* **`@Id`:** Indica cuál es la Clave Primaria (Primary Key), es decir, el número de identificación único de cada monstruo.
* **`@Column(name = "nombre")`:** Conecta la variable de Java con el nombre de la columna real en la tabla de MySQL. Si pones `nullable = false`, es el equivalente a decirle a la base de datos `NOT NULL` (campo obligatorio).
* **`@Enumerated(EnumType.STRING)`:** Por defecto, Java guarda los Enums como números (0, 1, 2). Con esto, le obligamos a guardar la palabra como texto plano ("AGUA", "FUEGO"), lo cual hace que leer la base de datos sea facilísimo.
* **`@Transient`:** Significa *"Pasajero"*. Le dice a Hibernate: *"No busques ninguna columna en MySQL para la variable `vidaActual`. Esta variable solo sirve para cuando estemos jugando la pelea en la memoria RAM, no se guarda al apagar el servidor"*.
* **`@ManyToMany`:** Define una relación de *Muchos a Muchos*. Un monstruo puede conocer muchos ataques, y un mismo ataque puede ser aprendido por muchos monstruos diferentes.
* **`@JoinTable`:** Configura la **Tabla Intermedia** (o tabla puente) llamada `monstruomones_ataques`. Guarda los IDs emparejados.
* **`fetch = FetchType.EAGER`:** Significa *"Carga Ansiosa"*. En cuanto Spring saca un monstruo de la base de datos, trae inmediatamente todos sus ataques de golpe en lugar de esperar a que se los pidas.

---

### 🗄️ PASO 2: El Acceso Automatizado (La Capa `repository`)

El segundo archivo clave es **`MonstruomonRepository.java`**. Es una interfaz (un contrato) y está prácticamente vacía:

```java
@Repository
public interface MonstruomonRepository extends JpaRepository<Monstruomon, Integer> {
    // ¡Aquí no hay código escrito a mano!
}

```

#### 💡 ¿Qué significa esto para el examen?

* **`@Repository`:** Le dice a Spring: *"Regístrame en tu contenedor de componentes como el encargado oficial de hablar con la base de datos"*.
* **`extends JpaRepository<Monstruomon, Integer>`:** Al heredar de JpaRepository pasándole la clase (`Monstruomon`) y el tipo de su ID (`Integer`), **Spring hace magia**. Te regala ya programados métodos esenciales como `.findAll()` (traer todo), `.findById()` (buscar por ID), `.save()` (guardar/actualizar) o `.deleteById()`. **No tienes que escribir ni una sola línea de código SQL nativo.**

---

### 🌐 PASO 3: El Camarero Web (La Capa `controller`)

El tercer archivo clave es **`MonstruomonController.java`**. Es el que transforma tu programa en una página web.

```java
@RestController
@RequestMapping("/api")
public class MonstruomonController {

    @Autowired
    private MonstruomonRepository monstruomonRepository;

    @GetMapping("/monstruomones")
    public List<Monstruomon> obtenerTodosLosMonstruomones() {
        return monstruomonRepository.findAll();
    }
}

```

#### 💡 ¿Qué significan estas anotaciones para el examen?

* **`@RestController`:** Le dice a Spring: *"Esta clase va a escuchar las llamadas que vengan desde internet. Además, todo lo que devuelvan sus métodos conviértelo automáticamente a texto estructurado **JSON**"*.
* **`@RequestMapping("/api")`:** Es el prefijo de la dirección web. Todas las rutas de este archivo empezarán por `/api`.
* **`@Autowired`:** ¡La inyección de dependencias en acción! Le dice a Spring: *"Busca el objeto que creaste para `MonstruomonRepository` y conéctamelo aquí dentro automáticamente"*. Así te ahorras hacer inicializaciones manuales.
* **`@GetMapping("/monstruomones")`:** Le dice al sistema: *"Si un usuario entra en su navegador a la ruta combinada `http://localhost:8080/api/monstruomones` usando una petición de lectura (GET), ejecuta este método de inmediato"*.

---

## 🔄 4. El Viaje del Dato (Secuencia de Ejecución Paso a Paso)

Para sacar un 10 en el examen, memoriza qué pasa exactamente por detrás cuando pones la URL en tu navegador y le das a Enter:

1. **La Petición:** El navegador manda un grito por internet: *"¡Quiero los datos de `GET /api/monstruomones`!"*.
2. **El Servidor Escucha:** Tu servidor interno de Spring Boot (**Tomcat**) intercepta ese grito en el puerto `8080`.
3. **El Camarero Actúa:** Spring revisa sus archivos y ve que el método `obtenerTodosLosMonstruomones()` de **`MonstruomonController`** coincide exactamente con lo que pide el cliente.
4. **La Orden al Almacén:** El controlador le pide ayuda al repositorio inyectado ejecutando `monstruomonRepository.findAll()`.
5. **La Magia de Hibernate (SQL):** El repositorio coge la petición en Java y la traduce automáticamente a código de base de datos clásico:
`SELECT * FROM monstruomones...`
6. **MySQL Responde:** La base de datos busca en sus tablas y le devuelve las filas de datos crudas a Hibernate.
7. **El Mapeo (ORM):** Hibernate lee esas filas de MySQL e instancia objetos reales en la memoria RAM de Java (crea instancias de `MonstruomonFuego`, `MonstruomonAgua`, etc., rellenando sus datos y listas de ataques).
8. **La Traducción a JSON (Jackson):** El controlador recibe la lista de objetos Java y una herramienta interna llamada *Jackson* la convierte en un texto formateado con llaves `{ }` y comillas (JSON).
9. **El Resultado en Pantalla:** El navegador recibe ese texto JSON, tú activas el tick de "Impresión con sangría" en la pantalla y ves tus monstruomones ordenados y perfectos.
"""

```

### 🎓 ¿Qué vas a encontrar dentro de este archivo `.md`?
He redactado el documento con un enfoque pedagógico ultra estructurado pensado para memorizar conceptos de cara al examen:
1. **La Metáfora del Restaurante:** Un mapa visual simplificado que describe los roles de la capa *Controller* (El camarero), *Repository* (El pinche) y la *Base de datos* (La despensa).
2. **Definiciones Oficiales para el Tribunal:** Explicaciones sencillas pero rigurosas de **Persistencia**, **ORM / Hibernate** e **Inyección de Dependencias**.
3. **Análisis de Código con Lupa (`Monstruomon.java`):** El significado real de etiquetas complejas de herencia en base de datos como `@Inheritance(strategy = InheritanceType.SINGLE_TABLE)`, `@DiscriminatorColumn`, `@Transient` o relaciones `@ManyToMany`.
4. **La Ruta del Dato:** El flujo cronológico paso a paso (desde que haces un clic web hasta que la base de datos MySQL lee el disco duro y se genera el archivo **JSON** final).

¡Muchísimo éxito en el repaso, Velia! Si te surge cualquier duda técnica con alguna de las anotaciones de la guía mientras estudias, coméntamelo y lo aclaramos al instante. ¡A por ese examen! 🚀🔥

```