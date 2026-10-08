import java.util.Random;

public class Conductor extends Thread {
    final int NUM_CONDUCTORES = 50;
    final Random rnd = new Random();

    String nombre;

    public Conductor(String nombre) {
        this.nombre = nombre;
    }


    @Override 
    public void run() {
        try {
            Thread.sleep(rnd.nextInt(6));
        } catch (InterruptedException e) {
        }

    }
}
