package es.ieslavereda.ajedrezandroid.Jugador;

import es.ieslavereda.ajedrezandroid.Color;
import es.ieslavereda.ajedrezandroid.Tablero;
import es.ieslavereda.ajedrezandroid.Timer.Timer;

import java.io.Serializable;
import java.util.Comparator;

public class Jugador implements Serializable {

    public static Comparator<Jugador> SORT_BY_COLOR = Comparator.comparing(o -> o.getColor().toString());
    private String nombre;
    private Color color;
    private Timer reloj;
    private Tablero tablero;

    public Jugador(String nombre, Color color, Tablero t, Timer r){
        this.nombre= nombre;
        this.color = color;
        tablero=t;
        reloj = r;
    }

    public Timer getReloj() {
        return reloj;
    }

    public Color getColor(){
        return color;
    }
    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString(){
        return nombre;
    }
}
