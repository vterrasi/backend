package com.monstruomones.backend.services; // Tu carpeta en singular

import com.monstruomones.backend.model.Ataque;
import com.monstruomones.backend.model.Monstruomon;
import com.monstruomones.backend.model.TipoMonstruo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // <-- Esto le encanta a Spring
public class Arbitro {

    public boolean equipoTieneVida(List<Monstruomon> equipo) {
        return equipo.stream().anyMatch(Monstruomon::estaVivo);
    }

    public RegistroTurno ejecutarAccion(Monstruomon atacante, Ataque ataque, Monstruomon defensor) {
        int danioFinal = 0;
        int danioBase = atacante.getFuerza() + ataque.getDanioBase();

        // Regla 1: Atacante Neutral
        if (atacante.getTipo() == TipoMonstruo.NEUTRAL) {
            danioFinal = danioBase - defensor.getDefensa();
        }
        // Regla 2: Ataque elemental
        else {
            double mult = CalculadoraDanio.obtenerMultiplicador(ataque.getTipo(), defensor.getTipo());
            danioFinal = (int)(danioBase * mult) - defensor.getDefensa();
        }

        if (danioFinal < 0) {
            danioFinal = 0;
        }

        int danioSufrido = defensor.recibirDanio(danioFinal);
        ataque.reducirUso();

        // Devolvemos el reporte del turno listo para la web
        return new RegistroTurno(ataque.getNombre(), atacante.getNombre(), danioSufrido);
    }

    public RegistroTurno turnoRival(Monstruomon rival, Monstruomon activoJugador) {
        Ataque ataqueBot = rival.getAtaques().stream()
                .filter(Ataque::puedeUsarse)
                .findFirst()
                .orElse(null);

        if (ataqueBot != null) {
            return ejecutarAccion(rival, ataqueBot, activoJugador);
        }
        return new RegistroTurno("Ninguno", rival.getNombre(), 0);
    }
}