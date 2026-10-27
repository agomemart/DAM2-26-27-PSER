package apuestas;

public class Apuesta {
    String nombre;
    int golesDM2;
    int golesPRF;
    int cantidad;
    
    public Apuesta(String nombre, int golesDM2, int golesPRF, int cantidad) {
        this.nombre = nombre;
        this.golesDM2 = golesDM2;
        this.golesPRF = golesPRF;
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return nombre + " - Goles DM2: " + golesDM2 + " - Goles PRF: " + golesPRF + " - Cantidad: " + cantidad;
    }
}
