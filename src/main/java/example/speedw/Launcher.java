package example.speedw;

import javafx.application.Application;

/**
 * Clase lanzadora para la aplicación SpeedW.
 * <p>
 * El único propósito de esta clase es proporcionar un método {@code main} que
 * invoca a {@link Application#launch(Class, String...)}, iniciando así el
 - * ciclo de vida de la aplicación JavaFX definida en la clase {@link App}.
 * <p>
 * Este enfoque de separación es una solución común para problemas de empaquetado
 * al crear archivos JAR ejecutables para aplicaciones JavaFX.
 *
 * @author Santiago Duque
 * @version 1.0
 * @since 2025-09-23
 */
public class Launcher
{
    /**
     * El punto de entrada principal del programa.
     * <p>
     * Lanza la aplicación JavaFX pasando la clase principal {@link App} y los
     * argumentos de la línea de comandos al método de lanzamiento de JavaFX.
     *
     * @param args los argumentos de la línea de comandos pasados al programa.
     */
    public static void main(String[] args) {
        Application.launch(App.class, args);
    }
}
