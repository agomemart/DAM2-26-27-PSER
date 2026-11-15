package practicaex.gasolinera;

public class AppGasolinera {
    static final int NUM_COCHES = 40;
    public static void main(String[] args) {
        Gasolinera gasolinera = new Gasolinera();
        Coche[] coches = new Coche[NUM_COCHES];

        for (int i = 0; i < coches.length; i++) {
            coches[i] = new Coche("Coche " + i, gasolinera);
            coches[i].start();
        }

        for (Coche c : coches) {
            try {
                c.join();
            } catch (InterruptedException e) {
            }
        }
    }
}