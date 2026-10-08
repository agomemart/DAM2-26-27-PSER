public class MainAparcamiento {
    static final int NUM_PLAZAS = 10;
    static final int NUM_CONDUCTORES = 50;

    public static void main(String[] args) {
        Aparcamiento aparcamiento = new Aparcamiento(NUM_PLAZAS);
        Conductor[] conductores = new Conductor[NUM_CONDUCTORES];

        for (int i = 1; i <= conductores.length; i++) {
            conductores[i] = new Conductor(i, aparcamiento);
            conductores[i].start();
        }

        for (int i = 1; i <= conductores.length; i++) {
            try {
                conductores[i].join();
            } catch (InterruptedException e) {
            }
        }
    }
}
