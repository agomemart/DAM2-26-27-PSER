package practicaex.estacionitv;

import java.util.ArrayList;


public class Estacion {
    static final int PRECIO = 40;
    final int NUM_INSPECTORES = 3;
    private final Linea[] lineas = new Linea[NUM_INSPECTORES];
    private int llegaron = 0;
    private int inspecciones = 0, favorables = 0, desfavorables = 0, recaudacion = 0;
    private final int[] porLinea = new int[NUM_INSPECTORES];

    public Estacion() {
        for (int i = 0; i < lineas.length; i++) {
            lineas[i] = new Linea(i);
        }
    }

    public synchronized void llegaInspector(Inspector i) {
        llegaron++;
        System.out.println(i.getName() + " llega (" + llegaron + "/" + NUM_INSPECTORES + ")");
        if (llegaron == NUM_INSPECTORES) {
            System.out.println("Abriendo estación ITV");
        }
        notifyAll();
        while (llegaron < NUM_INSPECTORES) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }

    public synchronized void esperarApertura(Coche c) {
        while (llegaron < NUM_INSPECTORES)
            try {
                wait();
            } catch (InterruptedException e) {
            }
    }

    private Linea buscarLibre() {
        for (Linea l : lineas) {
            if (l.estaLibre()) {
                return l;
            }
        }
        return null;
    }

    public synchronized Linea ocuparLinea(Coche c) {
        Linea linea;
        while ((linea = buscarLibre()) == null) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        linea.ocupar(c);
        System.out.println(c.getName() + " ENTRA en línea " + linea.getNumLinea());
        return linea;
    }

    public synchronized void salirLinea(Coche c, Linea l, boolean favorable) {
        l.liberar();
        System.out.println(
                c.getName() + " SALE de línea " + l.getNumLinea() + (favorable ? " FAVORABLE" : " DESFAVORABLE"));
        notifyAll();

        inspecciones++;
        recaudacion += 40;
        if (favorable)
            favorables++;
        else
            desfavorables++;
        porLinea[l.getNumLinea()]++;
    }

    public String estado() {
        StringBuilder sb = new StringBuilder();
        for (Linea l : lineas)
            sb.append(l).append(" ");
        return sb.toString();
    }

    public synchronized int getInspecciones() {
        return inspecciones;
    }

    public synchronized int getFavorables() {
        return favorables;
    }

    public synchronized int getDesfavorables() {
        return desfavorables;
    }

    public synchronized int getRecaudacion() {
        return recaudacion;
    }

    public synchronized int getInspeccionesLinea(int i) {
        return porLinea[i];
    }

    public synchronized ArrayList<Integer> getLineasMasTrabajo() {
        ArrayList<Integer> mejores = new ArrayList<>();
        int max = 0;
        for (int i = 0; i < porLinea.length; i++) {
            if (porLinea[i] > max) {
                max = porLinea[i];
                mejores.clear();
                mejores.add(i + 1);
            } else if (porLinea[i] == max) {
                mejores.add(i + 1);
            }
        }
        return mejores;
    }
}
