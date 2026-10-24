package ni.uam.edu.practicalaboratoriohilos.tareas;
import ni.uam.edu.practicalaboratoriohilos.modelo.ContadorCompartido;
public class PruebaEstacion3 {
    public static void main(String[] args) {
        System.out.println("Iniciando 10 pruebas de concurrencia (1000 incrementos x 2 hilos)...");
        System.out.println("Esperado en cada prueba: 2000\n");

        for (int prueba = 1; prueba <= 10; prueba++) {
            ContadorCompartido contador = new ContadorCompartido();

            Runnable tareaInsegura = () -> { for (int i = 0; i < 1000; i++) contador.incrementarInseguro(); };
            Runnable tareaSincronizada = () -> { for (int i = 0; i < 1000; i++) contador.incrementarSincronizado(); };
            Runnable tareaAtomica = () -> { for (int i = 0; i < 1000; i++) contador.incrementarAtomico(); };

            Thread t1_ins = new Thread(tareaInsegura); Thread t2_ins = new Thread(tareaInsegura);
            Thread t1_sin = new Thread(tareaSincronizada); Thread t2_sin = new Thread(tareaSincronizada);
            Thread t1_ato = new Thread(tareaAtomica); Thread t2_ato = new Thread(tareaAtomica);

            t1_ins.start(); t2_ins.start();
            t1_sin.start(); t2_sin.start();
            t1_ato.start(); t2_ato.start();

            try {
                t1_ins.join(); t2_ins.join();
                t1_sin.join(); t2_sin.join();
                t1_ato.join(); t2_ato.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.printf("Prueba %2d | Inseguro: %4d | Sincronizado: %4d | Atómico: %4d%n",
                    prueba, contador.getTotalInseguro(), contador.getTotalSincronizado(), contador.getTotalAtomico());
        }
    }
}
