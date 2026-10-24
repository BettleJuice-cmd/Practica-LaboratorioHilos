package ni.uam.edu.practicalaboratoriohilos.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProcesadorApp extends Application {

    @Override
    public void start(Stage ventana) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource( "/ni/uam/edu/practicalaboratoriohilos/procesador-view.fxml")
        );

        Scene escena = new Scene(loader.load(), 500, 400);

        ventana.setTitle("Procesamiento concurrente");
        ventana.setScene(escena);
        ventana.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
