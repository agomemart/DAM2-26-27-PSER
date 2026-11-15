package practicaex.hamburgueseria;

public class Bandeja {
    final int MAX_BANDEJA = 5;
    private int hamburguesas = 0;

    public synchronized void dejarHamburguesa(Cocinero c) {
        while (hamburguesas >= MAX_BANDEJA) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        hamburguesas++;
        System.out.println(c.getName() + " deja una hamburguesa. Bandeja: " + hamburguesas);
        notifyAll();
    }

    public synchronized void cogerHamburguesa(Cliente c) {
        while (hamburguesas == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        hamburguesas--;
        notifyAll();
        System.out.println(c.getName() + " cogió una hamburguesa. Bandeja: " + hamburguesas);
    }

    public synchronized int getHamburguesas() {
        return hamburguesas;
    }

    
}
