package example.speedw.models;

import javafx.scene.control.Alert;


import javafx.scene.control.TextArea;

/**
 * Implementacion concreta de {@link IAlertBox} para mostrar dialogos de alerta.
 * <p>
 * Esta clase proporciona una manera estandarizada y reutilizable de presentar
 * un cuadro de dialogo de informacion modal al usuario en toda la aplicacion.
 * Encapsula la configuracion y visualizacion de un {@link Alert} de JavaFX.
 *
 * @author Santiago Duque
 * @version 1.0
 * @since 2025-09-22
 */
public class AlertBox implements IAlertBox
{
    /**
     * Muestra un cuadro de dialogo de alerta de tipo informativo.
     * <p>
     * Este metodo construye y muestra una ventana de alerta modal (bloqueante)
     * que permanece en pantalla hasta que el usuario la cierra. El contenido
     * de la alerta se personaliza con los parametros proporcionados.
     *
     * @param title   El texto que se mostrara en la barra de titulo de la ventana.
     * @param message El mensaje principal o cuerpo del contenido de la alerta.
     * @param header  El texto del encabezado, usualmente un resumen o titulo corto.
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
