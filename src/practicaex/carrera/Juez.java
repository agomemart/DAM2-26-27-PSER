package practicaex.carrera;

import java.util.Random;

public class Juez extends Thread{
    private final Carrera carrera;
    Random rnd = new Random();

    public Juez(Carrera carrera) {
        this.carrera = carrera;
    }
    
    public void run() {
        try {
            Thread.sleep(rnd.nextInt(1000));
        } catch (Exception e) {
        }
        carrera.disparar();
    }
}
