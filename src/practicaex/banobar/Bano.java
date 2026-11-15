package practicaex.banobar;

public class Bano {
    private final int CABINAS = 50;
    private int dentro = 0;

    public synchronized void entrar(String nombre) {
        while (CABINAS <= dentro) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        dentro++;
        System.out.println(nombre + " entra. Dentro: " + dentro);
    }

    public synchronized void salir(String nombre) {
        dentro--;
        System.out.println(nombre + " sale. Dentro: " + dentro);
        notify();
    }
}
