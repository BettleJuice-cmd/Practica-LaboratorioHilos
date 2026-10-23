package ni.uam.edu.practicalaboratoriohilos.controller;

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

    @FXML
    private void iniciarProcesamiento() {

        lblEstado.setText("Estado: Ejecutando");
        txtRegistro.appendText("Inicio del procesamiento\n");

        btnIniciar.setDisable(true);
        btnDetener.setDisable(false);
    }

    @FXML
    private void detenerProcesamiento() {

        lblEstado.setText("Estado: Detenido");
        txtRegistro.appendText("Procesamiento detenido\n");

        btnIniciar.setDisable(false);
        btnDetener.setDisable(true);
    }
}