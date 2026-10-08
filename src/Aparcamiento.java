public class Aparcamiento {
    private final int[] plazas;
    private int plazasLibres;

    public Aparcamiento(int numPlazas) {
        plazas = new int[numPlazas];
        plazasLibres = numPlazas;
    }

    public synchronized int aparcar(int conductor) {
        while (plazasLibres == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        int plaza = 0;
        while (plazas[plaza] != 0) {
            plaza++;
        }

        plazas[plaza] = conductor;
        plazasLibres--;

        System.out.println("Conductor " + conductor + " entra en la plaza " + plaza);
        System.out.println(estado());

        return plaza;
    }

    public synchronized void salir(int conductor, int plaza) {
        plazas[plaza] = 0;
        plazasLibres++;
        System.out.println("Conductor " + conductor + " sale de la plaza " + plaza);
        System.out.println(estado());
        notifyAll();
    }

    public String estado() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plazas.length; i++) {
            sb.append(" | ");
            sb.append(plazas[i] == 0 ? "--" : plazas[i]);
        }

        return sb.append(" |").toString();
    }
}
