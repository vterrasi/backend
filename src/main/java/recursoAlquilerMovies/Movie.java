package recursoAlquilerMovies;

public class Movie {
    private String id;
    private String titulo;
    private String genero;
    private boolean available; // El nuevo campo que nos pide el enunciado

    // Constructor vacío (obligatorio para que Spring maneje JSON)
    public Movie() {
    }

    // Constructor con parámetros
    public Movie(String id, String titulo, String genero, boolean available) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.available = available;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}