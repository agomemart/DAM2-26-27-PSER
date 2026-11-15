package practicaex.hamburgueseria;

import java.util.Random;

public class Cliente extends Thread {
    final Bandeja bandeja;
    final static int HAMBURGUESAS_POR_CLEINTE = 3;
    final Caja caja;
    final Random rnd = new Random();
    private int gastado = 0;

    public Cliente(String name, Bandeja bandeja, Caja caja) {
        super(name);
        this.bandeja = bandeja;
        this.caja = caja;
    }

    public void run() {
        for (int i = 0; i < HAMBURGUESAS_POR_CLEINTE; i++) {
            bandeja.cogerHamburguesa(this);
            int precio = 5 + rnd.nextInt(6);
            caja.registrarVenta(this, precio);
            gastado += precio;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }

        }

    }

    public int getGastado() {
        return gastado;
    }

}
