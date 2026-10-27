package apuestas;

import java.util.Random;

public class Empleado extends Thread{
    Porra porra;
    static final int TIEMPO_REFLEXION = 300;
    final int NUM_VUELTAS = 5;
    final int MAX_GOLES = 4;
    static Random rnd = new Random();

    public Empleado(String nombre, Porra porra) {
        super(nombre);
        this.porra = porra;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < NUM_VUELTAS; i++) {
                Thread.sleep(rnd.nextInt(TIEMPO_REFLEXION) + 100);
                int numGolesDM2 = rnd.nextInt(MAX_GOLES + 1);
                int numGolesPRF = rnd.nextInt(MAX_GOLES + 1);
                Apuesta a = new Apuesta(getName(), numGolesDM2, numGolesPRF, 1);
                porra.addApuesta(a);
            }
        } catch (InterruptedException ex) {
        }
        
    }
}
