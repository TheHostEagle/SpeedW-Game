package example.speedw.models;

import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.animation.PauseTransition;

/**
 * Gestiona una transicion de pausa para actualizar elementos de la interfaz.
 * <p>
 * Esta clase de utilidad encapsula la logica para crear una breve pausa
 * antes de actualizar el texto de una etiqueta en la interfaz de usuario.
 * Es util para temporizar la aparicion de texto en el juego.
 *
 * @author Santiago Duque
 * @version 1.0
 * @since 2025-09-22
 */
public class PauseGameTransition
{
    /**
     * Crea y ejecuta una pausa antes de actualizar el texto de una etiqueta.
     * <p>
     * El metodo establece una pausa fija de 0.7 segundos. Una vez finalizada,
     * el texto de la {@code Label} proporcionada se actualiza con la nueva frase.
     *
     * @param phraseLabel La etiqueta (Label) de JavaFX cuyo texto se actualizara.
     * @param actualPhrase El nuevo texto (String) que se mostrara en la etiqueta.
     */
    public void pauseTransition(Label phraseLabel,String actualPhrase)
    {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.7));
        pause.setOnFinished(e -> phraseLabel.setText(actualPhrase));
        pause.play();
    }
}
