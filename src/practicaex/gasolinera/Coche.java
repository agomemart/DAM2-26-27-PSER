package practicaex.gasolinera;

import java.util.Random;

public class Coche extends Thread{
    private final Gasolinera gasolinera;
    private final Random rnd = new Random();
    private final int TIEMPO_ESPERA = 100;

    public Coche(String name, Gasolinera gasolinera) {
        super(name);
        this.gasolinera = gasolinera;
    }
    
    public void run() {
        Surtidor s = gasolinera.ocupar(this);
        try {
            Thread.sleep(rnd.nextInt(TIEMPO_ESPERA) + 1);
        } catch (InterruptedException e) {
        }
        gasolinera.liberar(this, s, rnd.nextInt(TIEMPO_ESPERA) + 1);
    }
}
