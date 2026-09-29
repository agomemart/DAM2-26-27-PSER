public class HiloIncrementador extends Thread {
    final static int NUM_HILOS = 100;
    final static int NUM_INCREMENTOS = 10000;
    Contador contador;
    
    public HiloIncrementador(Contador contador) {
        this.contador = contador;
    }
    
    public void run() {
        for (int i = 0; i < NUM_INCREMENTOS; i++) {
            synchronized(contador) {
                contador.incrementa();
            }

            /*synchronized(cont);
            int temp = cont;
            temp++;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cont = temp;*/
        }
        
    }

    public static void main(String[] args) throws InterruptedException {
        HiloIncrementador[] hilos = new HiloIncrementador[NUM_HILOS];
        Contador c = new Contador();
        
        for (int i = 0; i < NUM_HILOS; i++) {
            hilos[i] = new HiloIncrementador(c);
            hilos[i].start();
        }

        for (HiloIncrementador h : hilos) {
            h.join();
        }

        System.out.println("Contador: " + c.getContador());
    }
}
