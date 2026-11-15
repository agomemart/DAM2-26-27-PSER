package practicaex.panaderia;

public class AppPanaderia {
    public static void main(String[] args) {
        final int NUM_CLIENTES = 3;
        Mostrador mostrador = new Mostrador();
        Panadero panadero = new Panadero(mostrador);
        Cliente[] clientes = new Cliente[NUM_CLIENTES];

        panadero.start();
        for (int i = 0; i < clientes.length; i++) {
            clientes[i] = new Cliente("Cliente " + i, mostrador);
            clientes[i].start();
        }

        for (Cliente c : clientes) {
            try {
                c.join();
            } catch (InterruptedException e) {
            }
        }
        try {
            panadero.join();
        } catch (InterruptedException e) {
        }

        System.out.println("Panadería cerrada. Mostrador: " + mostrador.getBarras());
    }
}
