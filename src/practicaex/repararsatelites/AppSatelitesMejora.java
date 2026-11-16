package practicaex.repararsatelites;

import java.util.ArrayList;
import java.util.Random;

// ===== SATÉLITE: datos + su propio "monitor" para la espera astronauta-inspector =====
class Satelite {
    static final int AVERIADO = 0, EN_REPARACION = 1, REPARADO = 2, EN_VERIFICACION = 3, VERIFICADO = 4;
    private final int num;
    private int estado = AVERIADO;          // lo cambia SOLO la Base (con su candado)
    private boolean verificado = false;     // lo cambia SOLO el propio satélite (con su candado)

    public Satelite(int num) { this.num = num; }
    public int getEstado() { return estado; }
    public void setEstado(int e) { estado = e; }

    // El astronauta espera AQUÍ, en este satélite concreto (no en la Base)
    public synchronized void esperarInspector(Astronauta a) {
        while (!verificado)
            try { wait(); } catch (InterruptedException e) { }
        System.out.println("   " + a.getName() + " se despide de " + this + " y se va");
    }

    // El inspector verifica y despierta SOLO a quien espera en este satélite
    public synchronized void verificar(Inspector i) {
        verificado = true;
        System.out.println(i.getName() + " VERIFICA " + this);
        notifyAll();
    }

    @Override public String toString() { return "S" + num; }
}

// ===== RECURSO COMPARTIDO: la Base reparte satélites =====
class Base {
    private final ArrayList<Satelite> satelites = new ArrayList<>();
    private final Random rnd = new Random();
    private int verificados = 0;

    public Base(int n) { for (int i = 1; i <= n; i++) satelites.add(new Satelite(i)); }

    private ArrayList<Satelite> conEstado(int estado) {
        ArrayList<Satelite> lista = new ArrayList<>();
        for (Satelite s : satelites) if (s.getEstado() == estado) lista.add(s);
        return lista;
    }

    // ---- ASTRONAUTAS: un averiado al azar, o null si ya no quedan averiados ----
    public synchronized Satelite pedirParaReparar(Astronauta a) {
        ArrayList<Satelite> libres = conEstado(Satelite.AVERIADO);
        if (libres.isEmpty()) return null;            // nunca volverá a haber averiados → fin
        Satelite s = libres.get(rnd.nextInt(libres.size()));
        s.setEstado(Satelite.EN_REPARACION);
        System.out.println(a.getName() + " va a reparar " + s);
        return s;
    }

    public synchronized void marcarReparado(Astronauta a, Satelite s) {
        s.setEstado(Satelite.REPARADO);
        System.out.println(a.getName() + " ha REPARADO " + s + " y espera al inspector");
        notifyAll();                                   // puede haber inspectores esperando trabajo
    }

    // ---- INSPECTORES: un reparado al azar; esperan si no hay; null cuando todo verificado ----
    public synchronized Satelite pedirParaVerificar(Inspector i) {
        while (conEstado(Satelite.REPARADO).isEmpty() && verificados < satelites.size())
            try { wait(); } catch (InterruptedException e) { }
        if (verificados == satelites.size()) return null;
        ArrayList<Satelite> listos = conEstado(Satelite.REPARADO);
        Satelite s = listos.get(rnd.nextInt(listos.size()));
        s.setEstado(Satelite.EN_VERIFICACION);        // ningún otro inspector lo cogerá
        System.out.println(i.getName() + " va a verificar " + s);
        return s;
    }

    public synchronized void marcarVerificado(Satelite s) {
        s.setEstado(Satelite.VERIFICADO);
        verificados++;
        notifyAll();                                   // por si era el último: los demás inspectores acaban
    }

    public synchronized int getVerificados() { return verificados; }
}

// ===== HILO TIPO A =====
class Astronauta extends Thread {
    private final Base base; private final Random rnd = new Random();
    private int reparados = 0;
    public Astronauta(String n, Base b) { super(n); base = b; }
    public int getReparados() { return reparados; }

    @Override public void run() {
        Satelite s;
        while ((s = base.pedirParaReparar(this)) != null) {
            try { Thread.sleep(100 + rnd.nextInt(401)); } catch (InterruptedException e) { }   // reparando
            base.marcarReparado(this, s);
            s.esperarInspector(this);                  // espera en ESE satélite
            reparados++;
        }
        System.out.println(getName() + " vuelve a la Tierra");
    }
}

// ===== HILO TIPO B =====
class Inspector extends Thread {
    private final Base base; private final Random rnd = new Random();
    private int verificados = 0;
    public Inspector(String n, Base b) { super(n); base = b; }
    public int getVerificados() { return verificados; }

    @Override public void run() {
        Satelite s;
        while ((s = base.pedirParaVerificar(this)) != null) {
            try { Thread.sleep(50 + rnd.nextInt(151)); } catch (InterruptedException e) { }    // viajando/verificando
            s.verificar(this);                         // despierta al astronauta de ese satélite
            base.marcarVerificado(s);
            verificados++;
        }
        System.out.println(getName() + " vuelve a la Tierra");
    }
}

public class AppSatelitesMejora {
    public static void main(String[] args) {
        final int NUM_SATELITES = 10, NUM_ASTRONAUTAS = 4, NUM_INSPECTORES = 2;
        Base base = new Base(NUM_SATELITES);
        Astronauta[] as = new Astronauta[NUM_ASTRONAUTAS];
        Inspector[] is = new Inspector[NUM_INSPECTORES];
        for (int i = 0; i < as.length; i++) { as[i] = new Astronauta("Astronauta " + (i + 1), base); as[i].start(); }
        for (int i = 0; i < is.length; i++) { is[i] = new Inspector("Inspector " + (i + 1), base); is[i].start(); }
        for (Astronauta a : as) try { a.join(); } catch (InterruptedException e) { }
        for (Inspector i : is) try { i.join(); } catch (InterruptedException e) { }

        System.out.println("\n===== RESULTADOS =====");
        int sumaR = 0, sumaV = 0;
        for (Astronauta a : as) { System.out.println("  " + a.getName() + ": " + a.getReparados() + " reparados"); sumaR += a.getReparados(); }
        for (Inspector i : is) { System.out.println("  " + i.getName() + ": " + i.getVerificados() + " verificados"); sumaV += i.getVerificados(); }
        System.out.println("Reparados: " + sumaR + "  Verificados: " + sumaV + " (esperados " + NUM_SATELITES + ") -> "
                + (sumaR == NUM_SATELITES && sumaV == NUM_SATELITES && base.getVerificados() == NUM_SATELITES ? "OK" : "ERROR"));
    }
}
