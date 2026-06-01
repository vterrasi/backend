package com.monstruomones.backend.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("AGUA") // Coincide con el texto en tu MySQL
public class MonstruomonAgua extends com.monstruomones.backend.model.Monstruomon {

    // Constructor para Spring
    public MonstruomonAgua() {
        super();
    }

    public MonstruomonAgua(String nombre, int vida, int fuerza, int defensa) {
        super(nombre, com.monstruomones.backend.model.TipoMonstruo.AGUA, vida, fuerza, defensa);
    }
}