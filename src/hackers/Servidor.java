package hackers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Servidor {
    private final String hashGuardado;
    private volatile boolean hackeado = false;
    private Hacker ganador;
    private String claveEncontrada;

    public Servidor(String contrasena) {
        hashGuardado = hashear(contrasena);
    }

    public String getHashFiltrado() { return hashGuardado; }
    public boolean isHackeado()     { return hackeado; }
    public Hacker getGanador()      { return ganador; }
    public String getClaveEncontrada() { return claveEncontrada; }

    public boolean comprobar(Hacker hacker, String clave, String hash) {
        if (!hash.equals(hashGuardado))
            return false;
        synchronized (this) {
            if (hackeado) return false;
            hackeado = true;
            ganador = hacker;
            claveEncontrada = clave;
            return true;
        }
    }

    public static String hashear(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(texto.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes)
                sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}