package practicaex.almacenpaqueteria;

import java.util.Random;

public class Encargado extends Thread{
    final Cinta cinta;
    Random rnd = new Random();
    
    public Encargado(String name, Cinta cinta) {
        super(name);
        this.cinta = cinta;
    }
    
    public void run() {
        try {
            Thread.sleep(rnd.nextInt(500));
        } catch (InterruptedException e) {
        }
        cinta.llegaEncargado(this);
    }
}
