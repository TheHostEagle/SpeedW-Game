package example.speedw.models;

import javafx.fxml.FXML;

/**
 * Define el contrato para cualquier clase que muestre cuadros de dialogo de alerta.
 * <p>
 * Esta interfaz establece un metodo estandar, {@code showAlertBox}, que deben
 * implementar todas las clases destinadas a presentar alertas al usuario.
 * Al programar contra esta interfaz, se promueve un diseño desacoplado, permitiendo
 * que diferentes implementaciones de alertas (informativas, de error, etc.)
 * puedan ser utilizadas de manera intercambiable.
 *
 * @author Santiago Duque
 * @version 1.0
 * @since 2025-09-22
 */
public interface IAlertBox
{
    /**
     * Muestra un cuadro de dialogo de alerta en la pantalla.
     * <p>
     * La clase que implemente este metodo sera responsable de la construccion
     * y visualizacion de la ventana de alerta, utilizando los parametros
     * proporcionados para personalizar su contenido.
     *
     * @param title   El texto para la barra de titulo de la ventana de alerta.
     * @param message El mensaje principal o cuerpo del contenido de la alerta.
     * @param header  El texto del encabezado para la alerta.
     */
    void showAlertBox(String title, String message, String header);
}
