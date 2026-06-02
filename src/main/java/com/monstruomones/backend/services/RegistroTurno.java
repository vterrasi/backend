package com.monstruomones.backend.services;

public class RegistroTurno {
    private String nombreAtaque;
    private String nombreAtacante;
    private double danioRealizado;

    public RegistroTurno(String nombreAtaque, String nombreAtacante, double danioRealizado) {
        this.nombreAtaque = nombreAtaque;
        this.nombreAtacante = nombreAtacante;
        this.danioRealizado = danioRealizado;
    }

    public String getNombreAtaque() { return nombreAtaque; }
    public String getNombreAtacante() { return nombreAtacante; }
    public double getDanioRealizado() { return danioRealizado; }
}