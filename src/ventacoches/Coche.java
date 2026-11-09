package ventacoches;

public class Coche {
    enum Estado {
        LIBRE, PROBANDOSE, VENDIDO
    }

    private final String nombre;
    private int visitas = 0;
    private Estado estado = Estado.LIBRE;
    private Cliente comprador = null;

    public Coche(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVisitas() {
        return visitas;
    }

    public Estado getEstado() {
        return estado;
    }

    public Cliente getComprador() {
        return comprador;
    }

    void setEstado(Estado estado) {
        this.estado = estado;
    }

    void incrementaVisitas() {
        visitas++;
    }

    void setComprador(Cliente c) {
        this.comprador = c;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
