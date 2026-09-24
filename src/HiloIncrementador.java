public class HiloIncrementador extends Thread {
    final static int NUM_HILOS = 100;
    static int cont = 0;
    
    public HiloIncrementador(String nombre) {
        super(nombre);
    }
    
    public void run() {
        cont++;
    }

    public static void main(String[] args) {
        HiloIncrementador[] hilos = new HiloIncrementador[NUM_HILOS];
        
        for (int i = 0; i < NUM_HILOS; i++) {
            hilos[i] = new HiloIncrementador("Hilo " + i);
            hilos[i].start();
        }

        System.out.println("Contador: " + cont);
    }
}
