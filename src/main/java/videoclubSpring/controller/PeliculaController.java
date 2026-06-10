package videoclubSpring.controller;

import org.springframework.web.bind.annotation.*;
import videoclubSpring.model.Movie;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/peliculas") // Así ordenamos la ruta base para películas
public class PeliculaController {

    // Simulamos el almacén de películas en memoria igual que en RentalController
    private List<Movie> listadoPeliculas = new ArrayList<>();

    public PeliculaController() {
        // Inicializamos las mismas películas para que el catálogo tenga vida propia
        listadoPeliculas.add(new Movie("1", "Matrix", "SciFi", true, false, 136));
        listadoPeliculas.add(new Movie("2", "Shrek", "Animacion", true, false, 90));
        listadoPeliculas.add(new Movie("3", "Interstellar", "SciFi", false, true, 169));
        listadoPeliculas.add(new Movie("4", "Constantine", "Accion", true, false, 121));
        listadoPeliculas.add(new Movie("5", "The Aviator", "Accion", true, true, 170));
    }

    // --- RUTA: OBTENER CATÁLOGO O FILTRAR POR TIEMPO ($maxRT) ---
    // En Thunder Client: GET http://localhost:8080/peliculas o http://localhost:8080/peliculas?$maxRT=120
    @GetMapping
    public List<Movie> getMovies(@RequestParam(value = "$maxRT", required = false) Integer maxRT) {
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

    // --- RUTA: OBTENER SOLO LAS DISPONIBLES ---
    // En Thunder Client: GET http://localhost:8080/peliculas/disponibles
    @GetMapping("/disponibles")
    public List<Movie> getPeliculasDisponibles() {
        List<Movie> disponibles = new ArrayList<>();
        for (Movie m : listadoPeliculas) {
            if (m.isAvailable()) {
                disponibles.add(m);
            }
        }
        return disponibles;
    }
}