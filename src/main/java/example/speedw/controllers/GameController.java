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

/**
 * Controlador para la pantalla principal del juego 'SpeedW'.
 * <p>
 * Gestiona toda la logica del juego, incluyendo el estado (niveles, tiempo),
 * la interaccion con el usuario (entrada de texto, botones) y las actualizaciones
 * de la interfaz grafica.
 *
 * @author Santiago Duque
 *
 * @version 1.0
 *
 * @since 2025-09-22
 */
public class GameController
{
    //FXML ELEMENTOS GRAFICOS
    /**
     * Boton para regresar a la pantalla de bienvenida.
     */
    @FXML
    private Button backButton;

    /**
     * Area de texto donde el usuario ingresa las palabras a validar.
     */
    @FXML
    private TextArea textArea;

    /**
     * Etiqueta que muestra la frase que el usuario debe escribir.
     * Tambien se usa para mostrar mensajes de feedback (ej. "Correcto!", "Incorrecto!").
     */
    @FXML
    private Label phraseLabel;

    /**
     * Etiqueta que muestra el nivel actual del juego.
     */
    @FXML
    private Label levelLabel;

    /**
     * Boton para mostrar el resumen del último intento despues de que el juego termina.
     */
    @FXML
    private Button recordButton;

    /**
     * Boton para reiniciar el juego una vez que ha terminado.
     */
    @FXML
    private Button restartButton;

    /**
     * Etiqueta que muestra el tiempo restante en segundos.
     */
    @FXML
    private Label timeLabel;

    /**
     * Boton para validar la palabra ingresada por el usuario.
     */
    @FXML
    private Button validateButton;

    // ATRIBUTOS O VARIABLES DEL CONTROLADOR

    /**
     * Referencia a la clase principal de la aplicacion, usada para cambiar de ventana.
     */
    private App mainApp;

    /**
     * Nivel actual del juego.
     */
    private int level;

    /**
     * Objeto Timeline de JavaFX que gestiona el temporizador del juego.
     */
    private Timeline timeline;

    /**
     * Segundos restantes en el nivel actual. Se inicializa en 20 y se reduce con el handicap.
     */
    private int remainingTime = 20;

    /**
     * Acumulador de 'handicap' (desventaja) que reduce el tiempo disponible en niveles mas altos para aumentar la dificultad.
     */
    private int handicapAccumulator = 0;

    /**
     * La frase actual que el jugador debe escribir.
     */
    private String actualPhrase;

    /**
     * Lista que almacena todas las frases escritas correctamente durante la partida actual.
     */
    private ArrayList<String> arrayOfCorrectPhrases = new ArrayList<>();

    /**
     * Almacena el resumen del último intento (generado por {@link #generateRecord}) para ser mostrado por el boton de record.
     */
    private String lastAttempt;

