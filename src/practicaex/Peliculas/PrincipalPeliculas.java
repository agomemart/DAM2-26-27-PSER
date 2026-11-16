package practicaex.Peliculas;

import java.util.ArrayList;
import java.util.Random;

/*
 * PELÍCULAS - versión corregida
 * Suposiciones (el enunciado no lo tengo):
 *  - El cine tiene NUM_PELICULAS salas, cada una con una película y AFORO_SALA entradas.
 *  - Cada cinéfilo elige una sala al azar (entre las que aún tienen entradas) y hace cola en SU taquilla.
 *  - En cada taquilla se atiende a uno a la vez, en orden de llegada.
 *  - Cuando le toca, piensa un poco y decide (50 %) si compra. Si no compra, vuelve a elegir sala.
 *  - Si ya no quedan entradas en ninguna sala, se va a casa.
 */

// ===== CLASE DE DATOS =====
class Pelicula {
    private final String nombre;
    private final ArrayList<String> espectadores = new ArrayList<>();   // solo se modifica desde Sala (synchronized)

    public Pelicula(int num) { nombre = "Pelicula" + num; }
    public void anadirEspectador(String nombre) { espectadores.add(nombre); }
    public ArrayList<String> getEspectadores() { return espectadores; }
    @Override public String toString() { return nombre; }
}

// ===== RECURSO COMPARTIDO (uno por sala: cada taquilla tiene su propia cola) =====
class Sala {
    private final int numero;
    private final int aforo;
    private final Pelicula pelicula;
    private int vendidas = 0;
    private int sinEntrada = 0;             // veces que alguien llegó y estaba agotada

    // Cola en orden de llegada (números de la carnicería)
    private int siguienteNumero = 0;
    private int atendiendo = 0;

    public Sala(int numero, int aforo) {
        this.numero = numero;
        this.aforo = aforo;
        this.pelicula = new Pelicula(numero);
    }

    public synchronized boolean quedanEntradas() { return vendidas < aforo; }

    // Ponerse en la cola. Devuelve true cuando es su turno en taquilla y quedan entradas.
    public synchronized boolean hacerCola(Cinefilo c) {
        if (vendidas >= aforo) {                       // ni se pone a la cola
            sinEntrada++;
            System.out.println(c.getName() + " llega a " + this + ", pero está AGOTADA");
            return false;
        }
        int miNumero = siguienteNumero++;
        while (miNumero != atendiendo)
            try { wait(); } catch (InterruptedException e) { }
        if (vendidas >= aforo) {                       // se agotó mientras hacía cola
            sinEntrada++;
            System.out.println(c.getName() + " llega a la taquilla de " + this + ", pero se han AGOTADO");
            atendiendo++;                              // deja pasar al siguiente
            notifyAll();
            return false;
        }
        return true;                                   // es su turno: puede decidir
    }

    // Decide en la taquilla. Comprobar y comprar en el MISMO método synchronized.
    public synchronized boolean comprar(Cinefilo c, boolean quiere) {
        boolean compra = quiere && vendidas < aforo;
        if (compra) {
            vendidas++;
            pelicula.anadirEspectador(c.getName());
            System.out.println(c.getName() + " COMPRA entrada para " + this + " (" + vendidas + "/" + aforo + ")");
        } else {
            System.out.println(c.getName() + " decide no comprar en " + this);
        }
        atendiendo++;                                  // que pase el siguiente de la cola
        notifyAll();
        return compra;
    }

    public synchronized int getVendidas() { return vendidas; }
    public synchronized int getSinEntrada() { return sinEntrada; }
    public int getAforo() { return aforo; }
    public Pelicula getPelicula() { return pelicula; }
    @Override public String toString() { return "Sala" + numero; }
}

// ===== CONTENEDOR DE SALAS =====
class Cine {
    private final Sala[] salas;
    private final Random rnd = new Random();

    public Cine(int numSalas, int aforo) {
        salas = new Sala[numSalas];
        for (int i = 0; i < numSalas; i++)
            salas[i] = new Sala(i, aforo);
    }

    public Sala[] getSalas() { return salas; }

    // Una sala al azar entre las que tienen entradas; null si están todas agotadas
    public synchronized Sala escogerSala() {
        ArrayList<Sala> conEntradas = new ArrayList<>();
        for (Sala s : salas)
            if (s.quedanEntradas()) conEntradas.add(s);
        if (conEntradas.isEmpty()) return null;
        return conEntradas.get(rnd.nextInt(conEntradas.size()));
    }
}

// ===== HILO =====
class Cinefilo extends Thread {
    private final Cine cine;
    private final Random rnd = new Random();
    private Sala salaComprada = null;

    public Cinefilo(int i, Cine cine) {
        super("Cinefilo" + i);
        this.cine = cine;
    }

    public Sala getSalaComprada() { return salaComprada; }

    @Override
    public void run() {
        Sala sala;
        while ((sala = cine.escogerSala()) != null) {           // null → todo agotado → a casa
            if (!sala.hacerCola(this)) continue;                 // agotada: elige otra
            try { Thread.sleep(rnd.nextInt(10)); } catch (InterruptedException e) { }   // piensa (FUERA del synchronized)
            boolean quiere = rnd.nextInt(100) < 50;
            if (sala.comprar(this, quiere)) {
                salaComprada = sala;
                break;
            }
        }
        if (salaComprada == null)
            System.out.println(getName() + " se va a casa SIN entrada");
    }
}

public class PrincipalPeliculas {
    public static void main(String[] args) {
        final int NUM_PELICULAS = 5;
        final int AFORO_SALA = 20;
        final int NUM_CINEFILOS = 120;                  // más que entradas (100) para ver los agotados

        Cine cine = new Cine(NUM_PELICULAS, AFORO_SALA);
        Cinefilo[] cinefilos = new Cinefilo[NUM_CINEFILOS];
        for (int i = 0; i < cinefilos.length; i++) {
            cinefilos[i] = new Cinefilo(i, cine);
            cinefilos[i].start();
        }
        for (Cinefilo c : cinefilos)
            try { c.join(); } catch (InterruptedException e) { }

        System.out.println("\n===== RESULTADOS =====");
        int totalVendidas = 0;
        boolean aforoOk = true;
        for (Sala s : cine.getSalas()) {
            System.out.println("\n" + s + " - " + s.getPelicula() + " (" + s.getVendidas() + " / " + s.getAforo() + ")");
            System.out.println("  " + s.getPelicula().getEspectadores());
            System.out.println("  Veces que alguien la encontró agotada: " + s.getSinEntrada());
            totalVendidas += s.getVendidas();
            if (s.getVendidas() > s.getAforo()) aforoOk = false;
        }

        int conEntrada = 0;
        for (Cinefilo c : cinefilos) if (c.getSalaComprada() != null) conEntrada++;

        System.out.println("\nCinéfilos con entrada: " + conEntrada + "  /  sin entrada: " + (NUM_CINEFILOS - conEntrada));
        System.out.println("Entradas vendidas: " + totalVendidas + " = cinéfilos con entrada: " + conEntrada + " -> "
                + (totalVendidas == conEntrada ? "OK" : "ERROR"));
        System.out.println("Ninguna sala supera el aforo -> " + (aforoOk ? "OK" : "ERROR"));
    }
}
