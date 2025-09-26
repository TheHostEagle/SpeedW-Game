package example.speedw;

import javafx.application.Application;

/**
 * Clase lanzadora para la aplicacion SpeedW.
 * <p>
 * El unico proposito de esta clase es proporcionar un metodo {@code main} que
 * invoca a {@link Application#launch(Class, String...)}, iniciando asi el
 - * ciclo de vida de la aplicacion JavaFX definida en la clase {@link App}.
 * <p>
 * Este enfoque de separacion es una solucion comun para problemas de empaquetado
 * al crear archivos JAR ejecutables para aplicaciones JavaFX.
 *
 * @author Santiago Duque
 * @version 1.0
 * @since 2025-09-22
 */
public class Launcher
{
    /**
     * El punto de entrada principal del programa.
     * <p>
     * Lanza la aplicacion JavaFX pasando la clase principal {@link App} y los
     * argumentos de la linea de comandos al metodo de lanzamiento de JavaFX.
     *
     * @param args los argumentos de la linea de comandos pasados al programa.
     */
    public static void main(String[] args) {
        Application.launch(App.class, args);
    }
}
