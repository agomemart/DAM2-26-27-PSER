package llegajefe;

public class MainOficina {
    public static void main(String[] args) {
        final int NUM_EMPLEADOS = 10;
        Empleado[] empleados = new Empleado[NUM_EMPLEADOS];
        Oficina oficina = new Oficina();
        Jefe jefe = new Jefe(oficina);
        jefe.start();
        
        for (int i = 0; i < NUM_EMPLEADOS; i++) {
            empleados[i] = new Empleado(oficina);
            empleados[i].start();
        }
    }
}
