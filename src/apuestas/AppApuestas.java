package apuestas;

public class AppApuestas {
    public static void main(String[] args) {
        final int NUM_EMPLEADOS = 10;
        Porra porra = new Porra();
        Empleado[] empleados = new Empleado[NUM_EMPLEADOS];
        for (int i = 0; i < NUM_EMPLEADOS; i++) {
            empleados[i] = new Empleado("Empleado " + (i + 1), porra);
            empleados[i].start();
        }
       
        for (Empleado e : empleados) {
            try {
                e.join();
            } catch (InterruptedException ex) {
            }
        }

        for (Apuesta a : porra.apuestas) {
            System.out.println(a);
        }

        System.out.println("Apuestas: " + porra.apuestas.size());
        System.out.println("Dinero acumulado: " + porra.dineroAcumulado + " euros");
    }
}
