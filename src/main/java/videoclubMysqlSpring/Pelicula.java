package videoclubMysqlSpring;

import jakarta.persistence.*;

@Entity // 1. Le dice a Spring: "Crea una tabla en la base de datos con este molde"
@Table(name = "peliculas")
public class Pelicula {
    @Id // 2. Toda tabla necesita una llave primaria (un ID único para no perderse)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Se numera solo: 1, 2, 3...
    private Long id;

    private String titulo;
    private String genero;
    private int stock;
    private boolean esNovedad;

    // --- ¡NUESTRO NUEVO INVITADO! ---
    @Column(name = "duracion_minutos")
    private int duracion; // Aquí guardaremos los minutos para el filtro $maxRT

    // Constructor vacío obligatorio para que Spring no llore
    public Pelicula() {
    }

    // Constructor normal que usaremos nosotros
    public Pelicula(String titulo, String genero, int stock, boolean esNovedad, int duracion) {
        this.titulo = titulo;
        this.genero = genero;
        this.stock = stock;
        this.esNovedad = esNovedad;
        this.duracion = duracion;
    }

    // Getters y Setters (los botones para leer y cambiar los datos)
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
    public int getStock() { return stock; }
    public boolean esNovedad() { return esNovedad; }
    public int getDuracion() { return duracion; } // <- Clave para el filtro

    public void reducirStock() { this.stock--; }
    public void aumentarStock() { this.stock++; }
    public double getPrecio() { return esNovedad ? 5.0 : 3.0; } //[cite: 10]

}
