package ventacoches;

public class VentaCoches {
    static final int NUM_CLIENTES = 50;
    static final boolean VerDetalle = true;

    public static void main(String[] args) {
        Concesionario concesionario = new Concesionario();
        concesionario.addModelo("León", 10);
        concesionario.addModelo("Ibiza", 7);
        concesionario.addModelo("Ateca", 3);

        Cliente[] clientes = new Cliente[NUM_CLIENTES];
        for (int i = 0; i < NUM_CLIENTES; i++) {
            clientes[i] = new Cliente("cliente" + (i + 1), concesionario);
            clientes[i].start();
        }

        for (Cliente cliente : clientes)
            try { cliente.join(); }
            catch (InterruptedException ex) {}

        System.out.println("\n===== VENTAS =====");
        concesionario.visualizarVentas();
    }
}