package practicaex.lavanderia;

public class Lavadora {
    String nombre;
    Cliente cliente = null;
    int precio = 3;
    int numColadas = 0;
    
    public Lavadora(String nombre) {
        this.nombre = nombre;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumColadas() {
        return numColadas;
    }

    @Override
    public String toString() {
        return nombre + " (" + cliente + ") ";
    }

    
}
