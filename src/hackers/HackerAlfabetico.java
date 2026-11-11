package hackers;


public class HackerAlfabetico extends Hacker {
    public HackerAlfabetico(String nombre, Servidor servidor, TablaRainbow tabla) {
        super(nombre, servidor, tabla);
    }

    @Override
    protected String getAlfabeto() { return "abcdefghijklmnopqrstuvwxyz"; }
}
