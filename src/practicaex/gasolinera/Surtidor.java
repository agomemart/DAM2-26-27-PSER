package practicaex.gasolinera;

public class Surtidor {
    int num;
    private Coche coche = null;

    public Surtidor(int num) {
        this.num = num;
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

    @Override
    public String toString() {
        return "Surtidor (" + num + ") está " + coche == null ? "LIBRE" : "OCUPADO";
    }


}
