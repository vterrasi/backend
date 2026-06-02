package com.monstruomones.backend.model;

import com.monstruomones.backend.services.Atacable;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "monstruomones")
// Esta etiqueta le dice a Spring que use la columna "tipo" de MySQL para saber qué hijo crear
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class Monstruomon implements Atacable {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    // Indicamos que es un Enum guardado como Texto en MySQL
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", insertable = false, updatable = false)
    private TipoMonstruo tipo;

    @Column(name = "vida_maxima", nullable = false)
    private int vidaMaxima;

    // Este campo lo dejamos para la vida actual en el combate (no se guarda en MySQL)
    @Transient
    private int vidaActual;

    @Column(name = "fuerza", nullable = false)
    private int fuerza;

    @Column(name = "defensa", nullable = false)
    private int defensa;

    // LA CONEXIÓN CON LOS ATAQUES: Spring lee la tabla puente automáticamente
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "monstruomones_ataques",
            joinColumns = @JoinColumn(name = "monstruomon_id"),
            inverseJoinColumns = @JoinColumn(name = "ataque_id")
    )
    private List<Ataque> ataques = new ArrayList<>();

    // Constructor obligatorio para Spring
    public Monstruomon() {}

    public Monstruomon(String nombre, TipoMonstruo tipo, int vidaMaxima, int fuerza, int defensa) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.vidaMaxima = vidaMaxima;
        this.vidaActual = vidaMaxima; // Al nacer, su vida actual es la máxima
        this.fuerza = fuerza;
        this.defensa = defensa;
    }

    // --- TU LÓGICA DE COMBATE SE QUEDA EXACTAMENTE IGUAL ---
    @Override
    public int recibirDanio(double danio) {
        int danioFinal = (int) danio;
        this.vidaActual -= danioFinal;
        if (this.vidaActual < 0) {
            this.vidaActual = 0;
        }
        return danioFinal;
    }

    @Override
    public boolean estaVivo() {
        return this.vidaActual > 0;
    }

    // Mét0do especial para cuando empiece un combate, restaurar su vida al máximo desde la BD
    public void inicializarVida() {
        this.vidaActual = this.vidaMaxima;
    }

    // GETTERS Y SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public TipoMonstruo getTipo() { return tipo; }
    public int getVida() { return vidaActual; } // UI lee la vida actual en combate
    public int getVidaMaxima() { return vidaMaxima; }
    public int getFuerza() { return fuerza; }
    public int getDefensa() { return defensa; }
    public List<Ataque> getAtaques() { return ataques; }
}