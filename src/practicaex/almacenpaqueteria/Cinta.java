package practicaex.almacenpaqueteria;

import java.util.ArrayList;
import java.util.Random;

public class Cinta {
    private boolean estaEncargado = false;
    final int MAX_CINTA = 6;
    private final ArrayList<Paquete> paquetes = new ArrayList<>();
    private int contPaquetes = 0;
    private final Random rnd = new Random();
    private int descargados = 0;
    private int recogidos = 0;
    private int pesoDescargado = 0;
    private int pesoRecogido = 0;

    public synchronized void llegaEncargado(Encargado e) {
        System.out.println("Llegó el " + e.getName());
        estaEncargado = true;
        notifyAll();
    }

    public synchronized void esperarEncargado() {
        while (!estaEncargado) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }

    public synchronized void descargarPaquete(Camion c) {
        while (paquetes.size() >= MAX_CINTA) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        contPaquetes++;
        Paquete p = new Paquete("P" + contPaquetes, 1 + rnd.nextInt(20));
        paquetes.add(p);
        descargados++;
        pesoDescargado += p.getPeso();
        System.out.println(c.getName() + " descarga " + p.getNombre() + " (" + p.getPeso() + " kg) Cinta: " + paquetes);
        notifyAll();
    }

    public synchronized Paquete repartirPaquete(Repartidor r) {
        while (paquetes.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        Paquete p = paquetes.remove(0);
        System.out.println(r.getName() + " recoge " + p.getNombre() + " (" + p.getPeso() + " kg) Cinta: " + paquetes);
        recogidos++;
        pesoRecogido += p.getPeso();
        notifyAll();
        return p;
    }

    public synchronized int getDescargados() {
        return descargados;
    }

    public synchronized int getRecogidos() {
        return recogidos;
    }

    public synchronized int getPesoDescargado() {
        return pesoDescargado;
    }

    public synchronized int getPesoRecogido() {
        return pesoRecogido;
    }

    public synchronized int getTamano() {
        return paquetes.size();
    }

}
