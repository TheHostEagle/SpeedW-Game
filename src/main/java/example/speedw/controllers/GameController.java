package example.speedw.controllers;

import example.speedw.App;
import example.speedw.models.AlertBox;
import example.speedw.models.Feedback;
import example.speedw.models.PauseGameTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameController
{

    @FXML
    private Button backButton;

    @FXML
    private TextArea textArea;

    @FXML
    private Label phraseLabel;


    /*
    * Falta documentación de JavaDoc
    * Falta funcionalidad del botón de volver en la view de Game.fxml
    * */

    @FXML
    private Label levelLabel;

    @FXML
    private Button recordButton;

    private App mainApp;

    public void setApp(App app)
    {
        this.mainApp = app;
    }

    @FXML
    private Button restartButton;

    @FXML
    private Label timeLabel;

    @FXML
    private Button validateButton;

    public void initialize()
    {
        level = 1;
        arrayOfCorrectPhrases.clear();
        lastAttempt = "";
        handicapAccumulator = 0;
        showNewPhrase();
        textArea.setDisable(false);
        validateButton.setDisable(false);
        startTimer();
        recordButton.setDisable(true);
        restartButton.setDisable(true);

        textArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            // La lógica de adentro es exactamente la misma
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("DEBUG: Enter capturado por el FILTRO, validando...");

                // Usamos el replaceAll por si acaso, para máxima seguridad
                validateTextArea();

                // El filtro consume el evento antes de que el TextArea lo procese
                event.consume();
                System.out.println("DEBUG: Evento consumido por el FILTRO.");
            }
        });
    }

    private int level;
    private Timeline timeline;
    private int remainingTime = 20;
    private int handicapAccumulator = 0;
    private String actualPhrase;
    private ArrayList<String> arrayOfCorrectPhrases = new ArrayList<>();
    private String lastAttempt;

    private void startTimer()
    {
        if (timeline != null)
        {
            timeline.stop();
        }
        timeLabel.setText(String.valueOf(remainingTime));

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e ->
        {
            remainingTime--;
            timeLabel.setText(String.valueOf(remainingTime));

            if (remainingTime <= 0)
            {
                timeline.stop();
                finalValidation();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void upLevel()
    {
        level++;
        levelLabel.setText(String.valueOf(level));
        levelRefiller();
    }

    /**
     * Procesa la lista de palabras correctas y genera un string con el resumen.
     * @param correctWords El ArrayList con todas las palabras que el usuario escribió bien.
     * @return Un String formateado listo para mostrarse en la alerta.
     */
    public String generateRecord(ArrayList<String> correctWords)
    {
        // Si no hubo palabras correctas, regresa un mensaje simple.
        if (correctWords == null || correctWords.isEmpty())
        {
            return "No hubo palabras correctas en este intento.";
        }

        // --- Parte 1: Contar frecuencias y encontrar la palabra más larga ---
        HashMap<String, Integer> wordCount = new HashMap<>();
        int maxLength = 0;

        for (String word : correctWords)
        {
            // Actualizamos el contador de palabras
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);

            // Verificamos si esta es la palabra más larga hasta ahora
            if (word.length() > maxLength) {
                maxLength = word.length();
            }
        }

        // --- Parte 2: Construir el String del mensaje ---
        StringBuilder msj = new StringBuilder("Resumen de tu intento:\n\n");

        // Agregamos cada palabra y su conteo
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            String word = entry.getKey();
            Integer count = entry.getValue();

            msj.append("- ").append(word);
            if (count > 1) {
                msj.append(" (x").append(count).append(")");
            }
            msj.append("\n");
        }

        // Agregamos la información de la palabra más larga
        msj.append("\n------------------------------\n");
        msj.append("Ultimo nivel: " + String.valueOf(level));
        msj.append("\nLetras de la palabra más larga: ").append(maxLength);

        return msj.toString();
    }

    private void levelRefiller()
    {
        showNewPhrase();
        remainingTime = 20;
        if (level % 5 == 0)
        {
            handicapAccumulator = handicapAccumulator + 2;
        }
        remainingTime = remainingTime - handicapAccumulator;

        startTimer();
    }

    @FXML
    private void restartGame()
    {
        level = 1;
        levelLabel.setText(String.valueOf(level));
        handicapAccumulator = 0;
        levelRefiller();
        textArea.setDisable(false);
        validateButton.setDisable(false);
        restartButton.setDisable(true);

    }

    @FXML
    private void validateTextArea()
    {
        Feedback feedback = new Feedback();
        PauseGameTransition pause = new PauseGameTransition();
        String answer = textArea.getText();

        if (answer.isEmpty())
        {
            phraseLabel.setText(feedback.TypeOfFeedback(2)); //falta iniciar la primera frase
            pause.pauseTransition(phraseLabel, actualPhrase);
            textArea.clear();
        } else if (answer.equals(phraseLabel.getText()))
        {

            //falta añadir una cosa más
            refillArrayOfWords(actualPhrase);
            textArea.clear();
            if(!winGame())
            {
                upLevel();
                phraseLabel.setText(feedback.TypeOfFeedback(3));
                pause.pauseTransition(phraseLabel, actualPhrase);
            }
        } else
        {
            phraseLabel.setText(feedback.TypeOfFeedback(1));
            pause.pauseTransition(phraseLabel, actualPhrase);
            textArea.clear();
        }
    }

    private boolean winGame()
    {
        if (level >= 35)
        {
            phraseLabel.setText("Has Ganado!");
            timeline.stop();
            textArea.setDisable(true);
            validateButton.setDisable(true);
            restartButton.setDisable(false);
            recordButton.setDisable(false);
            Platform.runLater(() -> {
                lastAttempt = generateRecord(arrayOfCorrectPhrases); // Pasando el nivel
                AlertBox alertBox = new AlertBox();
                alertBox.showAlertBox("\uD835\uDC11\uD835\uDC04\uD835\uDC06\uD835\uDC08\uD835\uDC12\uD835\uDC13\uD835\uDC11\uD835\uDC0E \uD835\uDC14\uD835\uDC0B\uD835\uDC13\uD835\uDC08\uD835\uDC0C\uD835\uDC0E \uD835\uDC08\uD835\uDC0D\uD835\uDC13\uD835\uDC04\uD835\uDC0D\uD835\uDC13\uD835\uDC0E",
                        lastAttempt,
                        "");
                arrayOfCorrectPhrases.clear();
            });
            return true;
        }
        return false;
    }

    private void lostGame()
    {
        // Estas acciones son seguras y se pueden ejecutar inmediatamente
        phraseLabel.setText("Has Perdido!");
        timeline.stop();
        textArea.setDisable(true);
        validateButton.setDisable(true);
        recordButton.setDisable(false);
        restartButton.setDisable(false);

        // ⚙️ Aquí está la solución:
        // Ponemos el resto en una "lista de espera" para que se ejecute de forma segura.
        Platform.runLater(() ->
        {
            // 1. Genera el récord (recuerda pasarle el nivel para el reporte correcto)
            lastAttempt = generateRecord(arrayOfCorrectPhrases);

            // 2. Muestra la alerta
            AlertBox alertBox = new AlertBox();
            alertBox.showAlertBox("\uD835\uDC11\uD835\uDC04\uD835\uDC06\uD835\uDC08\uD835\uDC12\uD835\uDC13\uD835\uDC11\uD835\uDC0E \uD835\uDC14\uD835\uDC0B\uD835\uDC13\uD835\uDC08\uD835\uDC0C\uD835\uDC0E \uD835\uDC08\uD835\uDC0D\uD835\uDC13\uD835\uDC04\uD835\uDC0D\uD835\uDC13\uD835\uDC0E",
                    lastAttempt,
                    "");

            // 3. Limpia los datos para el siguiente juego
            arrayOfCorrectPhrases.clear();
        });
    }

    @FXML
    private void recordActionR()
    {
        AlertBox alertBox = new AlertBox();
        alertBox.showAlertBox("\uD835\uDC11\uD835\uDC04\uD835\uDC06\uD835\uDC08\uD835\uDC12\uD835\uDC13\uD835\uDC11\uD835\uDC0E \uD835\uDC14\uD835\uDC0B\uD835\uDC13\uD835\uDC08\uD835\uDC0C\uD835\uDC0E \uD835\uDC08\uD835\uDC0D\uD835\uDC13\uD835\uDC04\uD835\uDC0D\uD835\uDC13\uD835\uDC0E",
                lastAttempt,
                "");
    }

    private void finalValidation()
    {
        PauseGameTransition pause = new PauseGameTransition();
        Feedback feedback = new Feedback();
        String answer = textArea.getText();
        if(answer.equals(phraseLabel.getText()))
        {
            refillArrayOfWords(actualPhrase);
            textArea.clear();
            if(!winGame())
            {
                upLevel();
                phraseLabel.setText(feedback.TypeOfFeedback(4));
                pause.pauseTransition(phraseLabel, actualPhrase);
            }
        }
        else
        {
            phraseLabel.setText("¡Se acabó el tiempo!");
            actualPhrase = phraseLabel.getText();
            lostGame();
        }
    }

    @FXML
    private void backAction()
    {
        if (timeline != null)
        { // Buena práctica: verificar que no sea null
            timeline.stop();
        }
        arrayOfCorrectPhrases.clear();

        System.out.println("Funciona");
        if (mainApp != null) {
            try
            {
                // Obtenemos la ventana actual (la del juego)
                Stage currentGameStage = (Stage) backButton.getScene().getWindow();
                // Llamamos a un nuevo método en la clase App para volver
                mainApp.openWelcomeWindow(currentGameStage);
            } catch (IOException e) {
                System.err.println("Error al volver a la ventana de bienvenida.");
                e.printStackTrace();
            }
        }
    }

    private void showNewPhrase()
    {
        Random rand = new Random();
        actualPhrase = arrFrases[rand.nextInt(arrFrases.length)];
        phraseLabel.setText(actualPhrase);
    }

    private void refillArrayOfWords(String word)
    {
        arrayOfCorrectPhrases.add(word);
    }

    private final String[] arrFrases =
            {
                    "radiologia",
                    "chicharra",
                    "biologia efimera",
                    "programacion eventos",
                    "filologia",
                    "intervencion",
                    "arreglo dinamico",
                    "exorbitante",
                    "exhortar",
                    "calentamiento global",
                    "indeterminacion",
                    "paralelepipedo",
                    "electroencefalografista",
                    "anticonstitucionalmente",
                    "esternocleidomastoideo",
                    "mantequilla",
                    "abyecto",
                    "posole",
                    "mecanico",
                    "polifomania",
                    "promotor",
                    "abstraccion",
                    "yucatan",
                    "grafito",
                    "tren",
                    "sol",
                    "malo",
                    "dinosaurio",
                    "escribe esto",
                    "santiago",
                    "frase larga",
                    "cien años de sol",
                    "enamorado tuyo",
                    "namaste",
                    "spider man",
                    "batman y superman",
                    "el fin se acerca",
                    "oración",
                    "población",
                    "sí"
            };
}
