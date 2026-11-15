package practicaex.hamburgueseria;

public class MainHamburgueseria {
    public static void main(String[] args) {
        final int NUM_COCINEROS = 2;
        final int NUM_CLIENTES = 10;
        Cocinero[] cocineros = new Cocinero[NUM_COCINEROS];
        Cliente[] clientes = new Cliente[NUM_CLIENTES];
        Bandeja bandeja = new Bandeja();
        Caja caja = new Caja();

        for (int i = 0; i < cocineros.length; i++) {
            cocineros[i] = new Cocinero("Cocinero " + i, bandeja);
            cocineros[i].start();
        }

        for (int i = 0; i < clientes.length; i++) {
            clientes[i] = new Cliente("Cliente " + i, bandeja, caja);
            clientes[i].start();
        }

        for (Cocinero c : cocineros) {
            try {
                c.join();
            } catch (InterruptedException e) {
            }
        }

        int gastoTotalClientes = 0;
        for (Cliente c : clientes) {
            try {
                c.join();
            } catch (InterruptedException e) {
            }
            gastoTotalClientes += c.getGastado();
        }

        if (caja.getNumVentas() == NUM_CLIENTES * Cliente.HAMBURGUESAS_POR_CLEINTE) {
            System.out.println("Número de ventas: " + caja.getNumVentas());
        }
        if (caja.getRecaudacionTotal() == gastoTotalClientes) {
            System.out.println("Recaudación total: " + caja.getRecaudacionTotal());
        }
        
        System.out.println("Cliente que más gastó: " + caja.getMejoresClientes().getFirst() + "(" + caja.getGastado(caja.getMejoresClientes().getFirst()) + ")");
        System.out.println("Hamburguesas en bandeja: " + bandeja.getHamburguesas());
        System.out.println("Fin del programa");
    }
}
