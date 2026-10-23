package ni.uam.edu.practicalaboratoriohilos.modelo;
import java.util.concurrent.atomic.AtomicInteger;
public class ContadorCompartido {
    private int totalInseguro = 0;
    private int totalSincronizado = 0;
    private final AtomicInteger totalAtomico = new AtomicInteger(0);

    public void incrementarInseguro() { totalInseguro++; }
    public int getTotalInseguro() { return totalInseguro; }

    public synchronized void incrementarSincronizado() { totalSincronizado++; }
    public synchronized int getTotalSincronizado() { return totalSincronizado; }

    public void incrementarAtomico() { totalAtomico.incrementAndGet(); }
    public int getTotalAtomico() { return totalAtomico.get(); }

}
