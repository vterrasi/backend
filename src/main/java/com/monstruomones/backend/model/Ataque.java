package com.monstruomones.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ataques")
public class Ataque {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoMonstruo tipo;

    // Corregido: cambiamos 'description' por 'descripcion' para que coincida con MySQL
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "danio_base", nullable = false)
    private int danioBase;

    // Esto mapea con los usos máximos que vienen de la base de datos
    @Column(name = "usos_maximos", nullable = false)
    private int usosMaximos;

    // Esta variable NO se guarda en MySQL, cambia libremente en el combate
    @Transient
    private int usosRestantes;

    // 1. CONSTRUCTOR VACÍO (¡El que te pedía Spring a gritos!)
    public Ataque() {
    }

    // 2. TU CONSTRUCTOR DE SIEMPRE (Para seguir usándolo si hace falta)
    public Ataque(String nombre, TipoMonstruo tipo, String descripcion, int danioBase, int usosMaximos) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.danioBase = danioBase;
        this.usosMaximos = usosMaximos;
        this.usosRestantes = usosMaximos;
    }

    // --- TU LÓGICA DE JUEGO DE SIEMPRE ---
    public boolean puedeUsarse() {
        return usosRestantes == -1 || usosRestantes > 0;
    }

    public void reducirUso() {
        if (usosRestantes > 0) { usosRestantes--; }
    }

    public void restaurarUsos() {
        this.usosRestantes = this.usosMaximos;
    }

    // --- GETTERS Y SETTERS COMPLETOS PARA SPRING ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public TipoMonstruo getTipo() { return tipo; }
    public void setTipo(TipoMonstruo tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getDanioBase() { return danioBase; }
    public void setDanioBase(int danioBase) { this.danioBase = danioBase; }

    public int getUsosMaximos() { return usosMaximos; }
    public void setUsosMaximos(int usosMaximos) { this.usosMaximos = usosMaximos; }

    public int getUsosRestantes() { return usosRestantes; }
    public void setUsosRestantes(int usosRestantes) { this.usosRestantes = usosRestantes; }
}