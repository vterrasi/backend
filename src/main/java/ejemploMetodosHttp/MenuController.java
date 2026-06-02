package ejemploMetodosHttp;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    // 1. Simulación de la persistencia: Lista estática con los ID tipo String como pide el enunciado
    private final List<Plato> platos = new ArrayList<>(List.of(
            new Plato("p01", "Tacos de pollo", 12.0f),
            new Plato("p02", "Hamburguesa", 15.5f),
            new Plato("p03", "Ensalada", 8.0f),
            new Plato("p04", "Pizza Marinara", 10.0f),
            new Plato("p05", "Brochetas de pulpo", 18.0f),
            new Plato("p06", "Paella valenciana", 20.0f),
            new Plato("p07", "Lasaña de berenjena", 11.2f)
    ));

    // PETICIÓN 1: Obtener todo el listado de platos
    // URL: http://localhost:8080/menu
    @GetMapping
    public List<Plato> obtenerMenu() {
        return platos;
    }

    // PETICIÓN 2: Obtener un plato en base a su id
    // URL ejemplo: http://localhost:8080/menu/buscar-id/p03
    @GetMapping("/buscar-id/{id}")
    public Plato obtenerPorId(@PathVariable String id) {
        for (Plato p : platos) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p; // Si lo encuentra, lo devuelve
            }
        }
        return null; // Si no lo encuentra, devuelve vacío
    }

    // PETICIÓN 3: Obtener un plato en base a su nombre
    // URL ejemplo: http://localhost:8080/menu/buscar-nombre/Hamburguesa
    @GetMapping("/buscar-nombre/{name}")
    public Plato obtenerPorNombre(@PathVariable String name) {
        for (Plato p : platos) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    // EXTRA O REQUISITO: Añadir un plato (POST) para poder probar el resto bien
    // URL: http://localhost:8080/menu
    @PostMapping
    public String anadirPlato(@RequestBody Plato nuevoPlato) {
        platos.add(nuevoPlato);
        return "¡Plato añadido con éxito: " + nuevoPlato.getName() + "!";
    }

    // PETICIÓN 4: Actualizar un plato (actualización completa) en base a su ID en la URL
    // URL: http://localhost:8080/menu/p02
    @PutMapping("/{id}")
    public String actualizarPlato(@PathVariable String id, @RequestBody Plato platoModificado) {
        for (int i = 0; i < platos.size(); i++) {
            if (platos.get(i).getId().equalsIgnoreCase(id)) {
                // Reemplazamos el plato viejo por el modificado en esa posición
                platos.set(i, platoModificado);
                return "Plato con ID " + id + " actualizado por completo a: " + platoModificado.getName();
            }
        }
        return "No se encontró ningún plato con el ID: " + id;
    }

    // PETICIÓN 5: Borrar un plato en base a su ID
    // URL: http://localhost:8080/menu/p01
    @DeleteMapping("/{id}")
    public String borrarPlato(@PathVariable String id) {
        for (int i = 0; i < platos.size(); i++) {
            if (platos.get(i).getId().equalsIgnoreCase(id)) {
                Plato eliminado = platos.remove(i);
                return "Se ha eliminado del menú el plato: " + eliminado.getName();
            }
        }
        return "No se pudo borrar. No existe el ID: " + id;
    }
}

// 2. El Modelo: El molde exacto con las propiedades que exige el enunciado (id, name, price)
class Plato {
    private String id;     // <-- Cambiado a String como pide el ejercicio
    private String name;   // <-- Cambiado a name
    private float price;   // <-- Cambiado a price (float)

    // Constructor corregido para aceptar el ID String
    public Plato(String id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getters necesarios para que Spring funcione
    public String getId() { return id; }
    public String getName() { return name; }
    public float getPrice() { return price; }
}