public class Aparcamiento {
    final static int PLAZAS_LIBRES = 10;
    static String[] aparcamiento = new String[PLAZAS_LIBRES];


    public static void main(String[] args) {
        for (int i = 0; i < aparcamiento.length; i++) {
            aparcamiento[i] = "| X | ";
            System.out.print(aparcamiento[i]);
        }


    }

    public void aparcar(Conductor c) {
        for (int i = 0; i < aparcamiento.length; i++) {
            if (aparcamiento[i].equals("| X | ")) {
                aparcamiento[i] = "| " + c.nombre + " | ";
                break;
            }
        }
    }
}
