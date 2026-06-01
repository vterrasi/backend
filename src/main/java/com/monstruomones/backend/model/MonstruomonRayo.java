package com.monstruomones.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RAYO") // Coincide con el texto en tu MySQL
public class MonstruomonRayo extends com.monstruomones.backend.model.Monstruomon {

    // Constructor para Spring
    public MonstruomonRayo() {
        super();
    }

    public MonstruomonRayo(String nombre, int vida, int fuerza, int defensa) {
        super(nombre, com.monstruomones.backend.model.TipoMonstruo.RAYO, vida, fuerza, defensa);
    }
}