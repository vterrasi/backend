package com.monstruomones.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("FUEGO") // Coincide con el texto en tu MySQL
public class MonstruomonFuego extends com.monstruomones.backend.model.Monstruomon {

    // Constructor para Spring
    public MonstruomonFuego() {
        super();
    }

    public MonstruomonFuego(String nombre, int vida, int fuerza, int defensa) {
        super(nombre, com.monstruomones.backend.model.TipoMonstruo.FUEGO, vida, fuerza, defensa);
    }
}