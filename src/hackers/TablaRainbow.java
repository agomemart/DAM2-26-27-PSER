package hackers;

import java.util.HashMap;

public class TablaRainbow {
    private final HashMap<String, EntradaRainbow> tabla = new HashMap<>();
    private EntradaRainbow masConsultada = null;

    public synchronized String getHash(String clave) {
        EntradaRainbow entrada = tabla.get(clave);
        if (entrada == null) {
            entrada = new EntradaRainbow(clave, Servidor.hashear(clave));
            tabla.put(clave, entrada);
        } else {
            entrada.incrementar();
        }
        if (masConsultada == null || entrada.getContador() > masConsultada.getContador())
            masConsultada = entrada;
        return entrada.getHash();
    }

    public synchronized EntradaRainbow getMasConsultada() { return masConsultada; }
    public synchronized int size() { return tabla.size(); }
}