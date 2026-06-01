package com.monstruomones.backend.services;

import com.monstruomones.backend.model.TipoMonstruo;

public class CalculadoraDanio {
    public static double obtenerMultiplicador(TipoMonstruo tipoAtaque, TipoMonstruo tipoDefensor) {

        if (tipoAtaque == TipoMonstruo.NEUTRAL) {
            return 1.0;
        }

        if (tipoAtaque == tipoDefensor) {
            return 1.0; // Mismo tipo es neutral según tu regla final
        }

        return switch (tipoAtaque) {
            case FUEGO -> (tipoDefensor == TipoMonstruo.TIERRA) ? 2.0 : 0.5;
            case AGUA -> (tipoDefensor == TipoMonstruo.FUEGO) ? 2.0 : 0.5;
            case RAYO -> (tipoDefensor == TipoMonstruo.AGUA) ? 2.0 : 0.5;
            case TIERRA -> (tipoDefensor == TipoMonstruo.RAYO) ? 2.0 : 0.5;
            default -> 1.0;
        };
    }
}