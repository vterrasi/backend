package videoclubMysqlSpring;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class PeliculaController {

    // Necesitamos los DOS robots ahora: el de películas y el de alquileres
    private final PeliculaRepository peliculaRepository;
    private final AlquilerRepository alquilerRepository;

    // Se los pedimos a Spring en el constructor
    public PeliculaController(PeliculaRepository peliculaRepository, AlquilerRepository alquilerRepository) {
        this.peliculaRepository = peliculaRepository;
        this.alquilerRepository = alquilerRepository;
    }

    // --- NUESTRA RUTA GET (La que ya tenías y funciona genial) ---
    @GetMapping("/peliculas")
    public List<Pelicula> getMovies(@RequestParam(value = "$maxRT", required = false) Integer maxRT) {
        if (maxRT != null) {
            return peliculaRepository.findByDuracionLessThanEqual(maxRT);
        }
        return peliculaRepository.findAll();
    }

    // --- 🚀 NUEVA RUTA POST: ¡EL BOTÓN DE ALQUILAR! 🚀 ---
    @PostMapping("/alquileres")
    public String alquilarPelicula(@RequestParam String titulo) {

        // 1. Buscamos todas las películas en la base de datos
        List<Pelicula> todas = peliculaRepository.findAll();
        Pelicula peliculaEncontrada = null;

        // 2. Buscamos si el título coincide (ignorando mayúsculas/minúsculas)
        for (Pelicula p : todas) {
            if (p.getTitulo().equalsIgnoreCase(titulo)) {
                peliculaEncontrada = p;
                break;
            }
        }

        // CONTROL DE ERRORES A: Si la película no existe en el videoclub
        if (peliculaEncontrada == null) {
            return "{\"error\": \"400 Bad Request\", \"mensaje\": \"La película '" + titulo + "' no existe en el catálogo.\"}";
        }

        // CONTROL DE ERRORES B: Si existe pero no nos quedan copias en stock
        if (peliculaEncontrada.getStock() <= 0) {
            return "{\"error\": \"400 Bad Request\", \"mensaje\": \"Lo sentimos, no queda stock de '" + titulo + "'.\"}";
        }

        // 3. SI TODO ESTÁ BIEN: Reducimos el stock en 1
        peliculaEncontrada.reducirStock();
        peliculaRepository.save(peliculaEncontrada); // Guardamos el stock actualizado en MySQL

        // 4. Calculamos el precio (5.0€ si es novedad, 3.0€ si es vieja)
        double precioCobrado = peliculaEncontrada.getPrecio();

        // 5. Creamos el ticket de alquiler y lo guardamos en tu nueva tabla de MySQL
        Alquiler nuevoAlquiler = new Alquiler(peliculaEncontrada, precioCobrado);
        alquilerRepository.save(nuevoAlquiler);

        // 6. Respondemos un mensaje JSON bonito confirmando el éxito
        return "{\n" +
                "  \"status\": \"success\",\n" +
                "  \"mensaje\": \"Película alquilada correctamente\",\n" +
                "  \"detalles\": {\n" +
                "    \"pelicula\": \"" + peliculaEncontrada.getTitulo() + "\",\n" +
                "    \"precioCobrado\": " + precioCobrado + ",\n" +
                "    \"stockRestante\": " + peliculaEncontrada.getStock() + "\n" +
                "  }\n" +
                "}";
    }
}