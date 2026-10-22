package llegajefe;

import java.util.Random;

public class Jefe extends Thread {
    final int TIEMPO_ESPERA = 3000;
    String nombre;
    Oficina oficina;
    static int contJefe = 0;
    static Random rnd = new Random();

    public Jefe(Oficina oficina) {
        this.nombre = "Jefe" + ++contJefe;
        this.oficina = oficina;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(TIEMPO_ESPERA);
        } catch (InterruptedException e) {
        }
        oficina.llegaElJefe(this);
    }
}
