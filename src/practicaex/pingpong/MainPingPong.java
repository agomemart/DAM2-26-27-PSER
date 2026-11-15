package practicaex.pingpong;

public class MainPingPong {
    
    public static void main(String[] args) {
        Mesa mesa = new Mesa();
        Jugador j1 = new Jugador("Ping", 0, mesa);
        Jugador j2 = new Jugador("Pong", 1, mesa);
        j1.start();
        j2.start();
        try {
            j1.join();
            j2.join();
        } catch (InterruptedException e) {
        }
        System.out.println("Fin del partido");
        
    }
}
