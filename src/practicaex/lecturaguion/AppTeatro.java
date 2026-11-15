package practicaex.lecturaguion;

public class AppTeatro {
    public static void main(String[] args) {
        String[] nombres = {"ANA", "LUIS", "MARTA"};
        Actor[] actores = new Actor[nombres.length];
        String[] guion = {
                "ANA: ¿Alguien ha visto mis llaves?",
                "LUIS: Las tenías hace un minuto.",
                "ANA: Hace un minuto también tenía paciencia.",
                "MARTA: ¿Habéis mirado en la nevera?",
                "LUIS: ¿Por qué iban a estar en la nevera?",
                "MARTA: Porque el mando de la tele estaba ahí ayer.",
                "MARTA: Y el móvil de Luis el martes.",
                "LUIS: Eso no fue culpa mía.",
                "ANA: ¡Aquí están! En el bolsillo.",
                "MARTA: Te lo dije, en el último sitio donde miras.",
                "LUIS: Siempre es el último sitio donde miras.",
                "ANA: Fin."
        };
        Teatro teatro = new Teatro(guion, nombres.length);

        for (int i = 0; i < actores.length; i++) {
            actores[i] = new Actor(nombres[i], teatro);
            actores[i].start();
        }

        for (Actor a : actores) {
            try {
                a.join();
            } catch (InterruptedException e) {
            }
        }

        System.out.println("Fin del programa");
    }
}
