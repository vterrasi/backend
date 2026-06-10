package videoclubSpring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import videoclubSpring.model.Movie;
import videoclubSpring.model.RentaModel;
import videoclubSpring.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/alquileres")
public class RentalController {

    // Simulamos las bases de datos en memoria (Reutilizado e inicializado como en DataInitializer/Previos)
    private List<Movie> listadoPeliculas = new ArrayList<>();
    private List<RentaModel> listadoAlquileres = new ArrayList<>();
    private List<Usuario> listadoUsuarios = new ArrayList<>();

    public RentalController() {
        // Inicializamos las películas mezclando los atributos previos (ID, título, género, available, esNovedad, duración)
        listadoPeliculas.add(new Movie("1", "Matrix", "SciFi", true, false, 136)); // Normal -> 3€
        listadoPeliculas.add(new Movie("2", "Shrek", "Animacion", true, false, 90));   // Normal -> 3€
        listadoPeliculas.add(new Movie("3", "Interstellar", "SciFi", false, true, 169)); // Novedad -> 5€ (Empieza alquilada)
        listadoPeliculas.add(new Movie("4", "Constantine", "Accion", true, false, 121)); // Normal -> 3€
        listadoPeliculas.add(new Movie("5", "The Aviator", "Accion", true, true, 170));  // Novedad -> 5€

        // Inicialización de usuarios de prueba para el extra interactivo
        listadoUsuarios.add(new Usuario("u1", "Velia"));
        listadoUsuarios.add(new Usuario("u2", "Pepe"));

        // Añadimos un alquiler histórico inicial de prueba para que la película 3 empiece ocupada
        List<String> pelisIniciales = new ArrayList<>();
        pelisIniciales.add("3");
        listadoAlquileres.add(new RentaModel("ALQ-99", pelisIniciales, 5.0, "u1"));
        listadoUsuarios.get(0).getAlquileresIds().add("ALQ-99");
    }

    // =========================================================================
    // RUTA REUTILIZADA Y ADAPTADA: CATÁLOGO CON FILTRO DURACIÓN ($maxRT) Y DISPONIBLES
    // =========================================================================
    @GetMapping("/peliculas")
    public List<Movie> getMovies(@RequestParam(value = "$maxRT", required = false) Integer maxRT) {
        // Si el usuario usa el filtro de duración (reutilizado de PeliculaController)
        if (maxRT != null) {
            List<Movie> filtradasPorTiempo = new ArrayList<>();
            for (Movie m : listadoPeliculas) {
                if (m.getDuracion() <= maxRT) {
                    filtradasPorTiempo.add(m);
                }
            }
            return filtradasPorTiempo;
        }
        return listadoPeliculas;
    }

    @GetMapping("/peliculas/disponibles")
    public List<Movie> getPeliculasDisponibles() {
        List<Movie> disponibles = new ArrayList<>();
        for (Movie m : listadoPeliculas) {
            if (m.isAvailable()) {
                disponibles.add(m);
            }
        }
        return disponibles;
    }

    // =========================================================================
    // RUTAS ORIGINALES DEL RECURSO ALQUILER
    // =========================================================================

