package ni.uam.edu.practicalaboratoriohilos;

public class ContadorCompartido {
    private int total = 0;

    // Sincronizado para evitar condiciones de carrera (Estación 3)
    public synchronized void incrementar() {
        total++;
    }

    public synchronized int getTotal() {
        return total;
    }
}