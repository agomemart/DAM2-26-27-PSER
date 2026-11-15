package practicaex.recaudacionbenefica;

public class AppRecaudacion {
    final static int NUM_VOLUNTARIOS = 200;
    public static void main(String[] args) {
        Hucha hucha = new Hucha();
        Voluntario[] voluntarios = new Voluntario[NUM_VOLUNTARIOS];
        for (int i = 0; i < NUM_VOLUNTARIOS; i++) {
            voluntarios[i] = new Voluntario("Voluntario " + i, hucha);
            voluntarios[i].start();
        }

        for (Voluntario v : voluntarios) {
            try {
                v.join();
            } catch (InterruptedException e) {
            }
        }

        int sumaVoluntarios  = 0;

        for (Voluntario v : voluntarios) {
            sumaVoluntarios += v.getRecaudado();

            System.out.println("Total en la hucha: " + hucha.getDineroAcumulado());
            System.out.println("Suma de voluntarios: " + sumaVoluntarios);
            System.out.println(hucha.getDineroAcumulado() == sumaVoluntarios ? "CUADRA" : "NO CUADRA");
        }

        System.out.println("Total donado: " + hucha.getDineroAcumulado());
    }
    
}
