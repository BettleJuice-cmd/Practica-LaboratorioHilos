package ni.uam.edu.practicalaboratoriohilos.controller;

import ni.uam.edu.practicalaboratoriohilos.modelo.ContadorCompartido;
import ni.uam.edu.practicalaboratoriohilos.tareas.ControlProcesamiento;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ProcesadorController {

    @FXML
    private Label lblEstado;

    @FXML
    private Label lblContador;

    @FXML
    private Button btnIniciar;

    @FXML
    private Button btnDetener;

    @FXML
    private TextArea txtRegistro;

    private ContadorCompartido contadorCompartido;
    private ControlProcesamiento tareaA;
    private ControlProcesamiento tareaB;
    private Thread hiloA;
    private Thread hiloB;

    @FXML
    public void initialize() {
        if (btnDetener != null) {
            btnDetener.setDisable(true);
        }
    }

    @FXML
    private void iniciarProcesamiento() {
        contadorCompartido = new ContadorCompartido();
        btnIniciar.setDisable(true);
        btnDetener.setDisable(false);
        txtRegistro.appendText("--- Inicio del procesamiento ---\n");

        tareaA = new ControlProcesamiento("Ventanilla-A", contadorCompartido, this::actualizarUI);
        tareaB = new ControlProcesamiento("Ventanilla-B", contadorCompartido, this::actualizarUI);

        hiloA = new Thread(tareaA, "Hilo-VentanillaA");
        hiloB = new Thread(tareaB, "Hilo-VentanillaB");

        hiloA.start();
        hiloB.start();
    }

    @FXML
    private void detenerProcesamiento() {
        txtRegistro.appendText("Solicitud de detención enviada...\n");
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
            Platform.runLater(() -> {
                btnIniciar.setDisable(false);
                lblEstado.setText("Estado: Detenido");
                txtRegistro.appendText("--- Procesamiento detenido de forma segura ---\n");
            });
        }).start();
    }

    public void actualizarUI() {
        if (tareaA != null && tareaB != null) {
            lblEstado.setText("Ventanilla A: " + tareaA.getEstado() + " | Ventanilla B: " + tareaB.getEstado());
        }
        if (contadorCompartido != null) {
            lblContador.setText("Contador: " + contadorCompartido.getTotalSincronizado());
        }
    }
}