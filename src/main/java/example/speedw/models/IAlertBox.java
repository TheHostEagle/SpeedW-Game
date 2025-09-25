package example.speedw.models;

import javafx.fxml.FXML;

/**
 * Define el contrato para cualquier clase que muestre cuadros de diálogo de alerta.
 * <p>
 * Esta interfaz establece un método estándar, {@code showAlertBox}, que deben
 * implementar todas las clases destinadas a presentar alertas al usuario.
 * Al programar contra esta interfaz, se promueve un diseño desacoplado, permitiendo
 * que diferentes implementaciones de alertas (informativas, de error, etc.)
 * puedan ser utilizadas de manera intercambiable.
 *
 * @author Santiago Duque
 * @version 1.0
 * @since 2025-09-23
 */
public interface IAlertBox
{
    /**
     * Muestra un cuadro de diálogo de alerta en la pantalla.
     * <p>
     * La clase que implemente este método será responsable de la construcción
     * y visualización de la ventana de alerta, utilizando los parámetros
     * proporcionados para personalizar su contenido.
     *
     * @param title   El texto para la barra de título de la ventana de alerta.
     * @param message El mensaje principal o cuerpo del contenido de la alerta.
     * @param header  El texto del encabezado para la alerta.
     */
    void showAlertBox(String title, String message, String header);
}
