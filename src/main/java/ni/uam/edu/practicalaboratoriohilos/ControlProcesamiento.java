package ni.uam.edu.practicalaboratoriohilos;

import javafx.application.Platform;

public class ControlProcesamiento implements Runnable {
    private volatile boolean activo = true;
    private final String nombreVentanilla;
    private final ContadorCompartido contador;
    private final Runnable actualizarVistaCallback;

    public enum EstadoVentanilla {
        EN_ESPERA, EJECUTANDO, DETENIENDO, DETENIDO, FINALIZADO
    }

    private EstadoVentanilla estado = EstadoVentanilla.EN_ESPERA;

    public ControlProcesamiento(String nombreVentanilla, ContadorCompartido contador, Runnable actualizarVistaCallback) {
        this.nombreVentanilla = nombreVentanilla;
        this.contador = contador;
        this.actualizarVistaCallback = actualizarVistaCallback;
    }

    public void detener() {
        this.activo = false;
        setEstado(EstadoVentanilla.DETENIENDO);
    }

    public EstadoVentanilla getEstado() {
        return estado;
    }

    private void setEstado(EstadoVentanilla nuevoEstado) {
        this.estado = nuevoEstado;
        if (actualizarVistaCallback != null) {
            Platform.runLater(actualizarVistaCallback);
        }
    }

    @Override
    public void run() {
        setEstado(EstadoVentanilla.EJECUTANDO);

        try {
            for (int i = 1; i <= 50 && activo && !Thread.currentThread().isInterrupted(); i++) {
                Thread.sleep(250);

                contador.incrementar();

                if (actualizarVistaCallback != null) {
                    Platform.runLater(actualizarVistaCallback);
                }

                System.out.println(nombreVentanilla + " procesó la unidad " + i);
            }

            if (activo) {
                setEstado(EstadoVentanilla.FINALIZADO);
            } else {
                setEstado(EstadoVentanilla.DETENIDO);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            setEstado(EstadoVentanilla.DETENIDO);
        }
    }
}