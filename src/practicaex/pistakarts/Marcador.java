package practicaex.pistakarts;

import java.util.ArrayList;

public class Marcador {
    private int totalVueltas = 0;
    private int mejorTiempo = Integer.MAX_VALUE;
    private final ArrayList<String> mejoresPilotos = new ArrayList<>();

    public synchronized void apuntarVuelta(String piloto, int tiempo) {
        totalVueltas++;
        if (tiempo < mejorTiempo) {
            mejorTiempo = tiempo;
            mejoresPilotos.clear();
            mejoresPilotos.add(piloto);
        } else if (tiempo == mejorTiempo) {
            mejoresPilotos.add(piloto);
        }
    }

    public synchronized int getTotalVueltas() {
        return totalVueltas;
    }

    public synchronized int getMejorTiempo() {
        return mejorTiempo;
    }

    public synchronized ArrayList<String> getMejoresPilotos() {
        return mejoresPilotos;
    }
}
