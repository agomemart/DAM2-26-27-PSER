package practicaex.almacenpaqueteria;

import java.util.Random;

public class Camion extends Thread{
    final Cinta cinta;
    Random rnd = new Random();
    static final int PAQUETES_POR_CAMION = 10;
    
    public Camion(String name, Cinta cinta) {
        super(name);
        this.cinta = cinta;
    }

    public void run() {
        cinta.esperarEncargado();
        for (int i = 0; i < PAQUETES_POR_CAMION; i++) {
            try {
            Thread.sleep(50 + rnd.nextInt(150));
            } catch (InterruptedException e) {
            }
            cinta.descargarPaquete(this);
        }
    }
}
