package example.speedw.models;

import javafx.scene.control.Alert;


import javafx.scene.control.TextArea;

/**
 * Implementación concreta de {@link IAlertBox} para mostrar diálogos de alerta.
 * <p>
 * Esta clase proporciona una manera estandarizada y reutilizable de presentar
 * un cuadro de diálogo de información modal al usuario en toda la aplicación.
 * Encapsula la configuración y visualización de un {@link Alert} de JavaFX.
 *
 * @author Santiago Duque
 * @version 1.0
 * @since 2025-09-23
 */
public class AlertBox implements IAlertBox
{
    /**
     * Muestra un cuadro de diálogo de alerta de tipo informativo.
     * <p>
     * Este método construye y muestra una ventana de alerta modal (bloqueante)
     * que permanece en pantalla hasta que el usuario la cierra. El contenido
     * de la alerta se personaliza con los parámetros proporcionados.
     *
     * @param title   El texto que se mostrará en la barra de título de la ventana.
     * @param message El mensaje principal o cuerpo del contenido de la alerta.
     * @param header  El texto del encabezado, usualmente un resumen o título corto.
     */
    @Override
    public void showAlertBox(String title, String message, String header)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        alert.getDialogPane().setContent(textArea);
        alert.getDialogPane().setPrefSize(400, 450);
        alert.showAndWait();
    }
}
