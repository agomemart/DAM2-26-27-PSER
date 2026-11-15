package practicaex.estacionitv;

public class Linea {
    int numLinea;
    private Coche coche = null;

    public Linea(int numLinea) {
        this.numLinea = numLinea;
    }

    public boolean estaLibre() {
        return coche == null;
    }

    public void ocupar(Coche c) {
        coche = c;
    } 
    
    public void liberar() {
        coche = null;
    }

    public int getNumLinea() {
        return numLinea;
    }

    @Override
    public String toString() {
        return "Linea [numLinea=" + numLinea + ", coche=" + coche + ", estaLibre()=" + estaLibre() + "]";
    }

    
}
