package practicaex.panaderia;

import java.util.Random;

public class Panadero extends Thread {
    private final Mostrador mostrador;
    final int NUM_BARRAS = 15;
    Random rnd = new Random();

    public Panadero(Mostrador mostrador) {
        this.mostrador = mostrador;
    }

    public void run() {
        for (int i = 0; i < NUM_BARRAS; i++) {
            try {
                Thread.sleep(rnd.nextInt(100));
            } catch (Exception e) {
            }
            mostrador.dejar();
        }
    }
}
