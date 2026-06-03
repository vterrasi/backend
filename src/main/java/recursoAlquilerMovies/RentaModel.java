package recursoAlquilerMovies;

import java.util.List;

public class RentaModel {
    private String id;
    private List<String> movies; // Guarda los IDs de las películas alquiladas

    // Constructor vacío
    public RentaModel() {
    }

    // Constructor con parámetros
    public RentaModel(String id, List<String> movies) {
        this.id = id;
        this.movies = movies;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public List<String> getMovies() { return movies; }
    public void setMovies(List<String> movies) { this.movies = movies; }
}