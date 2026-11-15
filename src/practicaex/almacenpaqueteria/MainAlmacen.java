package practicaex.almacenpaqueteria;

import java.util.ArrayList;

public class MainAlmacen {
    public static void main(String[] args) {
        final int NUM_CAMIONES = 3;
        final int NUM_REPARTIDORES = 5;
        Cinta cinta = new Cinta();
        Encargado encargado = new Encargado("Encargado", cinta);
        Repartidor[] repartidores = new Repartidor[NUM_REPARTIDORES];
        Camion[] camiones = new Camion[NUM_CAMIONES];

        encargado.start();

        for (int i = 0; i < camiones.length; i++) {
            camiones[i] = new Camion("Camión " + i, cinta);
            camiones[i].start();
        }

        for (int i = 0; i < repartidores.length; i++) {
            repartidores[i] = new Repartidor("Repartidor " + i, cinta);
            repartidores[i].start();
        }

        for (Camion c : camiones) {
            try {
                c.join();
            } catch (InterruptedException e) {
            }
        }

        for (Repartidor r : repartidores) {
            try {
                r.join();
            } catch (InterruptedException e) {
            }
        }

        try {
            encargado.join();
        } catch (InterruptedException e) {
        }

        int esperados = NUM_CAMIONES * Camion.PAQUETES_POR_CAMION;
        System.out.println("\n===== RESULTADOS =====");

        System.out.println("Descargados: " + cinta.getDescargados()
                + " / Recogidos: " + cinta.getRecogidos()
                + " (esperados " + esperados + ") -> "
                + (cinta.getDescargados() == esperados && cinta.getRecogidos() == esperados ? "OK" : "ERROR"));

        System.out.println("Peso descargado: " + cinta.getPesoDescargado()
                + " kg / Peso recogido: " + cinta.getPesoRecogido() + " kg -> "
                + (cinta.getPesoDescargado() == cinta.getPesoRecogido() ? "OK" : "ERROR"));

        ArrayList<String> mejores = new ArrayList<>();
        int max = 0;
        int sumaKg = 0;
        for (Repartidor r : repartidores) {
            System.out.println("  " + r.getName() + ": " + r.getKg() + " kg");
            sumaKg += r.getKg();
            if (r.getKg() > max) {
                max = r.getKg();
                mejores.clear();
                mejores.add(r.getName());
            } else if (r.getKg() == max) {
                mejores.add(r.getName());
            }
        }
        if (mejores.size() == 1)
            System.out.println("Más kilos: " + mejores.get(0) + " (" + max + " kg)");
        else
            System.out.println("Empate a " + max + " kg: " + mejores);

        System.out.println("Suma kilos repartidores: " + sumaKg + " kg -> "
                + (sumaKg == cinta.getPesoRecogido() ? "OK" : "ERROR"));

        System.out.println("Paquetes en la cinta: " + cinta.getTamano() + " -> "
                + (cinta.getTamano() == 0 ? "OK" : "ERROR"));
        System.out.println("Fin del programa");
    }
}
