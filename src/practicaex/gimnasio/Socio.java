package practicaex.gimnasio;

import java.util.Random;

public class Socio extends Thread {
    static final int NUM_EJERCICIOS = 3;
    private final Gimnasio gimnasio;
    private final Random rnd = new Random();
    private int hechos = 0;

    public Socio(String name, Gimnasio gimnasio) {
        super(name);
        this.gimnasio = gimnasio;
    }

    public int getHechos() {
        return hechos;
    }

    @Override
    public void run() {
        while (hechos < NUM_EJERCICIOS) {
            Maquina m = gimnasio.elegirMaquina();
            if (m == null) {
                System.out.println(getName() + " se va a casa con " + hechos + "/" + NUM_EJERCICIOS + " ejercicios");
                return;
            }
            if (!m.usar(this))
                continue;
            try {
                Thread.sleep(100 + rnd.nextInt(201));
            } catch (InterruptedException e) {
            }
            m.liberar(this);
            hechos++;
        }
        System.out.println(getName() + " termina su rutina");
    }

}
