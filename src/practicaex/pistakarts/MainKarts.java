package practicaex.pistakarts;

public class MainKarts {
    public static void main(String[] args) {
        final int NUM_KARTS = 4;
        final int NUM_PILOTOS = 20;
        Pista pista = new Pista(NUM_KARTS);
        Encargado encargado = new Encargado(pista);
        Piloto[] pilotos = new Piloto[NUM_PILOTOS];
        Marcador marcador = new Marcador();

        for (int i = 0; i < pilotos.length; i++) {
            pilotos[i] = new Piloto("Piloto " + i, pista, marcador);
            pilotos[i].start();
        }
        encargado.start();

        for (int i = 0; i < pilotos.length; i++) {
            try {
                pilotos[i].join();
            } catch (InterruptedException e) {
            }
        }

        try {
            encargado.join();
        } catch (InterruptedException e) {
        }

        System.out.println("\n--- RESULTADOS ---");
        System.out.println("Total vueltas: " + marcador.getTotalVueltas()
                + " (esperadas " + NUM_PILOTOS * Piloto.NUM_VUELTAS + ")");

        if (marcador.getMejoresPilotos().size() == 1)
            System.out.println("Mejor vuelta: " + marcador.getMejorTiempo() + " ms - "
                    + marcador.getMejoresPilotos().get(0));
        else
            System.out.println("Mejor vuelta: " + marcador.getMejorTiempo() + " ms - EMPATE: "
                    + marcador.getMejoresPilotos());

        System.out.println("Fin del programa");

    }
}
