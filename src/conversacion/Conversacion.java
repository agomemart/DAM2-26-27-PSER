package conversacion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Conversacion {
    private final List<String> lineas;
    private int siguiente = 0;

    public Conversacion(String fichero) throws IOException {
        lineas = Files.readAllLines(Paths.get(fichero));
    }

    public synchronized boolean decir(int id, String nombre) throws InterruptedException {
        while (siguiente < lineas.size() && siguiente % 2 != id)
            wait();

        if (siguiente >= lineas.size()) {
            notifyAll();
            return false;
        }

        System.out.println(nombre + ": " + lineas.get(siguiente));
        siguiente++;
        notifyAll();
        return siguiente < lineas.size();
    }
}