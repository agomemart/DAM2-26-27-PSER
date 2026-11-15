package practicaex.partidadados;

public class MainDados {
    public static void main(String[] args) {
        final int NUM_JUGADORES = 4;
        Mesa mesa = new Mesa();
        Marcador marcador = new Marcador();
        Jugador[] jugadores = new Jugador[NUM_JUGADORES];

        for (int i = 0; i < jugadores.length; i++) {
            jugadores[i] = new Jugador("Jugador " + i, i, mesa, marcador);
            jugadores[i].start();
        }

        for (int i = 0; i < jugadores.length; i++) {
            try {
                jugadores[i].join();
            } catch (InterruptedException e) {
            }
        }

        if (marcador.getTotalTiradas() == NUM_JUGADORES * Jugador.NUM_RONDAS) {
            System.out.println("Total tiradas: " + marcador.getTotalTiradas());
        }

        System.out.println("Puntuación jugadores:");
        System.out.println("Jugador con más puntos: " + marcador.getJugadorMasPuntos());


        System.out.println("Fin del programa");
    }
}
