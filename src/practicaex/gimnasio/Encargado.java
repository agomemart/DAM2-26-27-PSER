package practicaex.gimnasio;

public class Encargado extends Thread {
    private final Gimnasio gimnasio;

    public Encargado(Gimnasio gimnasio) {
        super("Encargado");
        this.gimnasio = gimnasio;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            System.out.println("   [ENCARGADO] " + gimnasio.estado());
        }
    }
}
