package practicaex.panaderia;

public class Mostrador {
    private final int MAX = 5;
    private int barras = 0;

    public synchronized void dejar() {
        while (barras >= MAX) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        barras++;
        System.out.println("Panadero deja una barra. Mostrador: " + barras);
        notifyAll();
    }

    public synchronized void coger(String nombre) {
        while (barras == 0) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        barras--;
        System.out.println(nombre + " coge una barra. Mostrador: " + barras);
        notifyAll();
    }

    public synchronized int getBarras() {
        return barras;
    }
}
