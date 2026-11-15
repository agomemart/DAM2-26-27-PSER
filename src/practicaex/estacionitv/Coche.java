package practicaex.estacionitv;

import java.util.Random;

public class Coche extends Thread {
    final Estacion estacion;
    final Random rnd = new Random();
    private boolean repito = false;
    private boolean favorableFinal;

    public Coche(String name, Estacion estacion) {
        super(name);
        this.estacion = estacion;
    }

    public void run() {
        try {
            Thread.sleep(rnd.nextInt(1000));
        } catch (Exception e) {
        }
        estacion.esperarApertura(this);

        for (int intento = 1; intento < 2; intento++) {
            Linea linea = estacion.ocuparLinea(this);
            try {
                Thread.sleep(100 + rnd.nextInt(301));
            } catch (Exception e) {
            }
            boolean favorable = rnd.nextInt(100) < 70;
            estacion.salirLinea(this, linea, favorable);
            favorableFinal = favorable;
            if (favorable) {
                break;
            }
            if (intento == 1) {
                repito = true;
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
            }
        }
    }

    public boolean getRepito() {
        return repito;
    }

    public boolean getFavorableFinal() {
        return favorableFinal;
    }

}
