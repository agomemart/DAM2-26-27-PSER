package practicaex.repararsatelites;

import java.util.ArrayList;
import java.util.Random;

// ===== CLASE DE DATOS (no es hilo: no hace nada por sí misma) =====
class Satelite {
    static final int AVERIADO = 0, EN_REPARACION = 1, REPARADO = 2;
    private final int num;
    private int estado = AVERIADO;          // solo lo cambia la Base, dentro de sus synchronized

    public Satelite(int num) { this.num = num; }
    public int getEstado() { return estado; }
    public void setEstado(int e) { estado = e; }
    @Override public String toString() { return "S" + num; }
}

// ===== RECURSO COMPARTIDO =====
class Base {
    private final ArrayList<Satelite> satelites = new ArrayList<>();
    private final Random rnd = new Random();
    private int reparados = 0;

    public Base(int numSatelites) {
        for (int i = 1; i <= numSatelites; i++)
            satelites.add(new Satelite(i));
    }

    private ArrayList<Satelite> averiados() {          // los que nadie está reparando
        ArrayList<Satelite> lista = new ArrayList<>();
        for (Satelite s : satelites)
            if (s.getEstado() == Satelite.AVERIADO) lista.add(s);
        return lista;
    }

    // Devuelve un satélite averiado al azar, o null si ya están todos reparados
    public synchronized Satelite pedirSatelite(Astronauta a) {
        while (averiados().isEmpty() && reparados < satelites.size())
            try { wait(); } catch (InterruptedException e) { }      // no hay libres, pero aún no hemos acabado
        if (reparados == satelites.size())
            return null;                                           // misión cumplida
        ArrayList<Satelite> libres = averiados();
        Satelite s = libres.get(rnd.nextInt(libres.size()));       // uno al azar
        s.setEstado(Satelite.EN_REPARACION);                       // ya nadie más lo puede coger
        System.out.println(a.getName() + " va a reparar " + s);
        return s;
    }

    // El astronauta informa del resultado de la reparación
    public synchronized void terminarReparacion(Astronauta a, Satelite s, boolean exito) {
        if (exito) {
            s.setEstado(Satelite.REPARADO);
            reparados++;
            System.out.println(a.getName() + " ha REPARADO " + s + "   (" + reparados + "/" + satelites.size() + ")");
        } else {
            s.setEstado(Satelite.AVERIADO);                        // vuelve a estar disponible
            System.out.println(a.getName() + " NO ha podido reparar " + s + ", queda libre otra vez");
        }
        notifyAll();     // despierta a los que esperan: hay un satélite libre o quizá hemos acabado
    }

    public synchronized int getReparados() { return reparados; }
}

// ===== HILO =====
class Astronauta extends Thread {
    private final Base base;
    private final Random rnd = new Random();
    private int reparados = 0;                      // solo lo toca este hilo

    public Astronauta(String nombre, Base base) { super(nombre); this.base = base; }
    public int getReparados() { return reparados; }

    @Override
    public void run() {
        Satelite s;
        while ((s = base.pedirSatelite(this)) != null) {
            try { Thread.sleep(100 + rnd.nextInt(401)); } catch (InterruptedException e) { }   // reparando (fuera del synchronized)
            boolean exito = rnd.nextInt(100) < 80;                                             // 80 % de éxito
            base.terminarReparacion(this, s, exito);
            if (exito) reparados++;
        }
        System.out.println(getName() + " vuelve a la Tierra");
    }
}

public class AppSatelites {
    public static void main(String[] args) {
        final int NUM_SATELITES = 10, NUM_ASTRONAUTAS = 4;
        Base base = new Base(NUM_SATELITES);
        Astronauta[] astronautas = new Astronauta[NUM_ASTRONAUTAS];
        for (int i = 0; i < NUM_ASTRONAUTAS; i++) {
            astronautas[i] = new Astronauta("Astronauta " + (i + 1), base);
            astronautas[i].start();
        }
        for (Astronauta a : astronautas)
            try { a.join(); } catch (InterruptedException e) { }

        System.out.println("\n===== RESULTADOS =====");
        int suma = 0;
        for (Astronauta a : astronautas) {
            System.out.println("  " + a.getName() + ": " + a.getReparados() + " satélites");
            suma += a.getReparados();
        }
        System.out.println("Total reparados: " + suma + " (esperados " + NUM_SATELITES + ") -> "
                + (suma == NUM_SATELITES && base.getReparados() == NUM_SATELITES ? "OK" : "ERROR"));
    }
}
