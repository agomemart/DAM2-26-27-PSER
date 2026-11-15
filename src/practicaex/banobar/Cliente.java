package practicaex.banobar;

import java.util.Random;

public class Cliente extends Thread{
    private final Bano bano;
    private final Random rnd = new Random();


    public Cliente(String nombre, Bano bano) {
        super(nombre);
        this.bano = bano;
    }

    public void run() {
        bano.entrar(getName());
        try {
            Thread.sleep(rnd.nextInt(1000));
        } catch (Exception e) {
        }
        bano.salir(getName());
    }
}
