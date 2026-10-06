import java.util.Random;

public class Votante extends Thread {
    Urna urna;
    static final int TIEMPO_REFLEXION = 5;
    static Random random = new Random();

    public Votante(Urna urna) {
        this.urna = urna;
    }

    @Override
    public void run() {
        try {

            Thread.sleep(random.nextInt(TIEMPO_REFLEXION));
        } catch (InterruptedException ex) {
        }
        int numeroDePartido = random.nextInt(urna.getNumPartidos());
        urna.vota(numeroDePartido);
    }
}
