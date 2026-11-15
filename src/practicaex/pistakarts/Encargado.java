package practicaex.pistakarts;

import java.util.Random;

public class Encargado extends Thread{
    final Pista pista;
    final Random rnd = new Random();

    public Encargado(Pista pista) {
        this.pista = pista;
    }

    public void run() {
        try {
            Thread.sleep(rnd.nextInt(2000));
        } catch (InterruptedException e) {
        }
        pista.llegaEncargado(this);
    }
}
