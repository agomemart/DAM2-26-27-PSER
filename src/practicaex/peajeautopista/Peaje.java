package practicaex.peajeautopista;

import java.util.ArrayList;

public class Peaje {
    final int NUM_OPERARIOS = 3;
    private int numOperarios = 0;
    private final Cabina[] cabinas = new Cabina[NUM_OPERARIOS];

    private int motos = 0, coches = 0, camiones = 0;
    private int recaudacion = 0;
    private final int[] porCabina;

    public Peaje() {
        porCabina = new int[NUM_OPERARIOS];
        for (int i = 0; i < cabinas.length; i++) {
            cabinas[i] = new Cabina("Cabina " + i, null);
        }
    }

    public synchronized void abrirPeaje() {
        numOperarios++;
        if (numOperarios == NUM_OPERARIOS) {
            System.out.println("Abriendo peaje");
        }
        notifyAll();
    }

    public synchronized void esperarApertura(Vehiculo v) {
        while (numOperarios < NUM_OPERARIOS) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

    }

    private Cabina buscarLibre() {
        for (Cabina c : cabinas) {
            if (c.estaLibre()) {
                return c;
            }
        }
        return null;
    }

    public synchronized Cabina llegarPeaje(Vehiculo v) {
        Cabina cabina;
        while ((cabina = buscarLibre()) == null) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        cabina.ocupar(v);
        System.out.println(v.getName() + " ENTRA en " + cabina.getNombre());
        return cabina;
    }

    public synchronized void salirLinea(Vehiculo v, Cabina c) {
        c.liberar();
        if (v.getTipo().equals("MOTO")) motos++;
        else if (v.getTipo().equals("COCHE")) coches++;
        else camiones++;
        recaudacion += v.getTarifa();
        porCabina[Integer.valueOf(c.getNombre().substring(c.getNombre().length() - 1))] += v.getTarifa();
        System.out.println(v.getName() + " (" + v.getTipo() + ") paga " + v.getTarifa() + " € y sale de "
                + c.getNombre() + "   " + estado());
        notifyAll();
    }

    public String estado() {
        StringBuilder sb = new StringBuilder();
        for (Cabina c : cabinas)
            sb.append(c).append(" ");
        return sb.toString();
    }

    public synchronized int getMotos() {
        return motos;
    }

    public synchronized int getCoches() {
        return coches;
    }

    public synchronized int getCamiones() {
        return camiones;
    }

    public synchronized int getRecaudacion() {
        return recaudacion;
    }

    public synchronized int getRecaudacionCabina(int i) {
        return porCabina[i];
    }

    public synchronized int getNumCabinas() {
        return cabinas.length;
    }

    public synchronized ArrayList<String> getCabinasMasRecaudacion() { // patrón de empates
        ArrayList<String> mejores = new ArrayList<>();
        int max = 0;
        for (int i = 0; i < porCabina.length; i++) {
            if (porCabina[i] > max) {
                max = porCabina[i];
                mejores.clear();
                mejores.add(cabinas[i].getNombre());
            } else if (porCabina[i] == max)
                mejores.add(cabinas[i].getNombre());
        }
        return mejores;
    }
}
