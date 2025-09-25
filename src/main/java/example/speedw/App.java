package example.speedw;

import example.speedw.controllers.GameController;
import example.speedw.controllers.WelcomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación SpeedW.
 * <p>
 * Esta clase extiende {@link Application} y sirve como el punto de entrada para
 * la interfaz gráfica de JavaFX. Se encarga de cargar la ventana de bienvenida
 * inicial y de gestionar la transición a la ventana principal del juego.
 *
 * @author Santiago Duque
 * @version 1.0
 * @since 2025-09-23
 */
public class App extends Application
{
    /**
     * Punto de entrada principal para la aplicación JavaFX.
     * <p>
     * Este método es llamado por el runtime de JavaFX después de que el método
     * {@code launch()} es invocado. Configura y muestra la ventana principal
     * (Stage), carga la vista de bienvenida desde {@code welcome.fxml},
     * y establece la comunicación con su controlador.
     *
     * @param stage El escenario principal (ventana) proporcionado por JavaFX.
     * @throws IOException Si ocurre un error al cargar el archivo FXML de bienvenida.
     */
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/welcome.fxml"));
        Parent root  = fxmlLoader.load();
        WelcomeController wcontroller = fxmlLoader.getController();
        wcontroller.setApp(this);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();
    }

    /**
     * Carga y muestra la ventana del juego, cerrando la ventana actual.
     * <p>
     * Este método gestiona la transición desde la ventana de bienvenida (o cualquier otra)
     * hacia la ventana principal del juego. Carga la vista desde {@code game.fxml},
     * la coloca en un nuevo Stage, y cierra la ventana que se le pasa como parámetro.
     *
     * @param ActualWindow La ventana (Stage) actual que debe ser cerrada.
     * @throws IOException Si ocurre un error al cargar el archivo FXML del juego.
     */
    public void newWindow(Stage ActualWindow) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/game.fxml"));
        Parent root  = fxmlLoader.load();

        GameController gameController = fxmlLoader.getController();
        gameController.setApp(this);
        Scene scene = new Scene(root);
        Stage gameStage = new Stage();
        gameStage.setScene(scene);
        gameStage.setWidth(800);
        gameStage.setHeight(600);
        gameStage.show();
        ActualWindow.close();
    }

    public void openWelcomeWindow(Stage currentGameStage) throws IOException
    {
        // Carga la vista de bienvenida
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/welcome.fxml"));
        Parent root = fxmlLoader.load();

        // Obtiene su controlador y le pasa la referencia de App (importante para la consistencia)
        WelcomeController wcontroller = fxmlLoader.getController();
        wcontroller.setApp(this);

        // Muestra la nueva ventana de bienvenida
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();

        // Cierra la ventana del juego que se le pasó como parámetro
        currentGameStage.close();
    }

}
