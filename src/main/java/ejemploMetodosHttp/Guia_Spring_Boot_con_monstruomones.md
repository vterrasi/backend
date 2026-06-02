# Guía de Examen: ¡Spring Boot para Humanos! 🚀

¡Hola, Velia! He preparado esta superguía de estudio en formato Markdown (`.md`) pensada 100% para ti, con un lenguaje súper sencillo ("para dummies") y basada exactamente en todo lo que hemos estado picando en código con tu proyecto **Monstruomon** y las clases de backend.

Aquí tienes las preguntas típicas que te pueden caer en un examen de Spring Boot, explicadas con peras y manzanas, analogías del mundo real y sin tecnicismos raros.

---

## 📌 Bloque 1: El Cerebro de Spring (Conceptos Básicos)

### Pregunta 1: ¿Qué es la "Inyección de Dependencias" (@Autowired) y cómo se la explicarías a tu abuela?
* **Respuesta de Examen:** Es un patrón de diseño donde un objeto no crea los otros objetos que necesita para funcionar, sino que el contenedor de Spring se los "inyecta" ya listos desde fuera.
* **Explicación "Para Dummies":** Imagina que eres una cocinera y vas a hacer una tarta. En lugar de tener que salir de la cocina, ir a la granja a buscar huevos, ordeñar una vaca y fabricar la batidora tú misma desde cero, cuando entras a la cocina **alguien ya te ha dejado los huevos en la mesa y la batidora enchufada**. 
  La anotación `@Autowired` es como decirle a Spring: *"Oye, necesito el robot de cocina aquí, búscalo y pónmelo en la encimera"*. Tu clase solo se preocupa de hacer su trabajo, no de fabricar sus herramientas.

### Pregunta 2: ¿Qué diferencia hay entre `@Component`, `@Service` y `@Repository`? ¿Por qué usamos tantos nombres diferentes si "hacen lo mismo"?
* **Respuesta de Examen:** Todos son estereotipos de Spring que le dicen al framework que cree y gestione esa clase como un "Bean" (un objeto del que se encarga Spring). La diferencia es semántica y de capa arquitectónica: `@Repository` se usa para acceso a datos (BBDD), `@Service` para la lógica de negocio y `@Component` es el genérico para cualquier otra cosa.
* **Explicación "Para Dummies":** Imagina una empresa de construcción. Todos los trabajadores son "empleados" (eso sería `@Component`), pero cada uno tiene un uniforme y un rol diferente:
  * **`@Repository` (El almacenero):** Es el único autorizado para entrar al almacén (la Base de Datos), buscar los materiales (las entidades) y traértelos.
  * **`@Service` (El capataz/diseñador):** No toca los camiones ni el cemento directamente. Él piensa la estrategia. Dice: *"Si nos llega un Monstruomon tipo Fuego, entonces calcula su ataque multiplicando por dos"*. Maneja las reglas del juego.
  * **`@Component` (El peón multiusos):** Hace tareas sueltas que no encajan exactamente en la oficina ni en el almacén (por ejemplo, un traductor de datos o un validador de textos).

---

## 📌 Bloque 2: La Base de Datos (JPA y Hibernate)

### Pregunta 3: ¿Qué es una "Entidad" (`@Entity`) en Spring Boot y para qué sirve el `@Id`?
* **Respuesta de Examen:** Una entidad es una clase Java normal y corriente (POJO) que está mapeada (vinculada) directamente a una tabla de la base de datos relacional. Cada objeto de esa clase será una fila de la tabla. El `@Id` define la clave primaria de esa tabla.
* **Explicación "Para Dummies":** Tu clase `Monstruomon` en Java es el "molde para galletas". La base de datos es la caja donde guardas las galletas. Cuando le pones `@Entity` arriba a la clase, le estás diciendo a Spring: *"Oye, créame una tabla en MySQL que se llame 'Monstruomon' con las mismas columnas que los atributos que tengo aquí"*.
  * El **`@Id`** es el DNI o el número de serie de la galleta. Es lo que hace que un Monstruomon sea único y no se confunda con otro aunque se llamen igual. El `@GeneratedValue(strategy = GenerationType.IDENTITY)` es el autoincrementable: la base de datos le va poniendo el número 1, luego el 2, luego el 3, sin que tú te preocupes.

