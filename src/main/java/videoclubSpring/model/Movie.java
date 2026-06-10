package videoclubSpring.model;

public class Movie {
    private String id;
    private String titulo;
    private String genero;
    private boolean available; // De recursoAlquiler
    private boolean esNovedad; // Reutilizado de Pelicula.java (Control de 3€ / 5€)
    private int duracion;      // Reutilizado de Pelicula.java (Para filtro $maxRT)

    public Movie() {}

    public Movie(String id, String titulo, String genero, boolean available, boolean esNovedad, int duracion) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.available = available;
        this.esNovedad = esNovedad;
        this.duracion = duracion;
    }

    // Getters y Setters de todos los campos...
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public boolean isEsNovedad() { return esNovedad; }
    public void setEsNovedad(boolean esNovedad) { this.esNovedad = esNovedad; }
    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }
}