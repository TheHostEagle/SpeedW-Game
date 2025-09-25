package example.speedw.models;

import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.animation.PauseTransition;

public class PauseGameTransition
{
    public void pauseTransition(Label phraseLabel,String actualPhrase)
    {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> phraseLabel.setText(actualPhrase));
        pause.play();

    }
}
