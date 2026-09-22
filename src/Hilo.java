public class Hilo extends Thread {
    final static int NUM_HILOS = 10;

    public Hilo(String nombre) {
        super(nombre);
    }

    public void run() {
        for (int i = 0; i < NUM_HILOS; i++) {
            System.out.println(getName() + " por " + i + " vez");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Hilo[] hilos = new Hilo[NUM_HILOS];

        for (int i = 0; i < NUM_HILOS; i++) {
            hilos[i] = new Hilo("Hilo " + i);
            hilos[i].start();
        }

        for (int i = 0; i < NUM_HILOS; i++) {
            hilos[i].join();
        }

        System.out.println("Fin del programa");
    }
}
