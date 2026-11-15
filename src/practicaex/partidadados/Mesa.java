package practicaex.partidadados;

public class Mesa {
    final int NUM_JUGADORES = 4;
    private int numJugadores;
    private int turno = 0;
    private int sentados = 0;

    public synchronized void sentarse(Jugador j) {
        sentados++;
        System.out.println(j.getName() + " se sienta (" + sentados + "/" + NUM_JUGADORES + ")");
        if (sentados == numJugadores) {
            System.out.println("Estamos todos. ¡Empieza la partida!");
        }
        notifyAll();
        while (sentados < numJugadores) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }

    public synchronized void esperarTurno(int miTurno) {
        while (turno != miTurno)
            try {
                wait();
            } catch (InterruptedException e) {
            }
    }

    public synchronized void pasarTurno() {
        turno = (turno + 1) % NUM_JUGADORES;
        notifyAll();
    }
}
