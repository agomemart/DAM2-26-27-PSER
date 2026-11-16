package practicaex.gimnasio;

import java.util.ArrayList;

public class AppGimnasio {
    public static void main(String[] args) {
        final int NUM_SOCIOS = 15;
        final int MAX_USOS = 8;
        String[] nombres = { "Cinta", "Bici", "Remo", "Eliptica", "Prensa" };

        Gimnasio gimnasio = new Gimnasio(nombres, MAX_USOS);
        Socio[] socios = new Socio[NUM_SOCIOS];
        Encargado encargado = new Encargado(gimnasio);

        encargado.start();
        for (int i = 0; i < socios.length; i++) {
            socios[i] = new Socio("Socio" + i, gimnasio);
            socios[i].start();
        }

        for (Socio s : socios)
            try {
                s.join();
            } catch (InterruptedException e) {
            }

        System.out.println("\n===== RESULTADOS =====");
        int usosMaquinas = 0;
        boolean limiteOk = true;
        for (Maquina m : gimnasio.getMaquinas()) {
            System.out.println(m.getNombre() + " (" + m.getUsos() + " / " + m.getMaxUsos() + "): " + m.getUsuarios());
            usosMaquinas += m.getUsos();
            if (m.getUsos() > m.getMaxUsos())
                limiteOk = false;
        }

        ArrayList<String> completos = new ArrayList<>(), incompletos = new ArrayList<>();
        int sumaSocios = 0;
        for (Socio s : socios) {
            sumaSocios += s.getHechos();
            if (s.getHechos() == Socio.NUM_EJERCICIOS)
                completos.add(s.getName());
            else
                incompletos.add(s.getName() + "(" + s.getHechos() + ")");
        }
        System.out.println("Completaron la rutina: " + completos);
        System.out.println("Se fueron sin terminar: " + incompletos);
        System.out.println("Usos de máquinas: " + usosMaquinas + " = ejercicios de socios: " + sumaSocios + " -> "
                + (usosMaquinas == sumaSocios ? "OK" : "ERROR"));
        System.out.println("Ninguna máquina supera " + MAX_USOS + " usos -> " + (limiteOk ? "OK" : "ERROR"));
    }
}
