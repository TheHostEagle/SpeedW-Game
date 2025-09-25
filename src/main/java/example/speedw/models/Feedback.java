package example.speedw.models;

public class Feedback
{
    private String textFeedback;

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
        }

        return textFeedback;

    }
}
