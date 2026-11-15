package practicaex.carrera;

public class MainCarrera {
    public static void main(String[] args) {
        final int NUM_CORREDORES = 8;
        Carrera carrera = new Carrera();
        Corredor[] corredores = new Corredor[NUM_CORREDORES];
        Juez juez = new Juez(carrera);

        for (int i = 0; i < corredores.length; i++) {
            corredores[i] = new Corredor("Corredor " + i, carrera);
            corredores[i].start();
        }

        juez.start();

        for (Corredor c : corredores) {
            try {
                c.join();
            } catch (InterruptedException e) {
            }
        }

        try {
            juez.join();
        } catch (InterruptedException e) {
        }
        System.out.println("Todos han salido");
    }
}
