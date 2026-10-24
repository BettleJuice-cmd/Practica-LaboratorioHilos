
module ni.uam.edu.practicalaboratoriohilos {

    requires javafx.controls;
    requires javafx.fxml;

    opens ni.uam.edu.practicalaboratoriohilos.controller to javafx.fxml;

    exports ni.uam.edu.practicalaboratoriohilos.app;
}
