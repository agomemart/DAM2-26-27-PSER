import java.util.ArrayList;
import java.util.Arrays;

public class Urna {
    Partido[] partidos;

    public Urna(String[] nombresPartidos) {
        partidos = new Partido[nombresPartidos.length];
        int numPartido = 0;
        for (String nombrePartido : nombresPartidos)
            partidos[numPartido++] = new Partido(nombrePartido);
    }

    public int getNumPartidos() {
        return partidos.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(partidos);
    }

    public void vota(int numPartido) {
        partidos[numPartido].vota();
    }

    public void visualizarResultados() {
        ArrayList<Partido> ganadores = new ArrayList<>();
        ganadores.add(partidos[0]);
        int totalVotos = partidos[0].getNumVotos();
        for (int i = 1; i < getNumPartidos(); i++) {
            int numVotosPartidoActual = partidos[i].getNumVotos();
            int numVotosPartidoGanador = ganadores.get(0).getNumVotos();
            if (numVotosPartidoActual >= numVotosPartidoGanador) {
                if (numVotosPartidoActual > numVotosPartidoGanador)
                    ganadores.clear();
                ganadores.add(partidos[i]);
            }
            totalVotos += numVotosPartidoActual;
        }

        System.out.println("\nTotal: " + totalVotos + " votos emitidos.");
        if (ganadores.size() == 1)
            System.out.println("Ganador: " + ganadores.get(0));
        else
            System.out.println("Empate: " + ganadores);

    }
}