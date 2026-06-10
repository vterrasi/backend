package videoclubSpring.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String id;
    private String nombre;
    private List<String> alquileresIds = new ArrayList<>(); // Almacena IDs de RentaModel

    public Usuario() {}

    public Usuario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public List<String> getAlquileresIds() { return alquileresIds; }
    public void setAlquileresIds(List<String> alquileresIds) { this.alquileresIds = alquileresIds; }
}