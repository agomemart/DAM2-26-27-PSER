package practicaex.banobar;

public class AppBano {
    public static void main(String[] args) {
        final int NUM_CLIENTES = 300;
        Bano bano = new Bano();
        Cliente[] clientes = new Cliente[NUM_CLIENTES];

        for (int i = 0; i < NUM_CLIENTES; i++) {
            clientes[i] = new Cliente("Cliente " + i, bano);
            clientes[i].start();
        }

        for (Cliente c : clientes) {
            try {
                c.join();
            } catch (InterruptedException e) {
            }
        }
    }
}