    /**
     * Arreglo de strings que contiene todas las frases posibles del juego.
     */
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
                    "oracion",
                    "población",
                    "sí"
            };

    /**
     * Establece la referencia a la aplicacion principal.
     * <p>
     * Este metodo es crucial para permitir que este controlador interactue con
     * la clase {@link App} para cambiar de escena, por ejemplo, al volver al menu principal.
     *
     * @param app La instancia de la clase principal App.
     */
    public void setApp(App app)
    {
        this.mainApp = app;
    }

    /**
     * Metodo de inicializacion del controlador.
     * <p>
     * Se llama automaticamente despues de que se cargan los elementos FXML.
     * Configura el estado inicial del juego, muestra la primera frase, inicia el temporizador
     * y añade un filtro de eventos para capturar la tecla ENTER en el TextArea.
     */
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

        //El filtro de eventos permite evaluar o condicionar algunos procesos antes de, para lo que necesitemos.

        textArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                validateTextArea();
                // El filtro consume el evento antes de que el TextArea procese el ENTER y haga salto de línea
                event.consume();
            }
        });
    }

    /**
     * Inicia o reinicia el temporizador del juego.
     * <p>
     * Configura un {@link Timeline} que se ejecuta cada segundo, decrementando el tiempo
     * restante y actualizando la etiqueta correspondiente. Si el tiempo llega a cero,
     * detiene el temporizador y llama a {@link #finalValidation()}.
     */
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

    /**
     * Incrementa el nivel del juego, actualiza la etiqueta de nivel y prepara la siguiente ronda.
     */
    private void upLevel()
    {
        level++;
        levelLabel.setText(String.valueOf(level));
        levelRefiller();
    }

    /**
     * Procesa la lista de palabras correctas y genera un string con el resumen del juego.
     *
     * @param correctWords El ArrayList con todas las palabras que el usuario escribio correctamente.
     * @return Un String formateado listo para mostrarse en la alerta de resumen.
     */
    public String generateRecord(ArrayList<String> correctWords)
    {
        if (correctWords == null || correctWords.isEmpty())
        {
            return "No hubo palabras correctas en este intento.";
        }

        //ESTE FRAGENTO DE CODIGO SE HA REALIZADO CON IA : GEMINI

        // --- Parte 1: Contar frecuencias y encontrar la palabra mas larga ---
        HashMap<String, Integer> wordCount = new HashMap<>();
        int maxLength = 0;

        for (String word : correctWords)
        {
            // Actualizamos el contador de palabras
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);

            // Verificamos si esta es la palabra mas larga hasta ahora
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

        // Agregamos la informacion de la palabra mas larga
        msj.append("\n------------------------------\n");
        msj.append("Ultimo nivel: " + String.valueOf(level));
        msj.append("\nLetras de la palabra mas larga: ").append(maxLength);
        return msj.toString();

        // TERMINA COLABORACION DE IA.
    }

    /**
     * Prepara el entorno para el siguiente nivel.
     * <p>
     * Muestra una nueva frase, reinicia el tiempo (aplicando el handicap si corresponde)
     * e inicia de nuevo el temporizador.
     */
    private void levelRefiller()
    {
        showNewPhrase();
        remainingTime = 20;

        //Aca cualquier número múltiplo de 5 cae en esta condicion.
        if (level % 5 == 0)
        {
            handicapAccumulator = handicapAccumulator + 2;
        }
        remainingTime = remainingTime - handicapAccumulator;
        startTimer();
    }

    /**
     * Manejador del evento del boton de reinicio.
     * <p>
     * Restablece todas las variables del juego a su estado inicial, habilita los controles
     * y comienza una nueva partida.
     */
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

    /**
     * Valida la entrada del usuario en el {@link TextArea}.
     * <p>
     * Este metodo es el núcleo de la logica de validacion. Comprueba si la entrada esta vacía,
     * si coincide con la frase objetivo o si es incorrecta, y proporciona el feedback
     * correspondiente al usuario. Si la palabra es correcta, avanza de nivel.
     */
    @FXML
    private void validateTextArea()
    {
        Feedback feedback = new Feedback();
        PauseGameTransition pause = new PauseGameTransition();
        String answer = textArea.getText();

        //LOGICA CONDICIONAL:
        if (answer.isEmpty()) //Si esta vacío
        {
            phraseLabel.setText(feedback.TypeOfFeedback(2));
            pause.pauseTransition(phraseLabel, actualPhrase);
            textArea.clear();
        } else if (answer.equals(phraseLabel.getText())) //Si equivale
        {
            refillArrayOfWords(actualPhrase);
            textArea.clear();

            //SE REVISA SI SE GANO EL JUEGO PARA NO AVANZAR A OTRO NIVEL SI ESTAS EN EL 35
            if(!winGame())
            {
                upLevel();
                phraseLabel.setText(feedback.TypeOfFeedback(3));
                pause.pauseTransition(phraseLabel, actualPhrase);
            }
        } else //SI ES INCORRECTA
        {
            phraseLabel.setText(feedback.TypeOfFeedback(1));
            pause.pauseTransition(phraseLabel, actualPhrase);
            textArea.clear();
        }
    }

    /**
     * Verifica si el jugador ha alcanzado la condicion de victoria (nivel 35).
     * <p>
     * Si se cumple la condicion, detiene el juego, deshabilita los controles,
     * muestra un mensaje de victoria y genera la alerta con el resumen final.
     *
     * @return {@code true} si el jugador ha ganado, {@code false} en caso contrario.
     */
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

            //El Platform runLater sirve para ejecutar algo despues de una animacion o proceso.
            Platform.runLater(() -> {
                lastAttempt = generateRecord(arrayOfCorrectPhrases);

                AlertBox alertBox = new AlertBox();
                alertBox.showAlertBox("INFORMACION ULTIMO INTENTO", lastAttempt, "");

                arrayOfCorrectPhrases.clear();
            });
            return true;
        }
        return false;
    }

    /**
     * Gestiona el estado de 'juego perdido'.
     * <p>
     * Se activa cuando el jugador no logra escribir la palabra a tiempo. Detiene el temporizador,
     * deshabilita los controles y muestra una alerta con el resumen del intento.
     * Usa {@link Platform#runLater} para evitar problemas de concurrencia en el hilo de JavaFX.
     */
    private void lostGame()
    {
        phraseLabel.setText("Has Perdido!");
        timeline.stop();
        textArea.setDisable(true);
        validateButton.setDisable(true);
        recordButton.setDisable(false);
        restartButton.setDisable(false);

        Platform.runLater(() ->
        {
            lastAttempt = generateRecord(arrayOfCorrectPhrases);

            AlertBox alertBox = new AlertBox();
            alertBox.showAlertBox("INFORMACION ULTIMO INTENTO", lastAttempt, "");

            arrayOfCorrectPhrases.clear();
        });
    }

    /**
     * Manejador del evento del boton de record.
     * Muestra una alerta con el resumen del ultimo intento guardado.
     */
    @FXML
    private void recordActionR()
    {
        AlertBox alertBox = new AlertBox();
        alertBox.showAlertBox("INFORMACION ULTIMO INTENTO",
                lastAttempt, "");
    }

    /**
     * Realiza una ultima validacion cuando el tiempo se agota.
     * <p>
     * Si la palabra escrita es correcta justo cuando el tiempo llega a 0, se considera valida
     * y el juego continúa. Si no, el juego se da por perdido.
     */
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
            phraseLabel.setText("¡Se acabo el tiempo!");
            actualPhrase = phraseLabel.getText();
            lostGame();
        }
    }

    /**
     * Manejador del evento del boton 'VOLVER'.
     * <p>
     * Detiene el temporizador, limpia los datos de la partida actual y utiliza la
     * referencia a {@link App} para regresar a la ventana de bienvenida.
     */
    @FXML
    private void backAction()
    {
        //Detener reloj si esta alguno activo
        if (timeline != null)
        {
            timeline.stop();
        }
        arrayOfCorrectPhrases.clear();

        //PARA ABRIR DE NUEVO LA VENTANA WELCOME
        if (mainApp != null)
        {
            try
            {
                // Obtenemos la ventana actual (la del juego)
                Stage currentGameStage = (Stage) backButton.getScene().getWindow();
                // Llamamos a un nuevo metodo en la clase App para volver
                mainApp.openWelcomeWindow(currentGameStage);
            } catch (IOException e) {
                System.err.println("Error al volver a la ventana de bienvenida.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Selecciona una nueva frase aleatoria del arreglo {@link #arrFrases} y la muestra en la UI.
     */
    private void showNewPhrase()
    {
        Random rand = new Random();
        actualPhrase = arrFrases[rand.nextInt(arrFrases.length)];
        phraseLabel.setText(actualPhrase);
    }

    /**
     * Añade una palabra escrita correctamente a la lista {@link #arrayOfCorrectPhrases}.
     *
     * @param word La palabra correcta que sera añadida al registro del juego.
     */
    private void refillArrayOfWords(String word)
    {
        arrayOfCorrectPhrases.add(word);
    }
}
