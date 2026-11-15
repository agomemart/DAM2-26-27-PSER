package practicaex.recaudacionbenefica;

public class Hucha {
    private int dineroAcumulado = 0;

    public synchronized void donar(int cantidad) {
        dineroAcumulado += cantidad;
    }

    public int getDineroAcumulado() {
        return dineroAcumulado;
    }

    
}
