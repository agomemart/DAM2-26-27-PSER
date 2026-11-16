package practicaex.Pintores;

/*
 * PINTORES - versión corregida
 * Suposiciones (el enunciado no lo tengo):
 *  - La casa tiene NUM_TABIQUES tabiques. Cada pintor pinta TODA la casa de su color.
 *  - Solo puede haber UNA persona dentro de la casa (un pintor o el fotógrafo).
 *  - Los pintores y el fotógrafo trabajan en bucle durante TIEMPO_TRABAJADO ms.
 *  - El fotógrafo entra a mirar la casa; si TODOS los tabiques son de su color favorito, saca una foto.
 */

// ===== CLASE DE DATOS =====
class Tabique {
    private int color = -1;                 // -1 = sin pintar
    public int getColor() { return color; }
    public void pintar(int color) { this.color = color; }
}

// ===== RECURSO COMPARTIDO =====
class Casa {
    private final Tabique[] tabiques;
    private boolean fin = false;            // la jornada ha terminado

    public Casa(int numTabiques) {
        tabiques = new Tabique[numTabiques];
        for (int i = 0; i < tabiques.length; i++)
            tabiques[i] = new Tabique();
    }

    public Tabique[] getTabiques() { return tabiques; }

    // COLA EN ORDEN DE LLEGADA (como los números de la carnicería)
    private int siguienteNumero = 0;        // el número que le doy al próximo que llegue
    private int atendiendo = 0;             // el número que puede entrar ahora

    // Entrar: cojo número y espero a que salga el mío. Devuelve false si la jornada ya terminó.
    public synchronized boolean entrar(String quien) {
        if (fin) return false;
        int miNumero = siguienteNumero++;
        while (miNumero != atendiendo && !fin)
            try { wait(); } catch (InterruptedException e) { }
        if (fin) return false;
        System.out.println(quien + " entra en la casa");
        return true;
    }

    public synchronized void salir(String quien) {
        atendiendo++;                       // que pase el siguiente número
        System.out.println(quien + " sale de la casa");
        notifyAll();
    }

    // El main avisa del fin de la jornada: despierta a los que esperan para que se vayan
    public synchronized void terminarJornada() {
        fin = true;
        System.out.println("*** FIN DE LA JORNADA ***");
        notifyAll();
    }

    // Solo se llama estando DENTRO de la casa (con el turno), así que nadie pinta a la vez
    public boolean todaDeColor(int color) {
        for (Tabique t : tabiques)
            if (t.getColor() != color) return false;
        return true;
    }
}

// ===== HILO =====
class Pintor extends Thread {
    private final Casa casa;
    private final int color;
    private int casasPintadas = 0;
    private final java.util.Random rnd = new java.util.Random();

    public Pintor(int color, Casa casa) {
        super("Pintor" + color);
        this.color = color;
        this.casa = casa;
    }

    public int getCasasPintadas() { return casasPintadas; }

    @Override
    public void run() {
        while (casa.entrar(getName())) {               // repite hasta que acabe la jornada
            for (Tabique t : casa.getTabiques()) {      // pinta FUERA del synchronized: tiene el turno,
                t.pintar(color);                        // nadie más puede entrar
                try { Thread.sleep(10); } catch (InterruptedException e) { }
            }
            casasPintadas++;
            System.out.println("   " + getName() + " ha pintado la casa de Color" + color);
            casa.salir(getName());
            try { Thread.sleep(50 + rnd.nextInt(101)); } catch (InterruptedException e) { }   // descansa: deja entrar a otros
        }
    }
}

// ===== HILO =====
class Fotografo extends Thread {
    private final Casa casa;
    private final int colorFavorito;
    private int fotos = 0;

    public Fotografo(Casa casa, int colorFavorito) {
        super("Fotografo");
        this.casa = casa;
        this.colorFavorito = colorFavorito;
    }

    public int getFotos() { return fotos; }

    @Override
    public void run() {
        while (casa.entrar(getName())) {
            if (casa.todaDeColor(colorFavorito)) {
                fotos++;
                System.out.println("   ¡FOTO! La casa es de Color" + colorFavorito + " (foto " + fotos + ")");
            } else {
                System.out.println("   El fotógrafo mira, pero no es su color favorito");
            }
            casa.salir(getName());
            try { Thread.sleep(50); } catch (InterruptedException e) { }   // espera antes de volver a mirar
        }
    }
}

public class PrincipalPintores {
    public static void main(String[] args) {
        final int NUM_PINTORES = 10;
        final int NUM_TABIQUES = 25;
        final int TIEMPO_TRABAJADO = 5000;

        Casa casa = new Casa(NUM_TABIQUES);
        Pintor[] pintores = new Pintor[NUM_PINTORES];
        Fotografo fotografo = new Fotografo(casa, 1);

        for (int i = 0; i < pintores.length; i++) {
            pintores[i] = new Pintor(i, casa);
            pintores[i].start();
        }
        fotografo.start();                              // ← faltaba

        try { Thread.sleep(TIEMPO_TRABAJADO); } catch (InterruptedException e) { }
        casa.terminarJornada();                         // avisa a todos de que se acabó

        for (Pintor p : pintores)                       // ← estaban comentados
            try { p.join(); } catch (InterruptedException e) { }
        try { fotografo.join(); } catch (InterruptedException e) { }

        System.out.println("\n===== RESULTADOS =====");
        int total = 0;
        for (Pintor p : pintores) {
            System.out.println("  " + p.getName() + ": " + p.getCasasPintadas() + " veces");
            total += p.getCasasPintadas();
        }
        System.out.println("Total de veces pintada: " + total);
        System.out.println("Fotos sacadas: " + fotografo.getFotos());
    }
}
