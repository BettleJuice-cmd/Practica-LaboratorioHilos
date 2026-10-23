
package ni.uam.edu.practicalaboratoriohilos.tareas;

public class Estacion1Prueba {

    public static void main(String[] args) throws InterruptedException {

        ProcesoVentanilla ventanillaA =
                new ProcesoVentanilla("Ventanilla A");

        ProcesoVentanilla ventanillaB =
                new ProcesoVentanilla("Ventanilla B");

        Thread hiloA = new Thread(ventanillaA, "Ventanilla-A");
        Thread hiloB = new Thread(ventanillaB, "Ventanilla-B");

        System.out.println("Iniciando procesamiento...");

        hiloA.start();
        hiloB.start();

        hiloA.join();
        hiloB.join();

        System.out.println("Ambas ventanillas finalizaron.");
    }
}
