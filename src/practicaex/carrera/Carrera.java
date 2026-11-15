package practicaex.carrera;

public class Carrera {
    private boolean disparo = false;

    public synchronized void esperarSalida(String nombre) {
        System.out.println(nombre + " se coloca en línea de salida");
        while (!disparo) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        System.out.println(nombre + " SALE CORRIENDO!");
    }

    public synchronized void disparar() {
        System.out.println("Juez: PUM!!");
        disparo = true;
        notifyAll();
    }
}
