package videoclubMysqlSpring;

import jakarta.persistence.*;

@Entity
@Table(name = "alquileres")
public class Alquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pelicula_id", nullable = false)
    private Pelicula pelicula;

    private double precio;

    // 🌟 ¡NUEVA COLUMNA FISICA PARA EL WORKBENCH! 🌟
    @Column(name = "titulo_pelicula")
    private String tituloPelicula;

    // Constructor vacío obligatorio
    public Alquiler() {
    }

    // Constructor que usamos al alquilar
    public Alquiler(Pelicula pelicula, double precio) {
        this.pelicula = pelicula;
        this.precio = precio;
        this.tituloPelicula = pelicula.getTitulo(); // 👈 Guardamos el texto aquí automáticamente
    }

    // Getters y Setters
    public Long getId() { return id; }
    public Pelicula getPelicula() { return pelicula; }
    public double getPrecio() { return precio; }
    public String getTituloPelicula() { return tituloPelicula; } // 👈 Su botón de lectura

    public void setPelicula(Pelicula pelicula) { this.pelicula = pelicula; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setTituloPelicula(String tituloPelicula) { this.tituloPelicula = tituloPelicula; }
}