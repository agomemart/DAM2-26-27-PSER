package ventacoches;

import java.util.ArrayList;
import java.util.Random;

public class Concesionario {
    private final ArrayList<Coche> coches = new ArrayList<>();
    private final Random random = new Random();

    public void addModelo(String modelo, int unidades) {
        for (int i = 1; i <= unidades; i++)
            coches.add(new Coche(modelo + i));
    }

    private boolean quedanCochesALaVenta() {
        for (Coche c : coches)
            if (c.getEstado() != Coche.Estado.VENDIDO)
                return true;
        return false;
    }

    private ArrayList<Coche> cochesLibres() {
        ArrayList<Coche> libres = new ArrayList<>();
        for (Coche c : coches)
            if (c.getEstado() == Coche.Estado.LIBRE)
                libres.add(c);
        return libres;
    }

    public synchronized Coche pedirCita() throws InterruptedException {
        ArrayList<Coche> libres = cochesLibres();
        while (libres.isEmpty()) {
            if (!quedanCochesALaVenta())
                return null;
            wait();
            libres = cochesLibres();
        }
        Coche coche = libres.get(random.nextInt(libres.size()));
        coche.setEstado(Coche.Estado.PROBANDOSE);
        return coche;
    }

    public synchronized void terminarPrueba(Coche coche, Cliente cliente, boolean compra) {
        coche.incrementaVisitas();
        if (compra) {
            coche.setEstado(Coche.Estado.VENDIDO);
            coche.setComprador(cliente);
        } else
            coche.setEstado(Coche.Estado.LIBRE);
        notifyAll();
    }

    public synchronized int leerCartel(Coche coche) {
        return coche.getVisitas();
    }

    public synchronized void visualizarVentas() {
        int vendidos = 0;
        for (Coche c : coches) {
            if (c.getComprador() != null) {
                System.out.println(c.getComprador().getName() + " compró " + c
                        + "  (tras " + c.getVisitas() + " visitas)");
                vendidos++;
            } else
                System.out.println(c + " sin vender");
        }
        System.out.println("\nTotal: " + vendidos + " de " + coches.size() + " coches vendidos.");
    }
}
