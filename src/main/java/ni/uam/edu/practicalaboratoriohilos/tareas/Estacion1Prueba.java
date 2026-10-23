
package ni.uam.edu.practicalaboratoriohilos.tareas;

public class Estacion1Prueba {

    public static void main(String[] args) throws InterruptedException {


        System.out.println("===== PRUEBA CON START() =====");
        Thread hiloA = new Thread(
                new ProcesoVentanilla("Ventanilla A"),
                "Ventanilla-A"
        );

        Thread hiloB = new Thread(
                new ProcesoVentanilla("Ventanilla B"),
                "Ventanilla-B"
        );

        long inicio = System.currentTimeMillis();

        hiloA.start();
        hiloB.start();

        hiloA.join();
        hiloB.join();

        long fin = System.currentTimeMillis();

        System.out.println("Tiempo con start(): " + (fin - inicio) + " ms");

        System.out.println("\n===== PRUEBA CON RUN() =====");

        Thread hiloC = new Thread(
                new ProcesoVentanilla("Ventanilla A"),
                "Ventanilla-A"
        );

        Thread hiloD = new Thread(
                new ProcesoVentanilla("Ventanilla B"),
                "Ventanilla-B"
        );

        inicio = System.currentTimeMillis();

        hiloC.run();
        hiloD.run();

        fin = System.currentTimeMillis();

        System.out.println("Tiempo con run(): " + (fin - inicio) + " ms");
    }


}
