package practicaex.hamburgueseria;

import java.util.ArrayList;
import java.util.HashMap;

public class Caja {
    private int recaudacionTotal = 0;
    private int numVentas = 0;
    private final HashMap<String, Integer> gastadoPorCliente = new HashMap<>();

    public synchronized void registrarVenta(Cliente c, int precio) {
        numVentas++;
        recaudacionTotal += precio;
        int gastadoAntes = gastadoPorCliente.getOrDefault(c.getName(), 0);
        gastadoPorCliente.put(c.getName(), gastadoAntes + precio);
    }

    public synchronized int getRecaudacionTotal() {
        return recaudacionTotal;
    }

    public synchronized int getNumVentas() {
        return numVentas;
    }

    public synchronized ArrayList<String> getMejoresClientes() {
        ArrayList<String> mejores = new ArrayList<>();
        int max = 0;
        for (String nombre : gastadoPorCliente.keySet()) {
            int gastado = gastadoPorCliente.get(nombre);
            if (gastado > max) {
                max = gastado;
                mejores.clear();
                mejores.add(nombre);
            } else if (gastado == max) {
                mejores.add(nombre);
            }
        }

        return mejores;
    }

    public synchronized int getGastado(String nombre) {
        return gastadoPorCliente.getOrDefault(nombre, 0);
    }

}
