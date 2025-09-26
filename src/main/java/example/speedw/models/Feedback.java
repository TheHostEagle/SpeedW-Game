package example.speedw.models;

/**
 * Gestiona y genera mensajes de retroalimentacion para el jugador.
 * <p>
 * Esta clase proporciona una manera centralizada de obtener los textos
 * de feedback que se le mostraran al usuario segun el resultado de su accion
 * en el juego.
 *
 * @author Santiago Duque Magaña
 * @version 1.0
 * @since 2025-09-22
 */
public class Feedback
{
    /**
     * Almacena el texto del mensaje de feedback a retornar.
     */
    private String textFeedback;

    /**
     * Devuelve un mensaje de retroalimentacion basado en un codigo de tipo.
     * <p>
     * Este metodo utiliza un codigo numerico para determinar que mensaje
     * especifico debe retornarse. Cada numero corresponde a un estado diferente
     * del juego:
     * <ul>
     * <li><b>1:</b> Palabra incorrecta.</li>
     * <li><b>2:</b> Campo de texto vacio.</li>
     * <li><b>3:</b> Palabra correcta.</li>
     * <li><b>4:</b> Palabra correcta, pero cerca del limite de tiempo.</li>
     * </ul>
     *
     * @param type El codigo numerico que representa el tipo de feedback.
     * @return Un {@code String} con el mensaje de feedback correspondiente.
     */
    public String TypeOfFeedback(int type)
    {
        if (type == 1) // 1 es para palabra incorrecta
        {
            textFeedback = "Palabra Incorrecta!";
        }
        else if (type == 2)  // 2 es para palabra vacia
        {
            textFeedback = "No escribiste Nada";
        }
        else if (type == 3)  // 3 es para el exito
        {
            textFeedback = "Correcto!";
        }
        else if (type == 4)  // 4 es para el casi perder por tiempo
        {
            textFeedback = "Correcto! Casi te gana el tiempo eh!";
        }
        else
        {
            System.out.println("Error");
            textFeedback = "Error";
        }
        return textFeedback;
    }
}
