package practicaex.pistakarts;

import java.util.Random;

public class Piloto extends Thread {
    final Pista pista;
    final static int NUM_VUELTAS = 3;
    final Random rnd = new Random();
    private final Marcador marcador;

    public Piloto(String name, Pista pista, Marcador marcador) {
        super(name);
        this.pista = pista;
        this.marcador = marcador;
    }

    public void run() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        pista.llegaPiloto(this);
        Kart k = pista.cogerKart(this);
        for (int i = 0; i < NUM_VUELTAS; i++) {
            int tiempoVuelta = 100 + rnd.nextInt(201);
            try {
                Thread.sleep(tiempoVuelta);
            } catch (Exception e) {
            }
            marcador.apuntarVuelta(getName(), tiempoVuelta);
        }
        pista.dejarKart(k);
    }
}
