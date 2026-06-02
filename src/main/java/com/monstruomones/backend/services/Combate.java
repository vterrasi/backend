package com.monstruomones.backend.services; // Tu carpeta en singular

import com.monstruomones.backend.model.Ataque;
import com.monstruomones.backend.model.Monstruomon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // Convertido en componente oficial de Spring
public class Combate {

    private final com.monstruomones.backend.services.Arbitro arbitro;
    private final GestorEstadisticas gestorEstadisticas;

    // Listas de combatientes en memoria RAM durante la partida
    private List<Monstruomon> equipoJugador;
    private List<Monstruomon> equipoRival;
    private Monstruomon activoJugador;
    private Monstruomon activoRival;
    private List<RegistroTurno> historialDelCombate;

    // Constructor que inyecta los sub-servicios necesarios
    public Combate(com.monstruomones.backend.services.Arbitro arbitro, GestorEstadisticas gestorEstadisticas) {
        this.arbitro = arbitro;
        this.gestorEstadisticas = gestorEstadisticas;
    }

    // Prepara el combate trayendo los monstruos cargados desde la base de datos MySQL
    public void prepararNuevaPartida(List<Monstruomon> jugador, List<Monstruomon> rival) {
        this.equipoJugador = jugador;
        this.equipoRival = rival;
        this.historialDelCombate = new ArrayList<>();

        // Inicializamos las vidas al máximo para empezar a jugar
        this.equipoJugador.forEach(Monstruomon::inicializarVida);
        this.equipoRival.forEach(Monstruomon::inicializarVida);

        // El primer bicho vivo de cada lista sale a la arena
        this.activoJugador = jugador.stream().filter(Monstruomon::estaVivo).findFirst().orElse(null);
        this.activoRival = rival.stream().filter(Monstruomon::estaVivo).findFirst().orElse(null);
    }

    // JUEGA UN TURNO COMPLETO: Tu ataque + la respuesta automática del rival
    public List<RegistroTurno> jugarTurno(Ataque ataqueSeleccionado) {
        List<RegistroTurno> eventosDeEsteTurno = new ArrayList<>();

        if (activoJugador == null || !activoJugador.estaVivo() || activoRival == null || !activoRival.estaVivo()) {
            return eventosDeEsteTurno; // Si alguien está muerto, no se puede atacar
        }

        // 1. ATACAS TÚ: El árbitro calcula tu golpe
        RegistroTurno turnoJugador = arbitro.ejecutarAccion(activoJugador, ataqueSeleccionado, activoRival);
        eventosDeEsteTurno.add(turnoJugador);
        historialDelCombate.add(turnoJugador);

        // 2. ¿Sigue vivo el rival? Responde el bot
        if (activoRival.estaVivo()) {
            RegistroTurno turnoBot = arbitro.turnoRival(activoRival, activoJugador);
            eventosDeEsteTurno.add(turnoBot);
            historialDelCombate.add(turnoBot);
        } else {
            // Si el rival ha muerto, intentamos sacar al siguiente de su lista de MySQL
            activoRival = equipoRival.stream().filter(Monstruomon::estaVivo).findFirst().orElse(null);
        }

        // 3. ¿Ha muerto tu bicho tras el golpe del bot?
        if (!activoJugador.estaVivo()) {
            activoJugador = equipoJugador.stream().filter(Monstruomon::estaVivo).findFirst().orElse(null);
        }

        // 4. Si el combate ha terminado por completo, registramos las estadísticas globales
        if (comprobarFinalCombate()) {
            gestorEstadisticas.registrarPartida(historialDelCombate);
        }

        return eventosDeEsteTurno; // Devolvemos lo que ha pasado para pintarlo en la web
    }

    public boolean comprobarFinalCombate() {
        return !arbitro.equipoTieneVida(equipoJugador) || !arbitro.equipoTieneVida(equipoRival);
    }

    // Permite al usuario cambiar de bicho de forma voluntaria a través de la web
    public boolean cambiarMonstruoActivo(int idMonstruo) {
        Monstruomon elegido = equipoJugador.stream()
                .filter(m -> m.getId() == idMonstruo && m.estaVivo())
                .findFirst()
                .orElse(null);

        if (elegido != null && elegido != activoJugador) {
            this.activoJugador = elegido;
            return true;
        }
        return false;
    }

    // Getters para que el controlador cotillee el estado actual de la partida
    public Monstruomon getActivoJugador() { return activoJugador; }
    public Monstruomon getActivoRival() { return activoRival; }
    public List<Monstruomon> getEquipoJugador() { return equipoJugador; }
    public List<Monstruomon> getEquipoRival() { return equipoRival; }
}