package practicaex.pistakarts;

public class Kart {
    String nombre;
    String piloto;
    
    public Kart(String nombre, String piloto) {
        this.nombre = nombre;
        this.piloto = piloto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPiloto() {
        return piloto;
    }

    public void setPiloto(String piloto) {
        this.piloto = piloto;
    }

    
}