    @GetMapping
    public List<RentaModel> obtenerTodosLosAlquileres() {
        return listadoAlquileres;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerAlquilerPorId(@PathVariable String id) {
        for (RentaModel alquiler : listadoAlquileres) {
            if (alquiler.getId().equalsIgnoreCase(id)) {
                return ResponseEntity.ok(alquiler);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Alquiler no encontrado.");
    }

    @GetMapping("/pelicula/{movieId}")
    public List<RentaModel> obtenerAlquileresPorPelicula(@PathVariable String movieId) {
        List<RentaModel> filtrados = new ArrayList<>();
        for (RentaModel alquiler : listadoAlquileres) {
            if (alquiler.getMovies().contains(movieId)) {
                filtrados.add(alquiler);
            }
        }
        return filtrados;
    }

    // =========================================================================
    // OPERACIÓN CORE UNIFICADA: CREAR ALQUILER INTEROPERABLE (POST)
    // =========================================================================
    @PostMapping
    public ResponseEntity<?> crearAlquiler(@RequestBody RentaModel peticionAlquiler) {
        double precioAcumulado = 0.0;
        List<Movie> pelisApartar = new ArrayList<>();

        // 1. Validar que las películas de la lista existan y estén libres (available == true)
        for (String idPeli : peticionAlpeliIdList(peticionAlquiler)) {
            Movie peli = buscarPeliculaPorId(idPeli);
            if (peli == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("La película con ID '" + idPeli + "' no existe en el sistema.");
            }
            if (!peli.isAvailable()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("La película '" + peli.getTitulo() + "' ya está alquilada por otro cliente.");
            }

            // Reutilización de la lógica de negocio de tarifas (3€ normal / 5€ novedad)
            precioAcumulado += peli.isEsNovedad() ? 5.0 : 3.0;
            pelisApartar.add(peli);
        }

        // 2. Interoperabilidad opcional con Usuarios (Extra del Ejercicio)
        Usuario usuarioAsociado = null;
        if (peticionAlquiler.getUsuarioId() != null) {
            usuarioAsociado = buscarUsuarioPorId(peticionAlquiler.getUsuarioId());
            if (usuarioAsociado == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El usuario provisto con ID '" + peticionAlquiler.getUsuarioId() + "' no existe.");
            }
        }

        // 3. Procesar el Alquiler si todo es válido
        String nuevoId = "ALQ-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        peticionAlquiler.setId(nuevoId);
        peticionAlquiler.setPrecioTotal(precioAcumulado); // Asignamos el precio calculado automáticamente

        // Cambiar estados de las películas a no disponibles
        for (Movie m : pelisApartar) {
            m.setAvailable(false);
        }

        // Guardar el alquiler en el histórico global
        listadoAlquileres.add(peticionAlquiler);

        // Si venía un usuario seleccionado, le inyectamos la orden en su historial (Interoperabilidad)
        if (usuarioAsociado != null) {
            usuarioAsociado.getAlquileresIds().add(nuevoId);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(peticionAlquiler);
    }

    // =========================================================================
    // OPERACIÓN REUTILIZADA Y MEJORADA: BORRAR (DEVOLVER) ALQUILER (DELETE)
    // =========================================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> devolverAlquiler(@PathVariable String id) {
        RentaModel alquilerEncontrado = null;
        for (RentaModel alquiler : listadoAlquileres) {
            if (alquiler.getId().equalsIgnoreCase(id)) {
                alquilerEncontrado = alquiler;
                break;
            }
        }

        if (alquilerEncontrado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No se encontró el alquiler con ID " + id);
        }

        // Volver a poner disponibles todas las películas que contenía el paquete del alquiler
        for (String idPeli : alquilerEncontrado.getMovies()) {
            Movie peli = buscarPeliculaPorId(idPeli);
            if (peli != null) {
                peli.setAvailable(true);
            }
        }

        // Nota de recursoAlquiler: El registro no se borra de listadoAlquileres para auditoría histórica
        return ResponseEntity.ok("Devolución completada con éxito. Las películas están de nuevo libres en catálogo.");
    }

    // =========================================================================
    // EXTRA REUTILIZADO: BUSCADOR DE ADMINISTRACIÓN POR NOMBRE DE USUARIO
    // =========================================================================
    @GetMapping("/usuario/buscar/{nombre}")
    public ResponseEntity<?> buscarAlquileresPorNombreUsuario(@PathVariable String nombre) {
        Usuario usuarioEncontrado = null;
        for (Usuario u : listadoUsuarios) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                usuarioEncontrado = u;
                break;
            }
        }

        if (usuarioEncontrado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }

        // Buscamos todos los registros de alquiler vinculados al ID de ese usuario
        List<RentaModel> resultado = new ArrayList<>();
        for (RentaModel r : listadoAlquileres) {
            if (usuarioEncontrado.getId().equals(r.getUsuarioId())) {
                resultado.add(r);
            }
        }
        return ResponseEntity.ok(resultado);
    }

    // --- MÉTODOS AUXILIARES DE BÚSQUEDA INTERNA ---
    private Movie buscarPeliculaPorId(String id) {
        for (Movie m : listadoPeliculas) {
            if (m.getId().equals(id)) return m;
        }
        return null;
    }

    private Usuario buscarUsuarioPorId(String id) {
        for (Usuario u : listadoUsuarios) {
            if (u.getId().equals(id)) return u;
        }
        return null;
    }

    private List<String> peticionAlpeliIdList(RentaModel r) {
        return r.getMovies() != null ? r.getMovies() : new ArrayList<>();
    }
}