package hackers;

import java.util.Scanner;

public class AppHackers {
    static final int MAX_CHARS = 4;
    static final int NUM_HACKERS = 100;
    static final String STR_FIN = "fin";

    public static void main(String[] args) {
        TablaRainbow tabla = new TablaRainbow();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("\nInserta una contraseña (máx " + MAX_CHARS + " caracteres, a-z / 0-9) o '" + STR_FIN + "': ");
            String contrasena = sc.nextLine().trim();
            if (contrasena.equals(STR_FIN))
                break;
            if (!contrasena.matches("[a-z0-9]{1," + MAX_CHARS + "}")) {
                System.out.println("Contraseña no válida");
                continue;
            }

            Servidor servidor = new Servidor(contrasena);
            System.out.println("¡Servidor atacado! Hash filtrado: " + servidor.getHashFiltrado());

            Hacker[] hackers = new Hacker[NUM_HACKERS * 3];
            for (int i = 0; i < hackers.length; i++) {
                String nombre = "Hacker" + i;
                if (i < NUM_HACKERS)
                    hackers[i] = new HackerAlfabetico(nombre, servidor, tabla);
                else if (i < NUM_HACKERS * 2)
                    hackers[i] = new HackerNumerico(nombre, servidor, tabla);
                else
                    hackers[i] = new HackerAlfanumerico(nombre, servidor, tabla);
                hackers[i].start();
            }

            for (Hacker hacker : hackers)
                try {
                    hacker.join();
                } catch (InterruptedException ex) {
                }

            System.out.println("\n*** MEDALLA TIC para " + servidor.getGanador()
                    + " *** -> clave candidata: \"" + servidor.getClaveEncontrada() + "\"");
            System.out.println("Entrada rainbow más generada/consultada: " + tabla.getMasConsultada());
            System.out.println("Tamaño tabla rainbow: " + tabla.size() + " entradas");
        }
        System.out.println("Fin del programa.");
        sc.close();
    }
}