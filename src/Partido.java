public class Partido {
    private final String nombre;
    private int numVotos;

    public Partido(String nombre) {
        this.nombre = nombre;
        this.numVotos = 0;
    }

    public int getNumVotos() {
        return numVotos;
    }

    @Override
    public String toString() {
        return nombre + " (" + numVotos + ")";
    }

    public synchronized void vota() {
        this.numVotos++;
    }
}
