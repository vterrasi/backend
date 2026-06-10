package videoclubSpring.model;

import java.util.List;

public class RentaModel {
    private String id;
    private List<String> movies;  // IDs de las películas (recursoAlquiler)
    private Double precioTotal;   // Heredado de la lógica de negocio de Alquiler.java
    private String usuarioId;     // Extra de Interoperabilidad (Ficha de Usuario)

    public RentaModel() {}

    public RentaModel(String id, List<String> movies, Double precioTotal, String usuarioId) {
        this.id = id;
        this.movies = movies;
        this.precioTotal = precioTotal;
        this.usuarioId = usuarioId;
    }

    // Getters y Setters...
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public List<String> getMovies() { return movies; }
    public void setMovies(List<String> movies) { this.movies = movies; }
    public Double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(Double precioTotal) { this.precioTotal = precioTotal; }
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
}