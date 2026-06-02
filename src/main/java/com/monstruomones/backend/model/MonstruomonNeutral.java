package com.monstruomones.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("NEUTRAL") // Coincide con el texto en tu MySQL
public class MonstruomonNeutral extends com.monstruomones.backend.model.Monstruomon {

    // Constructor para Spring
    public MonstruomonNeutral() {
        super();
    }

    public MonstruomonNeutral(String nombre, int vida, int fuerza, int defensa) {
        super(nombre, com.monstruomones.backend.model.TipoMonstruo.NEUTRAL, vida, fuerza, defensa);
    }
}