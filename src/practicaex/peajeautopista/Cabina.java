package practicaex.peajeautopista;

public class Cabina {
    String nombre;
    Vehiculo vehiculo;

    public Cabina(String nombre, Vehiculo vehiculo) {
        this.nombre = nombre;
        this.vehiculo = vehiculo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public boolean estaLibre() {
        return vehiculo == null;
    }

    public void ocupar(Vehiculo v) {
        vehiculo = v;
    }

    public void liberar() {
        vehiculo = null;
    }

    @Override
    public String toString() {
        return nombre + " (" + vehiculo + ")";
    }

    
}
