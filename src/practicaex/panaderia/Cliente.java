package practicaex.panaderia;

import java.util.Random;

public class Cliente extends Thread {
    final int MAX_BARRAS = 5;
    private final Mostrador mostrador;
    Random rnd = new Random();

    public Cliente(String name, Mostrador mostrador) {
        super(name);
        this.mostrador = mostrador;
    }

    public void run() {
        for (int i = 0; i < MAX_BARRAS; i++) {
            mostrador.coger(getName());
            try {
                Thread.sleep(rnd.nextInt(3000));
            } catch (Exception e) {
            }
        }

    }
}
