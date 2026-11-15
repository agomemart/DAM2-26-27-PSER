package practicaex.partidadados;

import java.util.ArrayList;
import java.util.HashMap;

public class Marcador {
    private int totalTiradas = 0;
    private HashMap<String, Integer> puntosJugador = new HashMap<>();

    public synchronized void registrarPuntos(Jugador j, int puntos) {
        totalTiradas++;
        int puntosAnteriores = puntosJugador.getOrDefault(j.getName(), 0);
        puntosJugador.put(j.getName(), puntosAnteriores + puntos);
    }

    public synchronized int getTotalTiradas() {
        return totalTiradas;
    }

    public synchronized ArrayList<String> getJugadorMasPuntos() {
        ArrayList<String> mejores = new ArrayList<>();
        int max = 0;
        for (String nombre : puntosJugador.keySet()) {
            int puntos = puntosJugador.get(nombre);
            if (puntos > max) {
                max = puntos;
                mejores.clear();
                mejores.add(nombre);
            } else if (puntos == max) {
                mejores.add(nombre);
            }
        }

        return mejores;
    }
}
