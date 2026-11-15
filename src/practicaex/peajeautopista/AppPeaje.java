package practicaex.peajeautopista;

import java.util.ArrayList;

public class AppPeaje {
    public static void main(String[] args) {
        final int NUM_OPERARIOS = 3;
        final int NUM_VEHICULOS = 30;
        Peaje peaje = new Peaje();
        Operario[] operarios = new Operario[NUM_OPERARIOS];
        Vehiculo[] vehiculos = new Vehiculo[NUM_VEHICULOS];

        for (int i = 0; i < operarios.length; i++) {
            operarios[i] = new Operario("Operario " + i, peaje);
            operarios[i].start();
        }

        for (int i = 0; i < vehiculos.length; i++) {
            vehiculos[i] = new Vehiculo("Vehículo " + i, peaje);
            vehiculos[i].start();
        }

        for (Operario o : operarios) {
            try {
                o.join();
            } catch (InterruptedException e) {
            }
        }

        for (Vehiculo v : vehiculos) {
            try {
                v.join();
            } catch (InterruptedException e) {
            }
        }

        int motos = peaje.getMotos(), coches = peaje.getCoches(), camiones = peaje.getCamiones();
        int total = motos + coches + camiones;
        int rec = peaje.getRecaudacion();
        int esperada = motos * 2 + coches * 4 + camiones * 8;

        System.out.println("\n===== RESULTADOS =====");
        System.out.println("Motos: " + motos + "  Coches: " + coches + "  Camiones: " + camiones);
        System.out.println("Total vehículos: " + total + " (esperados " + NUM_VEHICULOS + ") -> "
                + (total == NUM_VEHICULOS ? "OK" : "ERROR"));
        System.out.println("Recaudación: " + rec + " € (esperada " + esperada + " €) -> "
                + (rec == esperada ? "OK" : "ERROR"));

        int sumaCabinas = 0;
        for (int i = 0; i < peaje.getNumCabinas(); i++) {
            System.out.println("  K" + (i + 1) + ": " + peaje.getRecaudacionCabina(i) + " €");
            sumaCabinas += peaje.getRecaudacionCabina(i);
        }
        System.out.println("Suma de cabinas: " + sumaCabinas + " € -> " + (sumaCabinas == rec ? "OK" : "ERROR"));

        ArrayList<String> mejores = peaje.getCabinasMasRecaudacion();
        System.out.println(mejores.size() == 1 ? "Cabina que más recaudó: " + mejores.get(0)
                : "Empate entre las cabinas: " + mejores);
        System.out.println("Fin del programa");
    }
}
