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
 * Controlador para la vista de bienvenida de la aplicación (welcome.fxml).
 * <p>
 * Gestiona la lógica y los eventos de la pantalla inicial, incluyendo la carga
 * de imágenes, la visualización de instrucciones y el inicio de la transición
 * hacia la ventana principal del juego.
 *
 * @author Santiago Duque
 * @version 1.0
 * @since 2025-09-23
 */
public class WelcomeController
{
    /**
     * Contenedor para mostrar la imagen o ícono principal en la vista.
     */
    @FXML
    private ImageView imageIcon1;

    /**
     * Botón que, al ser presionado, muestra las instrucciones del juego.
     */
    @FXML
    private Button instructionButton;

    /**
     * Botón que inicia el juego y cambia a la ventana principal.
     */
    @FXML
    private Button startButton;

    /**
     * Referencia a la clase principal de la aplicación para permitir la comunicación.
     */
    private App mainApp;

    /**
     * Inicializa el controlador después de que su elemento raíz ha sido procesado.
     * <p>
     * Este método es llamado automáticamente por el {@code FXMLLoader} y se utiliza
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
     * Maneja el evento de clic del botón de instrucciones.
     * <p>
     * Muestra un cuadro de diálogo de alerta con las reglas y el objetivo del juego.
     */
    @FXML
    public void instButton()
    {
        AlertBox alertBox = new AlertBox();
        alertBox.showAlertBox("\uD835\uDC08\uD835\uDC0D\uD835\uDC12\uD835\uDC13\uD835\uDC11\uD835\uDC14\uD835\uDC02\uD835\uDC02\uD835\uDC08\uD835\uDC0E\uD835\uDC0D\uD835\uDC04\uD835\uDC12",
                "\uD835\uDC0E\uD835\uDC1B\uD835\uDC23\uD835\uDC1E\uD835\uDC2D\uD835\uDC22\uD835\uDC2F\uD835\uDC28: Escribe la frase que aparece en pantalla exactamente igual antes de que el tiempo se acabe.\n\n" +
                        "\uD835\uDC02\uD835\uDC28́\uD835\uDC26\uD835\uDC28 \uD835\uDC23\uD835\uDC2E\uD835\uDC20\uD835\uDC1A\uD835\uDC2B:\n" +
                        "- Escribe la frase en el área de texto.\n" +
                        "- Presiona Enter o el botón Validar para verificar.\n\n"+
                        "\uD835\uDC11\uD835\uDC04\uD835\uDC06\uD835\uDC0B\uD835\uDC00\uD835\uDC12:\n"+
                        "❌ Si el tiempo se acaba o escribes incorrectamente, pierdes y vuelves al Nivel 1.\n" +
                        "✅ Cada 5 niveles, el tiempo se reduce 2 segundos (desde 20s) hasta un mínimo de 6s.\n" +
                        "\uD83C\uDFC6 ¡Ganas al superar el nivel de 6 segundos!",
                "\uD83C\uDD42\uD83C\uDD3F\uD83C\uDD34\uD83C\uDD34\uD83C\uDD33 \uD83C\uDD46"
        );
    }

    /**
     * Maneja el evento de clic del botón para iniciar el juego.
     * <p>
     * Invoca el método {@code newWindow} de la clase principal {@link App} para
     * cerrar la ventana de bienvenida y abrir la ventana del juego.
     *
     * @param event El evento de acción generado por el clic del botón.
     */
    @FXML
    public void  gameWindowButton(ActionEvent event)
    {
        if (mainApp != null)
        {
            try
            {
                // Obtenemos la ventana actual desde el botón
                Stage currentStage = (Stage) startButton.getScene().getWindow();
                // Llamamos al método newWindow() de la clase App
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
     * Establece la referencia a la instancia principal de la aplicación.
     * <p>
     * Este método es utilizado por la clase {@link App} para "inyectar" su propia
     * instancia en este controlador, permitiendo que el controlador pueda invocar
     * métodos públicos de la clase principal, como {@code newWindow}.
     *
     * @param apl La instancia principal de la aplicación.
     */
    public void setApp(App apl)
    {
        mainApp = apl;
    }
}
