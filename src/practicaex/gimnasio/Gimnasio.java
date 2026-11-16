package practicaex.gimnasio;

import java.util.ArrayList;
import java.util.Random;

public class Gimnasio {
    private final Maquina[] maquinas;
    private final Random rnd = new Random();

    public Gimnasio(String[] nombres, int maxUsos) {
        maquinas = new Maquina[nombres.length];
        for (int i = 0; i < nombres.length; i++)
            maquinas[i] = new Maquina(nombres[i], maxUsos);
    }

    public Maquina[] getMaquinas() {
        return maquinas;
    }

    public synchronized Maquina elegirMaquina() {
        ArrayList<Maquina> validas = new ArrayList<>();
        for (Maquina m : maquinas)
            if (m.admiteMasUsos())
                validas.add(m);
        if (validas.isEmpty())
            return null;
        return validas.get(rnd.nextInt(validas.size()));
    }

    public String estado() {
        StringBuilder sb = new StringBuilder();
        for (Maquina m : maquinas)
            sb.append(m).append(" ");
        return sb.toString();
    }
}