package practicaex.lavanderia;

import java.util.Arrays;

public class Lavanderia {
    final int NUM_LAVADORAS = 3;
    static final int NUM_CLIENTES = 10;
    private int totalRecaudado = 0;
    Lavadora[] lavadoras = new Lavadora[NUM_LAVADORAS];
    private int contClientes = 0;
    
    public Lavanderia() {
        for (int i = 0; i < lavadoras.length; i++) {
            lavadoras[i] = new Lavadora("Lavadora " + i);
        }
    }

    public Lavadora buscarLibre() {
        for (Lavadora l : lavadoras) {
            if (l.getCliente() == null) {
                return l;
            }
        }
        return null;
    }

    public synchronized Lavadora usarLavadora(Cliente c) {
        Lavadora lavadora;
        while ((lavadora = buscarLibre()) == null) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        lavadora.setCliente(c);
        System.out.println(c.getName() + " entra en " + lavadora.getNombre());
        estado();
        return lavadora;
    }

    public synchronized void liberarLavadora(Cliente c, Lavadora l) {
        l.setCliente(null);
        System.out.println(c.getName() + " sale de " + l.getNombre());
        totalRecaudado += l.precio;
        l.numColadas++;
        contClientes++;
        if (contClientes == NUM_CLIENTES) {
            System.out.println(c.getName() + " apaga la luz y cierra");
            return;
        }
        estado();
        notifyAll();
    }

    public synchronized void estado() {
        System.out.println(Arrays.toString(lavadoras));
    }

    public synchronized int getTotalRecaudado() {
        return totalRecaudado;
    }

    public synchronized void getColadasPorLavadora() {
        for (int i = 0; i < lavadoras.length; i++) {
            System.out.println(lavadoras[i].getNombre() + " - " + lavadoras[i].getNumColadas() + " coladas");
        }
    }

    
}
