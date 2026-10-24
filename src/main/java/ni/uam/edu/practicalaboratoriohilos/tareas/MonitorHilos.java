package ni.uam.edu.practicalaboratoriohilos.tareas;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class MonitorHilos implements Runnable {

    private final Thread hiloObservado;
    private final Consumer<String> registro;
    private volatile boolean monitoreando = true;
    private Thread.State ultimoEstado;

    public MonitorHilos(Thread hiloObservado, Consumer<String> registro) {
        this.hiloObservado = Objects.requireNonNull(hiloObservado);
        this.registro = Objects.requireNonNull(registro);
    }

    // Registrar el estado inicial (NEW).
    public void registrarEstadoInicial() {
        registrarCambio();
    }

    // Iniciar el monitor en un hilo independiente.
    public Thread iniciarMonitoreo() {
        Thread monitor = new Thread(
                this, "Monitor-" + hiloObservado.getName()
        );

        monitor.setDaemon(true);
        monitor.start();
        return monitor;
    }

    // Solicitar la detención del monitoreo.
    public void detenerMonitoreo() {
        monitoreando = false;
    }

    @Override
    public void run() {
        while (monitoreando) {
            registrarCambio();

            if (hiloObservado.getState() == Thread.State.TERMINATED) {
                break;
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    // Registrar únicamente los cambios de estado.
    private void registrarCambio() {
        Thread.State estado = hiloObservado.getState();

        if (estado != ultimoEstado) {
            ultimoEstado = estado;

            registro.accept(
                    hiloObservado.getName() + " -> " + estado
            );
        }
    }

    // Prueba independiente del monitor.
    public static void main(String[] args)
            throws InterruptedException {

        Runnable tareaDemostracion = () -> {

            // Simulación de trabajo activo.
            long fin = System.nanoTime()
                    + TimeUnit.MILLISECONDS.toNanos(500);

            while (System.nanoTime() < fin) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }

                Thread.onSpinWait();
            }

            // Simulación de espera temporal.
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        };

        Thread ventanilla = new Thread(
                tareaDemostracion, "Ventanilla-A"
        );

        MonitorHilos monitor = new MonitorHilos(
                ventanilla, System.out::println
        );

        monitor.registrarEstadoInicial();

        Thread hiloMonitor = monitor.iniciarMonitoreo();

        ventanilla.start();

        // Estamos fuera del hilo gráfico de JavaFX.
        ventanilla.join();
        hiloMonitor.join();

        System.out.println("Experimento finalizado.");
    }
}
