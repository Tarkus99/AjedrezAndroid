package es.ieslavereda.ajedrezandroid;

import com.diogonunes.jcolor.Attribute;

import static com.diogonunes.jcolor.Ansi.colorize;
//import static javafx.scene.paint.Color.rgb;

/**
 * Clase donde almaceno los distintos mensajes pertenecientes a menús o preguntas recurrentes.
 */
public class Mensajes {

    static Attribute rojo = Attribute.TEXT_COLOR(198, 195, 41 );
    static Attribute azul = Attribute.TEXT_COLOR(41, 172, 198);
    static Attribute verde = Attribute.TEXT_COLOR(   51, 156, 37);

    public static String bienvenida(){
        return "********** "+ colorize("Bienvenido al juego del ajedrez",  verde) +" **********.\n" +
                "Reglas:\n" +
                colorize("1-",rojo)+colorize(" Cada jugador tendrá un reloj con un tiempo máximo definido al principio de la partida.\n", azul) +
                colorize("2-",rojo)+colorize(" Un jugador podrá rendirse siempre que quiera, moviendo su rey a la misma casilla donde se encuentra.\n", azul) +
                colorize("3-",rojo)+colorize(" Los colores se asignarán de manera totalmente aleatoria cuando los jugadores se den de alta.\n", azul) +
                colorize("4-",rojo)+colorize(" En el turno del jugador, éste tendrá que elegir una casilla del tablero. Siempre y cuando la pieza \n" +
                "   sea de su color y pueda moverse, a continuación tendrá la opción de elegir la casilla de destino.\n", azul) +
                colorize("5-",rojo)+colorize(" Si una vez elegida la pieza, y mientras no se haya confirmado el movimiento, el jugador decide\n" +
                "   mover otra pieza diferente, podrá escribir el comando 'cancelar' para tener la posibilidad de elegir\n" +
                "   otra pieza.\n", azul) +
                "********** " + colorize("PULSA ~ENTER~ PARA CONTINUAR", verde) + " **********";
    }

    public static String menu(){
        return "¿Qué quieres hacer ahora?\n" +
                "-Comenzar una nueva partida [1].\n" +
                "-Cargar una partida guardada [2].\n" +
                "-Salir del juego [0].";
    }
    public static String moverPieza(){
        return "Selecciona una pieza para mover o guarda la partida [guardar].";
    }
    public static String selectMov(){
        return "¿A dónde quieres mover?.";
    }

}
