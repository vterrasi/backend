package ejemploMetodosHttp;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

// Le decimos a Java que esta clase va a manejar las peticiones del menú
@RestController
@RequestMapping("/menu")
public class MenuController {

    // 0. Ahora nuestra "base de datos" guarda objetos Plato con su nombre y su precio
    private final List<Plato> platos = new ArrayList<>(List.of(
            new Plato("Tacos de pollo", 12),
            new Plato("Hamburguesa", 15),
            new Plato("Ensalada", 8),
            new Plato("Pizza Marinara", 10),
            new Plato("Brochetas de pulpo", 18),
            new Plato("Paella valenciana", 20),
            new Plato("Lasaña de berenjena", 11)
    ));

    // GET - Ver todos los platos (¡Ahora con filtro opcional!)
    // Si entras a: http://localhost:8080/menu -> Te da T0DO
    // Si entras a: http://localhost:8080/menu?maxRT=10 -> Solo platos con 10 letras o menos
    // 1. El mét0do GET con el filtro opcional maxRT
    @GetMapping
    public List<Plato> obtenerMenu(@RequestParam(name = "maxRT", required = false) Integer maxRT) {

        // Si no ponen filtro, devolvemos toda la carta con sus precios
        if (maxRT == null) {
            return platos;
        }

        // Si ponen ?maxRT=12, buscamos los platos que cuesten 12€ o menos
        List<Plato> platosFiltrados = new ArrayList<>();

        for (Plato p : platos) {
            // ¡Aquí está la magia! Comparamos contra el precio real del plato
            if (p.getPrecio() <= maxRT) {
                platosFiltrados.add(p);
            }
        }

        return platosFiltrados;
    }

    // 2. POST - Añadir un plato nuevo.
    // Ahora el cliente nos manda un JSON con { "nombre": "Croquetas", "precio": 9 }
    @PostMapping
    public String anadirPlato(@RequestBody Plato nuevoPlato) { // <-- Cambiado a Plato
        platos.add(nuevoPlato);
        return "¡Plato añadido con éxito: " + nuevoPlato.getNombre() + " (" + nuevoPlato.getPrecio() + "€)!";
    }

    // 3. PUT - Reemplazar un plato entero
    @PutMapping("/{id}")
    public String reemplazarPlato(@PathVariable int id, @RequestBody Plato platoModificado) { // <-- Cambiado a Plato
        platos.set(id, platoModificado);
        return "Plato en la posición " + id + " reemplazado por: " + platoModificado.getNombre();
    }

    // 4. PATCH - Modificar solo un pedazo (Por ejemplo, cambiarle solo el precio)
    // Nos mandan un número suelto en el cuerpo para actualizar el precio
    @PatchMapping("/{id}")
    public String parchearPrecioPlato(@PathVariable int id, @RequestBody int nuevoPrecio) { // <-- Cambiado para actualizar precio
        Plato platoActual = platos.get(id);

        // Creamos un plato nuevo con el mismo nombre pero el precio modificado
        Plato platoActualizado = new Plato(platoActual.getNombre(), nuevoPrecio);
        platos.set(id, platoActualizado);

        return "Precio del plato '" + platoActual.getNombre() + "' actualizado a: " + nuevoPrecio + "€";
    }

    // 5. DELETE - Borrar un plato
    @DeleteMapping("/{id}")
    public String borrarPlato(@PathVariable int id) {
        Plato platoEliminado = platos.remove(id); // <-- Ahora recoge un objeto Plato
        return "Se ha eliminado del menú: " + platoEliminado.getNombre();
    }
}

// El molde para crear platos de verdad (Lo dejamos aquí abajo, fuera de la clase principal)
class Plato {
    private String nombre;
    private int precio;

    public Plato(String nombre, int precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() { return nombre; }
    public int getPrecio() { return precio; }
}