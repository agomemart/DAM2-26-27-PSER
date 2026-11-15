package practicaex.lecturaguion;

import java.util.HashMap;

public class Teatro {
    private final String[] guion;
    private final int numActores;
    private int llegados = 0;
    private int siguiente = 0;
    private final HashMap<String, Integer> lineasPorActor = new HashMap<>();

    public Teatro(String[] guion, int numActores) {
        this.guion = guion;
        this.numActores = numActores;
    }

    public synchronized void llegar(Actor a) {
        llegados++;
        System.out.println(a.getName() + " llega (" + llegados + "/" + numActores + ")");
        if (llegados == numActores)
            System.out.println("¡EMPIEZA EL ENSAYO!");
        notifyAll();
        while (llegados < numActores)
            try {
                wait();
            } catch (InterruptedException e) {
            }
    }

    private String actorDe(int i) {
        return guion[i].split(": ", 2)[0];
    }

    private String textoDe(int i) {
        return guion[i].split(": ", 2)[1];
    }

    public synchronized String esperarTurno(String nombre) {
        while (siguiente < guion.length && !actorDe(siguiente).equals(nombre))
            try {
                wait();
            } catch (InterruptedException e) {
            }
        if (siguiente >= guion.length)
            return null;
        return textoDe(siguiente);
    }

    public synchronized void decirLinea(String nombre, String texto) {
        System.out.println("[" + (siguiente + 1) + "] " + nombre + ": " + texto);
        lineasPorActor.put(nombre, lineasPorActor.getOrDefault(nombre, 0) + 1);
        siguiente++;
        notifyAll();
    }

    public synchronized int getLineasDichas() {
        return siguiente;
    }

    public synchronized int getLineas(String nombre) {
        return lineasPorActor.getOrDefault(nombre, 0);
    }
}
