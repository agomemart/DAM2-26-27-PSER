import java.util.Random;

public class Votante extends Thread {
    final static int CENSO = 10000;
    final static int NUM_PARTIDOS = 5;
    final static int[] partidos = new int[NUM_PARTIDOS];
    Random rnd = new Random();

    public Votante(String nombre) {
        super(nombre);
    }

    public void run() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
        }
        for (int i = 0; i < CENSO; i++) {
            votar();
        }
    }

    public synchronized void votar() {
        int aleatorio = rnd.nextInt(5);
        partidos[aleatorio]++;
    }

    public static void main(String[] args) throws InterruptedException {
        Votante[] votantes = new Votante[CENSO];

        for (int i = 0; i < CENSO; i++) {
            votantes[i] = new Votante("Votante " + (i + 1));
            votantes[i].start();
        }

        for (Votante v : votantes) {
            v.join();
        }

        System.out.println("RESULTADOS");
        int maxVotos = 0;
        int indiceMasVotado = 0;
        for (int i = 0; i < partidos.length; i++) {
            System.out.println("Partido " + i + ": " + partidos[i] + " votos");
            if (maxVotos > partidos[i]) {
                maxVotos = partidos[i];
                indiceMasVotado = i;
            }
        }

        System.out.println("GANADOR: " + "Partido " + indiceMasVotado);
    }
}
