package llegajefe;

public class MainOficina {
    public static void main(String[] args) {
        final int NUM_EMPLEADOS = 10;
        final int NUM_JEFES = 1;
        Empleado[] empleados = new Empleado[NUM_EMPLEADOS];
        Jefe[] jefes = new Jefe[NUM_EMPLEADOS];

        Oficina oficina = new Oficina();
        for (int i = 0; i < NUM_EMPLEADOS; i++) {
            empleados[i] = new Empleado(oficina);
            empleados[i].start();
        }

        for (int i = 0; i < NUM_JEFES; i++) {
            jefes[i] = new Jefe(oficina);
            jefes[i].start();
        }

    }
}
