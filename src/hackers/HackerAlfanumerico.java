package hackers;
 
public class HackerAlfanumerico extends Hacker {
    public HackerAlfanumerico(String nombre, Servidor servidor, TablaRainbow tabla) {
        super(nombre, servidor, tabla);
    }
 
    @Override
    protected String getAlfabeto() { return "abcdefghijklmnopqrstuvwxyz0123456789"; }
}