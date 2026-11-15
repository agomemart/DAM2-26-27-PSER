package practicaex.estacionitv;

import java.util.ArrayList;

public class MainEstacion {
    public static void main(String[] args) {
        final int NUM_INSPECTORES = 3;
        final int NUM_COCHES = 15;
        Inspector[] inspectores = new Inspector[NUM_INSPECTORES];
        Coche[] coches = new Coche[NUM_COCHES];
        Estacion estacion = new Estacion();
        
        for (int i = 0; i < inspectores.length; i++) {
            inspectores[i] = new Inspector("Inspector " + i, estacion);
            inspectores[i].start();
        }

        for (int i = 0; i < coches.length; i++) {
            coches[i] = new Coche("Coche " + i, estacion);
            coches[i].start();
        }

        for (Inspector i : inspectores) {
            try {
                i.join();
            } catch (InterruptedException e) {
            }
        }

        for (Coche c : coches) {
            try {
                c.join();
            } catch (InterruptedException e) {
            }
        }

       int insp = estacion.getInspecciones();
        int fav = estacion.getFavorables();
        int desf = estacion.getDesfavorables();
        int rec = estacion.getRecaudacion();

        System.out.println("\n===== RESULTADOS =====");
        System.out.println("Inspecciones: " + insp + " (favorables " + fav + " + desfavorables " + desf + ") -> "
                + (insp == fav + desf ? "OK" : "ERROR"));
        System.out.println("Recaudación: " + rec + " € (esperado " + insp * Estacion.PRECIO + " €) -> "
                + (rec == insp * Estacion.PRECIO ? "OK" : "ERROR"));

        int repitieron = 0, suspendidos = 0;
        for (Coche c : coches) {
            if (c.getRepito()) repitieron++;
            if (!c.getFavorableFinal()) suspendidos++;
        }
        System.out.println("Coches que repitieron: " + repitieron);
        System.out.println("Coches con resultado final DESFAVORABLE: " + suspendidos);
        System.out.println("Comprobación: inspecciones = coches + repeticiones -> " + insp + " = " + NUM_COCHES
                + " + " + repitieron + " -> " + (insp == NUM_COCHES + repitieron ? "OK" : "ERROR"));

        System.out.println("Inspecciones por línea:");
        for (int i = 0; i < NUM_INSPECTORES; i++)
            System.out.println("  L" + (i + 1) + ": " + estacion.getInspeccionesLinea(i));

        ArrayList<Integer> mejores = estacion.getLineasMasTrabajo();
        if (mejores.size() == 1)
            System.out.println("Línea que más trabajó: L" + mejores.get(0));
        else
            System.out.println("Empate entre las líneas: " + mejores);
        System.out.println("Fin del programa");
    }
}
