package conversacion;

import java.util.Random;

public class Hilo extends Thread {
    static final int TIEMPO_PENSAR = 2000;
    private final Random rnd = new Random();
    private final Conversacion conversacion;
    private final int id;

    public Hilo(int id, Conversacion conversacion) {
        super("Hilo " + (id + 1));
        this.id = id;
        this.conversacion = conversacion;
    }

    @Override
    public void run() {
        try {
            boolean seguir = true;
            while (seguir) {
                Thread.sleep(rnd.nextInt(TIEMPO_PENSAR));
                seguir = conversacion.decir(id, getName());
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
