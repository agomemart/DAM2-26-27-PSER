package practicaex.pingpong;

public class Jugador extends Thread{
    private final Mesa mesa;
    private int miTurno;
    private final int NUM_GOLPES = 10;

    public Jugador(String name, int miTurno, Mesa mesa) {
        super(name);
        this.miTurno = miTurno;
        this.mesa = mesa;
    }

    public void run() {
        for (int i = 0; i < NUM_GOLPES; i++) {
            mesa.golpear(getName(), miTurno);
        }
    }
}
