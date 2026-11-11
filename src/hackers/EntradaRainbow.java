package hackers;

public class EntradaRainbow {
    private final String clave;
    private final String hash;
    private int contador = 1;

    public EntradaRainbow(String clave, String hash) {
        this.clave = clave;
        this.hash = hash;
    }

    public String getHash()  { return hash; }
    public int getContador() { return contador; }
    public void incrementar() { contador++; }

    @Override
    public String toString() { return "\"" + clave + "\" -> " + hash + " (" + contador + " veces)"; }
}