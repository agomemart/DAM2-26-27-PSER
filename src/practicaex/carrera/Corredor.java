package practicaex.carrera;

import java.util.Random;

public class Corredor extends Thread{
    private final Carrera carrera;
    Random rnd = new Random();

    public Corredor(String name, Carrera carrera) {
        super(name);
        this.carrera = carrera;
    }

    public void run() {
        try {
            Thread.sleep(rnd.nextInt(1000));
        } catch (Exception e) {
        }
        carrera.esperarSalida(getName());
    }
}
