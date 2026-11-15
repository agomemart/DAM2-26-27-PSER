package practicaex.estacionitv;

import java.util.Random;

public class Inspector extends Thread{
    final Estacion estacion;
    final Random rnd = new Random();

    public Inspector(String name, Estacion estacion) {
        super(name);
        this.estacion = estacion;
    }

    public void run() {
        try {
            Thread.sleep(rnd.nextInt(300));
        } catch (Exception e) {
        }
        estacion.llegaInspector(this);
    }
}
