package practicaex.lavanderia;

public class MainLavanderia {
    public static void main(String[] args) {
        Lavanderia lavanderia = new Lavanderia();
        Cliente[] clientes = new Cliente[Lavanderia.NUM_CLIENTES];

        for (int i = 0; i < clientes.length; i++) {
            clientes[i] = new Cliente("Cliente " + i, lavanderia);
            clientes[i].start();
        }

        for (Cliente c : clientes) {
            try {
                c.join();
            } catch (InterruptedException e) {
            }
        }

        System.out.println("Recaudación total: " + lavanderia.getTotalRecaudado() + " / " + Lavanderia.NUM_CLIENTES * 3);
        System.out.println("Numero de coladas por lavadora: ");
        lavanderia.getColadasPorLavadora();
        System.out.println("Fin del programa");
    }
}
