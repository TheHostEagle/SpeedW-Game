package example.speedw.controllers;

import example.speedw.App;
import example.speedw.models.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Controlador para la vista de bienvenida de la aplicacion (welcome.fxml).
 * <p>
 * Gestiona la logica y los eventos de la pantalla inicial, incluyendo la carga
 * de imagenes, la visualizacion de instrucciones y el inicio de la transicion
 * hacia la ventana principal del juego.
 *
 * @author Santiago Duque
 * @version 1.0
 * @since 2025-09-22
 */
public class WelcomeController
{
    /**
     * Contenedor para mostrar la imagen o icono principal en la vista.
     */
    @FXML
    private ImageView imageIcon1;

    /**
     * Boton que, al ser presionado, muestra las instrucciones del juego.
     */
    @FXML
    private Button instructionButton;

    /**
     * Boton que inicia el juego y cambia a la ventana principal.
     */
    @FXML
    private Button startButton;

    /**
     * Referencia a la clase principal de la aplicacion para permitir la comunicacion.
     */
    private App mainApp;

    /**
     * Inicializa el controlador despues de que su elemento raiz ha sido procesado.
     * <p>
     * Este metodo es llamado automaticamente por el {@code FXMLLoader} y se utiliza
     * para realizar configuraciones iniciales, como cargar la imagen principal
     * en el {@code ImageView}.
     */
    @FXML
    public void initialize()
    {
        Image image1 = new Image((getClass().getResourceAsStream("/example/speedw/images/image1.jpg")));
        imageIcon1.setImage(image1);
    }

    /**
     * Maneja el evento de clic del boton de instrucciones.
     * <p>
     * Muestra un cuadro de dialogo de alerta con las reglas y el objetivo del juego.
     */
    @FXML
    public void instButton()
    {
        AlertBox alertBox = new AlertBox();
        alertBox.showAlertBox("INSTRUCCIONES",
                "OBJETIVO: Escribe la frase que aparece en pantalla exactamente igual antes de que el tiempo se acabe.\n\n" +
                        "COMO JUGAR:\n" +
                        "- Escribe la frase en el area de texto.\n" +
                        "- Presiona Enter o el boton Validar para verificar.\n\n"+
                        "REGLAS:\n"+
                        "\u2717 Si el tiempo se acaba o escribes incorrectamente, pierdes y vuelves al Nivel 1.\n" +
                        "✅ Cada 5 niveles, el tiempo se reduce 2 segundos (desde 20s) hasta un mínimo de 6s.\n" +
                        "\u272A ¡Ganas al superar el nivel de 6 segundos!",
                "|S|P|E|E|D|  |W|"
        );
    }

    /**
     * Maneja el evento de clic del boton para iniciar el juego.
     * <p>
     * Invoca el metodo {@code newWindow} de la clase principal {@link App} para
     * cerrar la ventana de bienvenida y abrir la ventana del juego.
     *
     * @param event El evento de accion generado por el clic del boton.
     */
    @FXML
    public void  gameWindowButton(ActionEvent event)
    {
        if (mainApp != null)
        {
            try
            {
                // Obtenemos la ventana actual desde el boton
                Stage currentStage = (Stage) startButton.getScene().getWindow();
                // Llamamos al metodo newWindow() de la clase App
                mainApp.newWindow(currentStage);
            }
            catch (IOException e)
            {
                System.err.println("No se pudo abrir la ventana del juego.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Establece la referencia a la instancia principal de la aplicacion.
     * <p>
     * Este metodo es utilizado por la clase {@link App} para "inyectar" su propia
     * instancia en este controlador, permitiendo que el controlador pueda invocar
     * metodos publicos de la clase principal, como {@code newWindow}.
     *
     * @param apl La instancia principal de la aplicacion.
     */
    public void setApp(App apl)
    {
        mainApp = apl;
    }
}
