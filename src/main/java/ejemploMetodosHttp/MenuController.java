package ejemploMetodosHttp;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

// Le decimos a Java que esta clase va a manejar las peticiones del menú
@RestController
@RequestMapping("/menu")
public class MenuController {

    // Simulamos nuestra base de datos como una lista de platos sencilla
    private final List<String> platos = new ArrayList<>(List.of("Tacos de pollo", "Hamburguesa", "Ensalada", "Pizza Marinara", "Brochetas de pulpo", "Paella valenciana", "Lasaña de berenjena"));

    // 1. GET - Ver todos los platos
    // Si entras a: http://localhost:8080/menu
    //@GetMapping
    //public List<String> obtenerMenu() { return platos; // Devuelve la lista completa para que la veas }


    // 1. GET - Ver todos los platos (¡Ahora con filtro opcional!)
    // Si entras a: http://localhost:8080/menu -> Te da T0DO
    // Si entras a: http://localhost:8080/menu?maxRT=10 -> Solo platos con 10 letras o menos
    @GetMapping
    public List<String> obtenerMenu(@RequestParam(name = "maxRT", required = false) Integer maxRT) { // <-- 1. Recibimos el filtro opcional

        // Si el usuario NO ha puesto el filtro en la URL, devolvemos la lista entera como siempre
        if (maxRT == null) { // <-- 2. Si es null, es que no lo usaron
            return platos;
        }

        // Si el usuario SÍ puso el filtro, creamos una lista nueva solo con los que cumplan la condición
        List<String> platosFiltrados = new ArrayList<>(); // <-- 3. Bolsa vacía para guardar el resultado

        for (String plato : platos) {
            // Contamos las letras del plato. Si es menor o igual al filtro, lo guardamos
            if (plato.length() <= maxRT) { // <-- 4. Aplicamos el filtro (duración/longitud máxima)
                platosFiltrados.add(plato);
            }
        }

        return platosFiltrados; // <-- 5. Devolvemos solo los platos que pasaron el filtro
    }

    // 2. POST - Añadir un plato nuevo.
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