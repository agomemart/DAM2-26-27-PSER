package practicaex.pistakarts;

public class Pista {
    Kart[] karts;
    boolean llegoEncargado = false;

    public Pista(int numKarts) {
        karts = new Kart[numKarts];
        for (int i = 0; i < karts.length; i++) {
            karts[i] = new Kart("Kart " + i, null);
        }
    }

    public synchronized void llegaEncargado(Encargado e) {
        llegoEncargado = true;
        notifyAll();
        System.out.println("Llegó el encargado. Abriendo pista...");
    }

    public synchronized void llegaPiloto(Piloto p) {
        while (!llegoEncargado) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        System.out.println(p.getName() + " llegó a la pista");
    }

    private Kart buscarLibre() {
        for (Kart k : karts) {
            if (k.getPiloto() == null) {
                return k;
            }
        }
        return null;
    }

    public synchronized Kart cogerKart(Piloto p) {
        Kart libre = null;
        while ((libre = buscarLibre()) == null) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        libre.setPiloto(p.getName());
        System.out.println(p.getName() + " coge " + libre.getNombre());
        return libre;

    }

    public synchronized void dejarKart(Kart kart) {
        for (int i = 0; i < karts.length; i++) {
            if (karts[i].getNombre().equals(kart.getNombre())) {
                karts[i].setPiloto(null);
                notifyAll();
                System.out.println(karts[i].getNombre() + " libre");
            }
        }
    }
}
