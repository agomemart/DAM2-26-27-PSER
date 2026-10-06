public class Elecciones extends Thread {

    static final int NUM_HABITANTES = 10000;
    static String[] nombresPartidos = { "PP", "BNG", "PSOE", "VOX", "FrenteAmplio" };

    public static void main(String[] args) {
        Urna urna = new Urna(nombresPartidos);

        Votante[] votantes = new Votante[NUM_HABITANTES];
        for (int i = 0; i < NUM_HABITANTES; i++) {
            votantes[i] = new Votante(urna);
            votantes[i].start();
        }

        for (Votante votante : votantes)
            try {
                votante.join();
            } catch (InterruptedException ex) {
            }

        System.out.println(urna);
        urna.visualizarResultados();
    }
}