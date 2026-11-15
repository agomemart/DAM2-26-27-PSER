package practicaex.lecturaguion;

import java.util.Random;

public class Actor extends Thread {
    final Teatro teatro;
    Random rnd = new Random();

    public Actor(String name, Teatro teatro) {
        super(name);
        this.teatro = teatro;
    }

    public void run() {
        try {
            Thread.sleep(rnd.nextInt(1000));
        } catch (InterruptedException e) {
        }
        teatro.llegar(this);

        String texto;
        while ((texto = teatro.esperarTurno(getName())) != null) {
            try {
                Thread.sleep(100 + rnd.nextInt(401));
            } catch (InterruptedException e) {
            }
            teatro.decirLinea(getName(), texto);
        }
        System.out.println(getName() + " se va a casa");
    }
}
