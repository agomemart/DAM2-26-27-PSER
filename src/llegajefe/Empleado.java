package llegajefe;

import java.util.Random;

public class Empleado extends Thread {
    final int TIEMPO_ESPERA = 3000;
    String nombre;
    Oficina oficina;
    static int contEmpleados = 0;
    static Random rnd = new Random();

    public Empleado(Oficina oficina) {
        this.nombre = "E" + ++contEmpleados;
        this.oficina = oficina;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(rnd.nextInt(3000));
        } catch (InterruptedException ex) {
        }
        oficina.llegaEmpleado(this);
    }
}
