package practicaex.peajeautopista;

import java.util.Random;

public class Operario extends Thread {
    final Peaje peaje;
    Random rnd = new Random();

    public Operario(String name, Peaje peaje) {
        super(name);
        this.peaje = peaje;
    }

    public void run() {
        try {
            Thread.sleep(rnd.nextInt(500));
        } catch (Exception e) {
        }
        peaje.abrirPeaje();
    }
}
