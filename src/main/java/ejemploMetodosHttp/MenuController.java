package ejemploMetodosHttp;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

// Le decimos a Java que esta clase va a manejar las peticiones del menú
@RestController
@RequestMapping("/menu")
public class MenuController {

    // Simulamos nuestra base de datos como una lista de platos sencilla
    private final List<String> platos = new ArrayList<>(List.of("Tacos de pollo", "Hamburguesa", "Ensalada"));

    // 1. GET - Ver todos los platos
    // Si entras a: http://localhost:8080/menu
    @GetMapping
    public List<String> obtenerMenu() {
        return platos; // Devuelve la lista completa para que la veas
    }

    // 2. POST - Añadir un plato nuevo
    // Mandas el nombre del nuevo plato en el "cuerpo" (body) de la petición
    @PostMapping
    public String anadirPlato(@RequestBody String nuevoPlato) {
        platos.add(nuevoPlato); // Lo guardamos en nuestra lista
        return "¡Plato añadido con éxito: " + nuevoPlato + "!";
    }

    // 3. PUT - Reemplazar un plato entero
    // Usamos el número de posición (id) del plato que queremos cambiar entero
    @PutMapping("/{id}")
    public String reemplazarPlato(@PathVariable int id, @RequestBody String platoModificado) {
        // Cambiamos el plato viejo por el nuevo por completo
        platos.set(id, platoModificado);
        return "Plato en la posición " + id + " reemplazado por: " + platoModificado;
    }

    // 4. PATCH - Modificar solo un pedazo
    // Es como ponerle un parche a un plato existente
    @PatchMapping("/{id}")
    public String parchearPlato(@PathVariable int id, @RequestBody String pedazoNuevo) {
        String platoActual = platos.get(id);
        // Supongamos que solo "parcheamos" el texto añadiéndole un extra al final
        String platoParcheado = platoActual + " (" + pedazoNuevo + ")";
        platos.set(id, platoParcheado);
        return "Plato actualizado con un parche: " + platoParcheado;
    }

    // 5. DELETE - Borrar un plato
    // Le pasamos el número de posición (id) del plato que queremos eliminar
    @DeleteMapping("/{id}")
    public String borrarPlato(@PathVariable int id) {
        String platoEliminado = platos.remove(id); // Lo quita de la lista
        return "Se ha eliminado del menú: " + platoEliminado;
    }
}