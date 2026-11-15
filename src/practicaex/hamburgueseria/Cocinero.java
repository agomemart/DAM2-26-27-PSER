package practicaex.hamburgueseria;

import java.util.Random;

public class Cocinero extends Thread{
    final Bandeja bandeja;
    final static int HAMBURGUESAS_POR_COCINERO = 15;
    Random rnd = new Random();

    public Cocinero(String name, Bandeja bandeja) {
        super(name);
        this.bandeja = bandeja;
    }

    public void run() {
        for (int i = 0; i < HAMBURGUESAS_POR_COCINERO; i++) {
            try {
                Thread.sleep(50 + rnd.nextInt(250));
                bandeja.dejarHamburguesa(this);
            } catch (Exception e) {
            }
        }
    }
}
