package practicaex.almacenpaqueteria;

import java.util.Random;

public class Repartidor extends Thread {
    final Cinta cinta;
    private int kg = 0;
    Random rnd = new Random();
    final int PAQUETES_POR_REPARTIDOR = 6;

    public Repartidor(String name, Cinta cinta) {
        super(name);
        this.cinta = cinta;
    }

    public int getKg() {
        return kg;
    }

    public void run() {
        cinta.esperarEncargado();
        for (int i = 0; i < PAQUETES_POR_REPARTIDOR; i++) {
            Paquete p = cinta.repartirPaquete(this);
            kg += p.getPeso();
            try {
                Thread.sleep(100 + rnd.nextInt(200));
            } catch (InterruptedException e) {
            }
        }
    }
}
