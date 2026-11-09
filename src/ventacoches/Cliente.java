package ventacoches;

import java.util.Random;

public class Cliente extends Thread {
    static final int TIEMPO_MAX_PRUEBA = 20;
    static Random random = new Random();

    private final Concesionario concesionario;

    public Cliente(String nombre, Concesionario concesionario) {
        super(nombre);
        this.concesionario = concesionario;
    }

    @Override
    public void run() {
        try {
            boolean comprado = false;
            while (!comprado) {
                Coche coche = concesionario.pedirCita();
                if (coche == null)
                    break;

                if (VentaCoches.VerDetalle)
                    System.out.println(getName() + " está probando " + coche);

                Thread.sleep(random.nextInt(TIEMPO_MAX_PRUEBA + 1));
                int visitas = concesionario.leerCartel(coche);

                comprado = random.nextInt(100) < visitas;

                if (VentaCoches.VerDetalle)
                    System.out.println(getName() + (comprado ? " COMPRA " : " no se convence con ")
                            + coche + " (" + visitas + "%)");

                concesionario.terminarPrueba(coche, this, comprado);
            }
        } catch (InterruptedException ex) {
        }
    }
}