package practicaex.lavanderia;

import java.util.Random;

public class Cliente extends Thread {
    final Lavanderia lavanderia;
    final Random rnd = new Random();

    public Cliente(String name, Lavanderia lavanderia) {
        super(name);
        this.lavanderia = lavanderia;
    }

    public void run() {
        Lavadora l = lavanderia.usarLavadora(this);
        try {
            Thread.sleep(200 + rnd.nextInt(301));
        } catch (Exception e) {
        }
        lavanderia.liberarLavadora(this, l);
    }
}
