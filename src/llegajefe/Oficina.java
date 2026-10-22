package llegajefe;
public class Oficina {
    boolean estaElJefe = false;

    public synchronized void llegaEmpleado(Empleado e) {
        if (estaElJefe) {
            System.out.println(e.nombre + " ha llegado. Hola jefe!, me pongo a trabajar...");
        } else {
            System.out.println(e.nombre + " ha llegado. ZZZZZZ");
            while (!estaElJefe) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                }
            }
            System.out.println("(" + e.nombre + " desperezándose), buenos días jefe, aquí estoy trabajando");
        }
    }

    public synchronized void llegaElJefe(Jefe j) {
        estaElJefe = true;
        System.out.println("HA LLEGADO EL JEFE");
        notifyAll();
    }
}
