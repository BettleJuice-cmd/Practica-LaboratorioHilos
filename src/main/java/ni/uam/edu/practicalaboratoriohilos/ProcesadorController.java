package ni.uam.edu.practicalaboratoriohilos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ProcesadorController {

    @FXML private Button btnIniciar;
    @FXML private Button btnDetener;
    @FXML private Label lblEstadoVentanillaA;
    @FXML private Label lblEstadoVentanillaB;
    @FXML private Label lblContador;
    @FXML private TextArea txtAreaLog;

    private ContadorCompartido contadorCompartido;
    private ControlProcesamiento tareaA;
    private ControlProcesamiento tareaB;
    private Thread hiloA;
    private Thread hiloB;

    @FXML
    public void initialize() {
        btnDetener.setDisable(true);
    }

    @FXML
    private void handleIniciar() {
        contadorCompartido = new ContadorCompartido();
        btnIniciar.setDisable(true);
        btnDetener.setDisable(false);
        txtAreaLog.appendText("--- Iniciando sesión de procesamiento ---\n");

        tareaA = new ControlProcesamiento("Ventanilla-A", contadorCompartido, this::actualizarUI);
        tareaB = new ControlProcesamiento("Ventanilla-B", contadorCompartido, this::actualizarUI);

        hiloA = new Thread(tareaA, "Hilo-VentanillaA");
        hiloB = new Thread(tareaB, "Hilo-VentanillaB");

        hiloA.start();
        hiloB.start();
    }

    @FXML
    private void handleDetener() {
        txtAreaLog.appendText("Solicitud de detención enviada...\n");
        if (tareaA != null) tareaA.detener();
        if (tareaB != null) tareaB.detener();

        if (hiloA != null) hiloA.interrupt();
        if (hiloB != null) hiloB.interrupt();

        btnDetener.setDisable(true);

        new Thread(() -> {
            try {
                if (hiloA != null) hiloA.join();
                if (hiloB != null) hiloB.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            javafx.application.Platform.runLater(() -> {
                btnIniciar.setDisable(false);
                txtAreaLog.appendText("--- Todos los hilos han terminado de forma segura ---\n");
            });
        }).start();
    }

    public void actualizarUI() {
        if (tareaA != null) {
            lblEstadoVentanillaA.setText("Ventanilla A: " + tareaA.getEstado());
        }
        if (tareaB != null) {
            lblEstadoVentanillaB.setText("Ventanilla B: " + tareaB.getEstado());
        }
        if (contadorCompartido != null) {
            lblContador.setText("Total Operaciones: " + contadorCompartido.getTotal());
        }
    }
}