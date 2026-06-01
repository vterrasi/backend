# Los 5 Métodos HTTP Explicados con el Menú de un Restaurante

Imagínate que una base de datos es como el **menú de un restaurante** colgado en la pared. Los métodos HTTP son las acciones que puedes hacer con ese menú. 

Para que sea fácil, usaremos como ejemplo que quieres gestionar **los platos de comida** de ese menú.

---

## 1. GET (Ver / Leer)
Sirve para **pedir información**. No cambias nada, solo miras.

* **El ejemplo:** Abres el menú para ver qué platos hay disponibles.
* **En la práctica:** Le dices al servidor: *"Oye, muéstrame la lista de platos"*. El servidor te responde con la comida disponible.

## 2. POST (Crear / Añadir)
Sirve para **enviar información nueva** y que se guarde.

* **El ejemplo:** El chef inventa un plato nuevo (por ejemplo, "Tacos de pollo") y lo apunta al final del menú.
* **En la práctica:** Mandas un paquete con los datos del nuevo plato (*Nombre: Tacos, Precio: $5*). El servidor lo recibe y lo añade a la lista.

## 3. PUT (Cambiar todo / Reemplazar)
Sirve para **modificar algo que ya existe, pero cambiándolo por completo**. Si te olvidas de poner un dato, ese dato se borra.

* **El ejemplo:** Quieres actualizar el plato "Tacos de pollo". Con PUT, tienes que volver a escribir **todo** el renglón: el nombre, el precio y los ingredientes. Si solo dices el precio nuevo, el nombre del plato se borrará.
* **En la práctica:** Mandas el plato completo editado para sustituir la versión vieja por la nueva.

## 4. PATCH (Corregir un pedacito / Parchear)
Es el hermano menor de PUT. Sirve para **cambiar solo un detalle suelto** sin tocar el resto.

* **El ejemplo:** Los "Tacos de pollo" siguen llamándose igual, pero subieron de precio. Usas un "parche" (PATCH) para tachar solo el precio viejo y poner el nuevo. El nombre no lo tocas.
* **En la práctica:** Solo mandas el dato exacto que quieres cambiar (*Precio: $6*). Lo demás se queda como estaba.

## 5. DELETE (Borrar)
Sirve para **eliminar** algo para siempre.

* **El ejemplo:** Se acabó el pollo en la cocina, así que coges un borrador y eliminas los "Tacos de pollo" del menú.
* **En la práctica:** Le dices al servidor el código o nombre del plato y le ordenas: *"Borra esto"*.

---

### 📋 Resumen rápido para recordar:

| Método | ¿Qué hace en el menú? | Ejemplo rápido |
| :--- | :--- | :--- |
| **GET** | Leer | Mirar la carta. |
| **POST** | Crear | Añadir un plato nuevo. |
| **PUT** | Reemplazar todo | Cambiar el plato entero por otro. |
| **PATCH**| Modificar un trozo | Cambiar solo el precio de un plato. |
| **DELETE**| Borrar | Quitar un plato del menú. |

---

## 🎛️ Las 4 Formas de Pasar Parámetros en Peticiones REST

Imagínate que los parámetros son los **detalles específicos que le das al camarero** para que sepa exactamente qué plato quieres, en qué mesa te sientas o qué modificaciones necesitas.

### 1. 🛣️ Path Variables (Parámetros en la Ruta)
Se usan cuando el dato es **parte fija de la propia dirección web** (la URL). Es un parámetro **obligatorio** y sirve para identificar un recurso único de forma directa.
* **Cómo se ve en la URL:** `http://localhost:8080/menu/2` *(Ese **2** es el parámetro)*.
* **El ejemplo del restaurante:** Vas directo a sentarte a la **mesa número 2**. La mesa forma parte de la estructura física del lugar.
* **En Java (Spring Boot):** Se recibe en el código usando la anotación `@PathVariable`.

### 2. 🔎 Request Parameters / Query Parameters (Parámetros de Consulta)
Se usan principalmente para **filtrar, ordenar o buscar** cosas dentro de una lista. Van al final de la URL, justo después de un signo de interrogación (`?`), y son **opcionales**. Si no los pones, el servidor te lo muestra todo igualmente.
* **Cómo se ve en la URL:** `http://localhost:8080/menu?tipo=picante&precioMaximo=10`
* **El ejemplo del restaurante:** Le dices al camarero: *"Tráeme el menú, pero por favor, fíltrame solo los platos que sean **picantes** y que cuesten menos de **10 dólares**"*.
* **En Java (Spring Boot):** Se recibe en el código usando la anotación `@RequestParam`.

### 3. 📦 Request Body (El Cuerpo de la Petición)
Se usa para enviar **mucha información o datos complejos** (como un objeto completo o un formulario enorme). Estos datos viajan "escondidos" dentro del paquete de la petición, por lo que **no se ven a simple vista en la URL**. Es el sistema que usamos por defecto en los métodos **POST**, **PUT** y **PATCH**.
* **Cómo se ve (Formato JSON):** La URL se queda limpia (`/menu`), pero por dentro del paquete viaja este texto:
  ```json
  {
    "nombre": "Tacos de pollo",
    "precio": 5.99,
    "ingredientes": ["Pollo", "Tortilla", "Cebolla"]
  }
El ejemplo del restaurante: Le entregas al chef una nota de papel con la receta completa escrita de un plato nuevo que quieres que cocine desde cero.

En Java (Spring Boot): Se recibe en el código usando la anotación @RequestBody.

### 4. ✉️ Request Headers (Las Cabeceras)
   Se usan para enviar información de configuración, datos técnicos o de seguridad (como contraseñas, tokens de autenticación para saber quién eres, o el idioma en el que necesitas que te responda el servidor). Al igual que el cuerpo, no se ven en la URL.

* **Cómo se ve:** Viaja junto a la petición como metadatos invisibles para el usuario común. Por ejemplo: Authorization: Token12345 o Accept-Language: es.

El ejemplo del restaurante: Es como el carné de identidad que le enseñas al portero para que te deje entrar a la zona VIP, o cuando le dices directamente al camarero: "Por favor, háblame en español".

En Java (Spring Boot): Se recibe en el código usando la anotación @RequestHeader.

---

### 📋 Resumen de Parámetros para Llevar a Casa

* **Path Variable (/menu/2):** Para ir directo a un plato o recurso específico (Obligatorio).

* **Query Param (/menu?tipo=picante):** Para filtrar, ordenar o buscar entre muchos platos (Opcional).

* **Body (Formato JSON invisible):** Para enviar un plato entero con todos sus ingredientes pesados (Datos Complejos).

* **Header (Metadato invisible):** Para las credenciales, llaves secretas, tokens o idioma (Configuración).