### Pregunta 4: ¿Qué es y cómo funciona un `JpaRepository`? ¿Tengo que escribir yo las consultas SQL?
* **Respuesta de Examen:** Es una interfaz de Spring Data JPA que, al heredar de ella, te proporciona automáticamente todos los métodos de un CRUD básico (`save()`, `findAll()`, `findById()`, `deleteById()`) sin necesidad de escribir ni una sola línea de código SQL.
* **Explicación "Para Dummies":** Es magia pura. Creas una interfaz, le dices `extends JpaRepository<Monstruomon, Long>` y, de repente, Spring te regala una botonera completa. ¿Quieres guardar un bicho en la base de datos? Llamas a `.save(miMonstruo)`. ¿Quieres borrarlo? `.deleteById(5)`. Spring traduce tu código Java a las consultas SQL de toda la vida por detrás para que tú no sufras.

---

## 📌 Bloque 3: La Puerta de Entrada (Controladores y Web)

### Pregunta 5: ¿Qué es un `@RestController` y qué diferencia hay entre un `@GetMapping` y un `@PostMapping`?
* **Respuesta de Examen:** Un `@RestController` es la clase encargada de recibir las peticiones HTTP desde el exterior (como una web, Postman o el móvil) y devolver las respuestas, generalmente en formato JSON.
  * `@GetMapping` se usa para **solicitar o leer** datos (peticiones de lectura).
  * `@PostMapping` se usa para **enviar o crear** datos nuevos (peticiones de escritura).
* **Explicación "Para Dummies":** El controlador es como el camarero de un restaurante:
  * Tú estás sentado en la mesa (Postman) y le pides la carta. Eso es un **`GET`** (*"Camarero, dame la lista de todos los Monstruomons"*). El camarero va a la cocina (el `@Service`), trae los datos y te los enseña.
  * Si quieres pedir un plato nuevo que no está puesto, le das una nota con los ingredientes. Eso es un **`POST`** (*"Camarero, toma este JSON con los datos de un bicho nuevo y guárdalo"*).

### Pregunta 6: ¿Qué significan las anotaciones `@PathVariable` y `@RequestBody` dentro de los métodos del controlador?
* **Respuesta de Examen:** * `@PathVariable` extrae variables que vienen incrustadas directamente en la URL del endpoint (ej: `/monstruos/5`).
  * `@RequestBody` le dice a Spring que coja el cuerpo de la petición HTTP (que suele ser un texto en formato JSON enviado en un POST) y lo transforme automáticamente en un objeto Java.
* **Explicación "Para Dummies":**
  * **`@PathVariable` (La etiqueta del paquete):** Es cuando la información va a la vista de todos en la dirección de envío. Si pones en la URL `/api/monstruomon/4`, ese `4` es la variable del camino. El método lo atrapa diciendo: *"¡Ah, quieres el bicho número 4!"*.
  * **`@RequestBody` (El contenido de la caja):** Es cuando envías un paquete cerrado. El JSON con el nombre, tipo y ataque del monstruo viaja "oculto" dentro de la petición. Cuando llega al controlador, Spring abre la caja, lee el JSON y, mágicamente, te monta el objeto `Monstruomon` en Java listo para usar.

---

## 💡 El "Chuleta-Resumen" para llevar en la cabeza

| Anotación | ¿Qué le dice a Spring? | Analogía rápida |
| :--- | :--- | :--- |
| **`@SpringBootApplication`** | "Arranca el motor aquí, esta es la raíz de todo." | La llave de contacto del coche. |
| **`@Entity`** | "Esto representa una tabla de la Base de Datos." | El plano de una fila de la tabla. |
| **`@Autowired`** | "Búscame este objeto y conéctamelo ya." | Enchufar un electrodoméstico. |
| **`@RestController`** | "Escucha lo que piden desde internet." | El camarero que atiende las mesas. |
| **`@Service`** | "Aquí dentro están las reglas del juego y las fórmulas." | El cerebro de la operación. |

---

¡Mucho ánimo con el estudio, Velia! Tienes esto controladísimo. Con que te acuerdes del camarero, el almacenero y el capataz, el examen lo tienes aprobado seguro. 🐾💥
