package apuestas;

import java.util.ArrayList;

public class Porra {
    int dineroAcumulado;
    ArrayList<Apuesta> apuestas = new ArrayList<>();

    public synchronized void addApuesta(Apuesta a) {
        apuestas.add(a);
        dineroAcumulado += a.cantidad;
    }
}
