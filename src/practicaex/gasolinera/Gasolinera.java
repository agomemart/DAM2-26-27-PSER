package practicaex.gasolinera;

public class Gasolinera {
    static final int NUM_SURTIDORES = 4;
    private final Surtidor[] surtidores = new Surtidor[NUM_SURTIDORES];
    private int litrosTotales;

    public Gasolinera() {
        for (int i = 0; i < surtidores.length; i++) {
            surtidores[i] = new Surtidor(i + 1);
        }
    }

    private Surtidor buscarLibre() {
        for (Surtidor s : surtidores) {
            if (s.estaLibre()) {
                return s;
            }
        }
        return null;
    }

    public synchronized Surtidor ocupar(Coche c) {
        Surtidor libre;
        while ((libre = buscarLibre()) == null) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        libre.ocupar(c);
        System.out.println(c.getName() + " ENTRA " + estado());
        return libre;
    }

    public synchronized void liberar(Coche c, Surtidor s, int litros) {
        s.liberar();
        litrosTotales += litros;
        System.out.println(c.getName() + "SALE (" + litros + " l) " + estado());
        notifyAll();
    }

    public synchronized int getLitrosTotales() {
        return litrosTotales;
    }

    private String estado() {
        StringBuilder sb = new StringBuilder();
        for (Surtidor s : surtidores) {
            sb.append(s).append(" ");
        }
        return sb.toString();
    }

}
