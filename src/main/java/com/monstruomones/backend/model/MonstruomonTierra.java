package com.monstruomones.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TIERRA") // Coincide con el texto en tu MySQL
public class MonstruomonTierra extends com.monstruomones.backend.model.Monstruomon {

    // Constructor para Spring
    public MonstruomonTierra() {
        super();
    }

    public MonstruomonTierra(String nombre, int vida, int fuerza, int defensa) {
        super(nombre, com.monstruomones.backend.model.TipoMonstruo.TIERRA, vida, fuerza, defensa);
    }
}