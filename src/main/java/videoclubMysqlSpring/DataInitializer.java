package videoclubMysqlSpring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PeliculaRepository peliculaRepository;

    // Le pedimos a Spring que nos preste su robot buscador/guardador
    public DataInitializer(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Solo añadimos las películas si la tabla está vacía, para no duplicarlas cada vez que arranques
        if (peliculaRepository.count() == 0) {
            // Título, Género, Stock, ¿Es Novedad?, ¡Y DURACIÓN EN MINUTOS!
            peliculaRepository.save(new Pelicula("Matrix", "SciFi", 5, false, 136));
            peliculaRepository.save(new Pelicula("Shrek", "Animación", 2, false, 90));
            peliculaRepository.save(new Pelicula("Interstellar", "SciFi", 6, true, 169));
            peliculaRepository.save(new Pelicula("Todo a la vez en todas partes", "Drama, Comedia", 3, true, 139));
            peliculaRepository.save(new Pelicula("Forrest Gump", "Drama, Comedia", 5, false, 142));
            peliculaRepository.save(new Pelicula("Constantine", "Acción", 4, false, 121));

            System.out.println("-> [BASE DE DATOS] ¡Películas de prueba insertadas con éxito en monstruomon_db!");
        }
    }
}