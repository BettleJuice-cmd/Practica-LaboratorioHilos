
# Práctica de Laboratorio: Gestión de Hilos y Concurrencia

Proyecto académico en **Java 21** y **JavaFX** para explorar la ejecución concurrente, el ciclo de vida de los hilos y la sincronización de recursos compartidos.

## Descripción

La aplicación simula **dos ventanillas** que procesan unidades de trabajo en paralelo. Su interfaz permite iniciar o detener el procesamiento, consultar los estados de las ventanillas y visualizar un contador compartido. Además, incluye tres ejercicios independientes por consola.

## Objetivos

- Crear y ejecutar tareas con `Thread` y `Runnable`.
- Diferenciar `start()` de una invocación directa a `run()`.
- Observar estados de los hilos mediante `Thread.getState()`.
- Reconocer condiciones de carrera en datos compartidos.
- Comparar incrementos inseguros, `synchronized` y `AtomicInteger`.
- Practicar la interrupción de hilos y la actualización de interfaces JavaFX.

## Funcionalidades

### Aplicación gráfica

La ventana **Gestión de Procesamiento y Hilos** ofrece botones **Iniciar** y **Detener**, un indicador de estado, un contador y un área de registro de eventos generales.

- Al iniciar, se crean dos hilos: `Hilo-VentanillaA` y `Hilo-VentanillaB`.
- Cada ventanilla procesa hasta **50 unidades**, con una pausa simulada de **250 ms** por unidad.
- Ambas incrementan el mismo contador mediante un método `synchronized`; si completan todas las unidades, el total esperado es **100**.
- Los mensajes de cada unidad procesada se muestran en la **consola**, mientras que la interfaz refleja el contador y los estados.
- Al detener, se solicita la finalización de las tareas y se interrumpen los hilos. El controlador espera su terminación mediante `join()` sin bloquear el hilo gráfico.
- Las actualizaciones visuales se programan con `Platform.runLater()`.

### Estación 1 — `start()` frente a `run()`

**Archivos:** `Estacion1Prueba.java` y `ProcesoVentanilla.java`.

Compara la ejecución de dos ventanillas:

- `start()` inicia un hilo independiente por tarea, permitiendo concurrencia.
- Llamar directamente a `run()` ejecuta las tareas secuencialmente en el hilo llamador.

Cada tarea realiza cinco iteraciones con pausas de **500 ms**. La prueba imprime los tiempos de cada enfoque en consola.

### Estación 2 — Monitoreo de estados

**Archivo:** `MonitorHilos.java`.

Un monitor ejecutado en su propio hilo consulta periódicamente `Thread.getState()` y registra únicamente los cambios de estado detectados. Su demostración independiente permite observar estados como `NEW`, `RUNNABLE`, `TIMED_WAITING` y `TERMINATED`.

> Java define seis estados: `NEW`, `RUNNABLE`, `BLOCKED`, `WAITING`, `TIMED_WAITING` y `TERMINATED`. El ejemplo no fuerza los seis y, al muestrear cada 50 ms, podría no detectar estados breves.

### Estación 3 — Contador compartido y sincronización

**Archivos:** `ContadorCompartido.java` y `PruebaEstacion3.java`.

Se realizan **10 pruebas** para comparar tres formas de incrementar un contador desde dos hilos (1,000 incrementos por hilo; **2,000 esperados**):

| Método | Implementación | Resultado esperado |
| --- | --- | --- |
| Inseguro | `totalInseguro++` | Puede perder incrementos por condiciones de carrera. |
| Sincronizado | `synchronized` | Conserva los 2,000 incrementos. |
| Atómico | `AtomicInteger.incrementAndGet()` | Conserva los 2,000 incrementos. |

El contador inseguro **no necesariamente falla en todas las ejecuciones**; los resultados dependen de la planificación de hilos.

## Tecnologías

- **Java 21**
- **JavaFX 21.0.6** (`javafx-controls` y `javafx-fxml`)
- **Maven** con Maven Wrapper
- **FXML** para la interfaz gráfica
- **JUnit Jupiter 5.12.1**, configurado como dependencia de pruebas

## Requisitos

- JDK **21** instalado y configurado.
- Entorno de escritorio compatible con JavaFX.
- Conexión a internet para descargar dependencias en la primera ejecución, si no están en caché.

El proyecto incluye `mvnw` y `mvnw.cmd`, por lo que no se requiere instalar Maven por separado.

## Instalación y ejecución

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/BettleJuice-cmd/Practica-LaboratorioHilos.git
   cd Practica-LaboratorioHilos
   ```

2. Ejecutar la aplicación gráfica:

   **Windows (PowerShell o CMD):**

   ```powershell
   .\mvnw.cmd clean javafx:run
   ```

   **Linux/macOS:**

   ```bash
   ./mvnw clean javafx:run
   ```

También se puede importar el proyecto Maven en **IntelliJ IDEA**, configurar el JDK 21 y ejecutar `ni.uam.edu.practicalaboratoriohilos.app.ProcesadorApp`.

### Ejecutar las demostraciones por consola

Desde IntelliJ IDEA, abrir y ejecutar el método `main()` de una de estas clases:

| Clase | Demostración |
| --- | --- |
| `Estacion1Prueba` | Comparación de `start()` con `run()`. |
| `MonitorHilos` | Observación de cambios de estado de un hilo. |
| `PruebaEstacion3` | Comparación de contadores inseguros, sincronizados y atómicos. |

Estas demostraciones son independientes de la interfaz JavaFX.

## Estructura del proyecto

```text
Practica-LaboratorioHilos/
├── .mvn/wrapper/                        # Maven Wrapper
├── mvnw
├── mvnw.cmd
├── pom.xml                              # Configuración Maven
└── src/main/
    ├── java/
    │   ├── module-info.java
    │   └── ni/uam/edu/practicalaboratoriohilos/
    │       ├── app/
    │       │   └── ProcesadorApp.java         # Inicio de JavaFX
    │       ├── controller/
    │       │   └── ProcesadorController.java  # Control de la interfaz
    │       ├── modelo/
    │       │   └── ContadorCompartido.java    # Contadores compartidos
    │       └── tareas/
    │           ├── ControlProcesamiento.java  # Procesamiento de ventanillas
    │           ├── ProcesoVentanilla.java      # Tarea Runnable de prueba
    │           ├── Estacion1Prueba.java        # start() versus run()
    │           ├── MonitorHilos.java          # Monitor de estados
    │           └── PruebaEstacion3.java        # Pruebas de concurrencia
    └── resources/ni/uam/edu/practicalaboratoriohilos/
        ├── procesador-view.fxml               # Vista JavaFX
        └── util/style.css                     # Estilos incluidos
```

## Conceptos principales

**Concurrencia:** varias tareas progresan durante períodos de tiempo superpuestos.

**Condición de carrera:** el resultado depende del orden de acceso simultáneo a un recurso.

**`synchronized`:** protege secciones críticas mediante un monitor.

**`AtomicInteger`:** proporciona operaciones atómicas sobre enteros.

**`interrupt()`:** señala una solicitud de interrupción que el código debe gestionar.

**`join()`:** espera que otro hilo termine.

**`Platform.runLater()`:** programa cambios en el hilo de JavaFX.

> **Nota:** el orden de los mensajes y los tiempos de ejecución no son deterministas. Este repositorio tiene fines académicos y de experimentación con hilos en Java.

---

**Proyecto académico de programación concurrente en Java.**
