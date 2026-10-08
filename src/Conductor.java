import java.util.Random;

public class Conductor extends Thread {

    private final int numero;
    private final Aparcamiento aparcamiento;
    private static final Random rnd = new Random();

    public Conductor(int numero, Aparcamiento aparcamiento) {
        this.numero = numero;
        this.aparcamiento = aparcamiento;
    }

    @Override
    public void run() {
        try {
            int plaza = aparcamiento.aparcar(numero);
            Thread.sleep(1000 + rnd.nextInt(4001));
            aparcamiento.salir(numero, plaza);
        } catch (InterruptedException e) {
        }
    }
}
