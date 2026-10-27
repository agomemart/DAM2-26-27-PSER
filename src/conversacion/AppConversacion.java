package conversacion;

import java.io.IOException;

public class AppConversacion {
    public static void main(String[] args) {
        try {
            Conversacion conversacion = new Conversacion("conversacion.txt");
            Hilo[] hilos = new Hilo[2];
            for (int i = 0; i < hilos.length; i++) {
                hilos[i] = new Hilo(i, conversacion);
                hilos[i].start();
            }
            for (Hilo h : hilos)
                h.join();
            System.out.println("--- Fin de la conversación ---");
        } catch (IOException e) {
            System.out.println("No se pudo leer conversacion.txt: " + e.getMessage());
        } catch (InterruptedException e) {
        }
    }
}
