package hackers;

import java.util.Random;

public abstract class Hacker extends Thread {
    private final Servidor servidor;
    private final TablaRainbow tabla;
    private final Random rnd = new Random();

    public Hacker(String nombre, Servidor servidor, TablaRainbow tabla) {
        super(nombre);
        this.servidor = servidor;
        this.tabla = tabla;
    }

    protected abstract String getAlfabeto();

    private String generarClave() {
        String alfabeto = getAlfabeto();
        int longitud = rnd.nextInt(AppHackers.MAX_CHARS) + 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++)
            sb.append(alfabeto.charAt(rnd.nextInt(alfabeto.length())));
        return sb.toString();
    }

    @Override
    public String toString() { return getName() + " (" + getClass().getSimpleName() + ")"; }

    @Override
    public void run() {
        while (!servidor.isHackeado()) {
            String clave = generarClave();
            String hash = tabla.getHash(clave);
            servidor.comprobar(this, clave, hash);
        }
    }
}
