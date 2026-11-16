package practicaex.gimnasio;

import java.util.ArrayList;

public class Maquina {
    private String nombre;
    private Socio socio = null;
    private final int maxUsos;
    private int usos = 0;
    private final ArrayList<String> usuarios = new ArrayList<>();

    public Maquina(String nombre, int maxUsos) {
        this.nombre = nombre;
        this.maxUsos = maxUsos;
    }

    public synchronized boolean usar(Socio s) {
        while (socio != null && usos < maxUsos)
            try {
                wait();
            } catch (InterruptedException e) {
            }
        if (usos >= maxUsos) {
            System.out.println(s.getName() + " ve que " + nombre + " está ESTROPEADA y busca otra");
            return false;
        }
        socio = s;
        usos++;
        usuarios.add(s.getName());
        System.out.println(s.getName() + " empieza en " + nombre + " (uso " + usos + "/" + maxUsos + ")");
        return true;
    }

    public synchronized void liberar(Socio s) {
        socio = null;
        System.out.println(s.getName() + " deja " + nombre + (usos >= maxUsos ? " y se ESTROPEA" : ""));
        notifyAll();
    }

    public synchronized boolean estaEstropeada() {
        return usos >= maxUsos && socio == null;
    }

    public synchronized boolean admiteMasUsos() {
        return usos < maxUsos;
    }

    public synchronized int getUsos() {
        return usos;
    }

    public synchronized ArrayList<String> getUsuarios() {
        return usuarios;
    }

    public int getMaxUsos() {
        return maxUsos;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public synchronized String toString() {
        if (usos >= maxUsos && socio == null)
            return nombre + "[ESTROPEADA]";
        return nombre + "[" + (socio == null ? "libre" : socio.getName()) + "]";
    }
}
