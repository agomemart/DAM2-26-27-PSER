package hackers;

public class HackerNumerico extends Hacker {
    public HackerNumerico(String nombre, Servidor servidor, TablaRainbow tabla) {
        super(nombre, servidor, tabla);
    }

    @Override
    protected String getAlfabeto() { return "0123456789"; }
}
