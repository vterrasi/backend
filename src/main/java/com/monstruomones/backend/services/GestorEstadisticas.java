package com.monstruomones.backend.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GestorEstadisticas {

    private static int totalCombates = 0;
    private static int totalTurnosGlobal = 0;
    private static int maxTurnosEnUnCombate = 0;
    private static double danioTotalAcumulado = 0;
    private static double mayorDanioEnUnGolpe = 0;
    private static String ataqueMasPoderoso = "";
    private static final Map<String, Integer> contadorGlobalAtaques = new HashMap<>();

    public void registrarPartida(List<RegistroTurno> historial) {
        totalCombates++;
        int turnosDeEsteCombate = historial.size();
        totalTurnosGlobal += turnosDeEsteCombate;

        if (turnosDeEsteCombate > maxTurnosEnUnCombate) {
            maxTurnosEnUnCombate = turnosDeEsteCombate;
        }

        for (RegistroTurno turno : historial) {
            double danio = turno.getDanioRealizado();
            danioTotalAcumulado += danio;

            if (danio > mayorDanioEnUnGolpe) {
                mayorDanioEnUnGolpe = danio;
                ataqueMasPoderoso = turno.getNombreAtaque() + " (por " + turno.getNombreAtacante() + ")";
            }

            contadorGlobalAtaques.put(turno.getNombreAtaque(), contadorGlobalAtaques.getOrDefault(turno.getNombreAtaque(), 0) + 1);
        }
    }

    public int getTotalCombates() { return totalCombates; }
    public int getTotalTurnosGlobal() { return totalTurnosGlobal; }
    public int getMaxTurnosEnUnCombate() { return maxTurnosEnUnCombate; }
    public double getDanioTotalAcumulado() { return danioTotalAcumulado; }
    public double getMayorDanioEnUnGolpe() { return mayorDanioEnUnGolpe; }
    public String getAtaqueMasPoderoso() { return ataqueMasPoderoso; }

    public String getAtaqueMasUsado() {
        if (contadorGlobalAtaques.isEmpty()) return "Ninguno";
        String masUsado = "Ninguno";
        int maxUsos = 0;
        for (Map.Entry<String, Integer> entry : contadorGlobalAtaques.entrySet()) {
            if (entry.getValue() > maxUsos) {
                maxUsos = entry.getValue();
                masUsado = entry.getKey();
            }
        }
        return masUsado;
    }
}