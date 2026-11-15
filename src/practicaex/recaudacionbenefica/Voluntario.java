package practicaex.recaudacionbenefica;

import java.util.Random;

public class Voluntario extends Thread{
    static final int NUM_DONACIONES = 50;
    private final Hucha hucha;
    private final Random rnd = new Random();
    private int recaudado = 0;
    final int TIEMPO_ESPERA = 10;
    final int MAX_DINERO_DONADO = 20;

    
    public Voluntario(String name, Hucha hucha) {
        super(name);
        this.hucha = hucha;
    }

    public int getRecaudado() {
        return recaudado;
    }

    public void run() {
        for (int i = 0; i < NUM_DONACIONES; i++) {
            try {
                Thread.sleep(TIEMPO_ESPERA);
            } catch (InterruptedException e) {
            }
            int dinero = rnd.nextInt(MAX_DINERO_DONADO) + 1;
            hucha.donar(dinero);
            recaudado += dinero;
        }
    }

}
