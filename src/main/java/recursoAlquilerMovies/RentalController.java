package recursoAlquilerMovies;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/alquileres") // Todas las URLs de este controlador empezarán por /alquileres
public class RentalController {

    // Simulamos las bases de datos en memoria (listas privadas)
    private List<Movie> listadoPeliculas = new ArrayList<>();
    private List<RentaModel> listadoAlquileres = new ArrayList<>();

    // Constructor para inicializar datos falsos (Mock) y probar a gusto
    public RentalController() {
        // Añadimos unas cuantas películas de prueba
        listadoPeliculas.add(new Movie("1", "Matrix", "SciFi", true));
        listadoPeliculas.add(new Movie("2", "Shrek", "Animacion", true));
        listadoPeliculas.add(new Movie("3", "Interstellar", "SciFi", false)); // Esta empieza no disponible
        listadoPeliculas.add(new Movie("4", "Constantine", "Accion", true));
        listadoPeliculas.add(new Movie("5", "The Aviator", "Accion", true));
        listadoPeliculas.add(new Movie("6", "Clockwork Orange", "Thriller Psicologico", true));

        // Añadimos un alquiler de prueba inicial (La película 3 ya está alquilada aquí)
        List<String> pelisAlquiladas = new ArrayList<>();
        pelisAlquiladas.add("3");
        listadoAlquileres.add(new RentaModel("RENT-100", pelisAlquiladas));

        RentaModel alquiler2 = new RentaModel("RENT-101", List.of("1", "5"));
        listadoAlquileres.add(alquiler2);

        RentaModel alquiler3 = new RentaModel("RENT-102", List.of("1", "6"));
        listadoAlquileres.add(alquiler3);

    }

    // --- 1. OBTENER TODOS LOS ALQUILERES ---
    // URL: GET http://localhost:8080/alquileres
    @GetMapping
    public List<RentaModel> obtenerTodos() {
        return listadoAlquileres;
    }

    // --- 2. OBTENER UN ALQUILER POR SU ID ---
    // URL: GET http://localhost:8080/alquileres/RENT-100
    @GetMapping("/{id}")
    public RentaModel obtenerPorId(@PathVariable String id) {
        for (RentaModel alquiler : listadoAlquileres) {
            if (alquiler.getId().equalsIgnoreCase(id)) {
                return alquiler; // Devuelve el alquiler si coincide el ID
            }
        }
        return null; // Si no lo encuentra (puedes mejorar esto devolviendo un error HTTP en el futuro)
    }

    // --- 3. OBTENER ALQUILERES EN BASE A LA ID DE UNA PELÍCULA ---
    // URL: GET http://localhost:8080/alquileres/pelicula/3
    @GetMapping("/pelicula/{movieId}")
    public List<RentaModel> obtenerPorPelicula(@PathVariable String movieId) {
        List<RentaModel> encontrados = new ArrayList<>();

        for (RentaModel alquiler : listadoAlquileres) {
            // Comprobamos si la lista de películas de ese alquiler contiene la ID buscada
            if (alquiler.getMovies().contains(movieId)) {
                encontrados.add(alquiler);
            }
        }
        return encontrados;
    }

    // --- 4. CREAR UN ALQUILER ---
    // URL: POST http://localhost:8080/alquileres
    // En el Body (JSON) mandamos una lista de IDs de películas, por ejemplo: [ "1", "2" ]
    @PostMapping
    public String crearAlquiler(@RequestBody List<String> peliculasSolicitadas) {

        // Paso A: Comprobar que TODAS las películas solicitadas existan y estén disponibles [cite: 4]
        for (String idBuscada : peliculasSolicitadas) {
            Movie peliEncontrada = buscarPeliculaPorId(idBuscada);

            if (peliEncontrada == null) {
                return "Error: La película con ID " + idBuscada + " no existe.";
            }
            if (!peliEncontrada.isAvailable()) {
                return "Error: La película '" + peliEncontrada.getTitulo() + "' ya está alquilada (no disponible).";
            }
        }

        // Paso B: Marcar las películas elegidas como NO DISPONIBLES [cite: 5]
        for (String idBuscada : peliculasSolicitadas) {
            Movie peliEncontrada = buscarPeliculaPorId(idBuscada);
            if (peliEncontrada != null) {
                peliEncontrada.setAvailable(false); // Cambia el estado a No disponible [cite: 5]
            }
        }

        // Paso C: Crear el objeto Alquiler y guardarlo en el sistema [cite: 5]
        String nuevoIdAlquiler = "RENT-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        RentaModel nuevoAlquiler = new RentaModel(nuevoIdAlquiler, peliculasSolicitadas);
        listadoAlquileres.add(nuevoAlquiler);

        return "¡Alquiler " + nuevoIdAlquiler + " creado con éxito!";
    }

    // --- 5. BORRAR/CANCELAR UN ALQUILER ---
    // URL: DELETE http://localhost:8080/alquileres/RENT-100
    @DeleteMapping("/{id}")
    public String borrarAlquiler(@PathVariable String id) {
        RentaModel alquilerEncontrado = null;

        for (RentaModel alquiler : listadoAlquileres) {
            if (alquiler.getId().equalsIgnoreCase(id)) {
                alquilerEncontrado = alquiler;
                break;
            }
        }

        if (alquilerEncontrado == null) {
            return "Error: No se encontró ningún alquiler con ID " + id;
        }

        // Paso A: Poner las películas que componen el alquiler otra vez como DISPONIBLES [cite: 6]
        for (String idPeli : alquilerEncontrado.getMovies()) {
            Movie peli = buscarPeliculaPorId(idPeli);
            if (peli != null) {
                peli.setAvailable(true); // Vuelve a estar libre para alquilar [cite: 6]
            }
        }

        // Paso B: ¡OJO! El enunciado dice: "El alquiler en sí no se borrará, quedando para poder consultarlo" [cite: 7]
        // Así que NO hacemos un listadoAlquileres.remove(). Lo dejamos en la lista intacto.

        return "El alquiler " + id + " ha sido procesado. Las películas vuelven a estar disponibles. El registro se mantiene en el sistema.";
    }

    // --- MÉT0DO AUXILIAR INTERNO ---
    // Nos ayuda a buscar rápido una película por su ID en nuestra lista de simulación
    private Movie buscarPeliculaPorId(String id) {
        for (Movie m : listadoPeliculas) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }
}