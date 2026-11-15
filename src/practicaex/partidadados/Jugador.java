package practicaex.partidadados;

import java.util.Random;

public class Jugador extends Thread {
    final Mesa mesa;
    final Marcador marcador;
    final static int NUM_RONDAS = 5;
    int turno;
    final Random rnd = new Random();

    public Jugador(String name, int turno, Mesa mesa, Marcador marcador) {
        super(name);
        this.turno = turno;
        this.mesa = mesa;
        this.marcador = marcador;
    }

    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        mesa.sentarse(this);
        for (int i = 0; i < NUM_RONDAS; i++) {
            mesa.esperarTurno(turno);
            int tirada1 = 1 + rnd.nextInt(6);
            int tirada2 = 1 + rnd.nextInt(6);
            try {
                Thread.sleep(50 + rnd.nextInt(151));
            } catch (InterruptedException e) {
            }
            int total = tirada1 + tirada2;
            marcador.registrarPuntos(this, total);
            System.out.println("Ronda " + i + " - " + getName() + " saca " + total);
            mesa.pasarTurno();
        }
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

}
