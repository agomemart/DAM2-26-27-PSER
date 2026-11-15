package practicaex.pingpong;

public class Mesa {
    private int turno = 0;

    public synchronized void golpear(String palabra, int miTurno) {
        while (turno != miTurno) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        System.out.println(palabra);
        turno = 1 - turno;
        notify();
    }
}
