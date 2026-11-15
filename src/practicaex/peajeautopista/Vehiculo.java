package practicaex.peajeautopista;

import java.util.Random;

public class Vehiculo extends Thread {
    final Peaje peaje;
    Random rnd = new Random();
    private final String tipo;
    private final int tarifa;

    public Vehiculo(String name, Peaje peaje) {
        super(name);
        this.peaje = peaje;
        int t = rnd.nextInt(3);
        if (t == 0) {
            tipo = "MOTO";
            tarifa = 2;
        } else if (t == 1) {
            tipo = "COCHE";
            tarifa = 4;
        } else {
            tipo = "CAMION";
            tarifa = 8;
        }
    }

    public String getTipo() {
        return tipo;
    }

    public int getTarifa() {
        return tarifa;
    }

    private int tiempoEnCabina() {
        if (tipo.equals("MOTO"))
            return 50 + rnd.nextInt(51);
        if (tipo.equals("COCHE"))
            return 100 + rnd.nextInt(101);
        return 200 + rnd.nextInt(201);
    }

    public void run() {
        peaje.esperarApertura(this);
        try {
            Thread.sleep(rnd.nextInt(300));
        } catch (Exception e) {
        }
        Cabina c = peaje.llegarPeaje(this);
        try {
            Thread.sleep(tiempoEnCabina());
        } catch (Exception e) {
        }
        peaje.salirLinea(this, c);
    }
}
