package es.ieslavereda.ajedrezandroid;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.diogonunes.jcolor.Ansi.colorize;

import androidx.annotation.Nullable;

/**
 * Clase abstracta que gestiona el comportamiento común de todas las piezas del ajedrez.
 * @author genguidanosn
 */
public abstract class Pieza implements Serializable, Comparable<Pieza> {

    @Override
    public int compareTo(Pieza pieza) {
        if (this.getValor()==pieza.getValor()){
            return this.getType().nombre.compareTo(pieza.getType().nombre);
        }
        return pieza.getValor()-this.getValor();
    }

    /**
     * Celda sobre la que está una pieza.
     */
    protected Celda celda;
    /**
     * Forma que adquiere la pieza.
     */
    private PieceType forma;
    /**
     * Conjunto de coordenadas a los que puede moverse una pieza.
     */
    protected Set<Coordenada> coordenadasMovimientos;

    /**
     * Constructor de Pieza.
     * @param f forma que tendrá la pieza.
     * @param celda celda sobre la cuál se posará la pieza.
     */
    public Pieza(PieceType f, Celda celda) {
        this.forma = f;
        this.celda = celda;
        coordenadasMovimientos = new HashSet<>();
        putPieceInPlace();
    }

    /**
     * Getter de la celda donde se encuentra la pieza.
     * @return Celda
     */
    public Celda getCelda() {
        return celda;
    }

    /**
     * Getter del valor en puntos que tiene una pieza.
     * @return número natural.
     */
    public int getValor(){
        return forma.valor;
    }

    /**
     * Getter del color de la forma de la pieza.
     * @return Color.
     */
    public Color getColor() {
        return forma.color;
    }

    /**
     * Getter del tipo (o forma) de la pieza.
     * @return la forma de la pieza,
     */
    public PieceType getType() {
        return forma;
    }

    /**
     * Método que asigna la pieza actual a la celda. Sólo es usada al inicio de la partida cuando se colocan todas
     * las piezas. Este método permite que la pieza sea visible sobre su celda.
     */
    public void putPieceInPlace() {
        celda.setPieza(this);
    }

    /**
     * Metodo que devuelve el texto descriptivo de la pieza con su artículo femenino o masculino.
     * @return cadena de texto.
     */
    public String decirNombre() {
        if (forma == PieceType.BLACK_REINA || forma == PieceType.BLACK_TORRE
                || forma == PieceType.WHITE_REINA || forma == PieceType.WHITE_TORRE) {
            return "La " + this.forma.nombre;
        } else {
            return "El " + this.forma.nombre;
        }
    }

    /**
     * Metodo abstracto que obtiene las coordenadas donde se puede mover una pieza, sin restricciones.
     * @return conjunto de coordenadas sin repetición.
     */
    public abstract Set<Coordenada> getNextKills();

    /**
     * Método abstracto para obtener las coordenadas donde se puede mover una pieza siempre que no deje en jaque
     * a su rey.
     * @return conjunto de coordenadas sin repetición.
     */
    public abstract Set<Coordenada> getComplexMoves();

    /**
     * Método que mueve la pieza a una nueva posición.
     * @param nuevaPosicion nueva coordenada dónde mover la pieza.
     * @param visor
     */
    public abstract void moveTo(Coordenada nuevaPosicion);

    /**
     * Método para deshacer un movimiento. útil para verificar el método complexMoves()
     * @param origen coordenada donde estaba la pieza antes de moverse.
     * @param provisional coordenada donde se ha movido la pieza.
     * @param laQueEstaba pieza que estaba en la coordenada provisional.
     */
    public void deshacerMove(Coordenada origen, Coordenada provisional, Pieza laQueEstaba) {
        Tablero t = getCelda().getTablero();

        if (laQueEstaba == null) {
            t.getCelda(provisional).setPieza(null);
            celda = t.getCelda(origen);
            t.getCelda(origen).setPieza(this);
        } else {
            t.getCelda(provisional).setPieza(laQueEstaba);
            t.getCelda(provisional).getPiece().celda = t.getCelda(provisional);
            this.celda = t.getCelda(origen);
            t.getCelda(origen).setPieza(this);
        }
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Pieza){
            Pieza aux = (Pieza)obj;
            return this.forma==aux.forma;
        }
        return false;
    }

    //    @Override
//    public String toString() {
//        return colorize(forma.toString(), forma.color.getColor(), celda.getColor().attribute);
//    }

    /**
     * Lista de las diferentes formas que puede adquirir una pieza.
     */
    public enum PieceType {

        BLACK_PEON(R.mipmap.ic_b_peon_foreground, Color.BLACK, "peón",1, '\u265F'),
        BLACK_ALFIL(R.mipmap.ic_b_alfil_foreground, Color.BLACK, "alfil",3, '\u265D'),
        BLACK_CABALLO(R.mipmap.ic_b_caballo_foreground, Color.BLACK, "caballo",3,'\u265E'),
        BLACK_TORRE(R.mipmap.ic_b_torre_foreground, Color.BLACK, "torre",5, '\u265C'),
        BLACK_REINA(R.mipmap.ic_b_reina_foreground, Color.BLACK, "reina",9, '\u265B'),
        BLACK_REY(R.mipmap.ic_b_rey_foreground, Color.BLACK, "rey",0, '\u265A'),
        WHITE_PEON(R.mipmap.ic_w_peon_foreground, Color.WHITE, "peón",1, '\u265F'),
        WHITE_ALFIL(R.mipmap.ic_w_alfil_foreground, Color.WHITE, "alfil",3, '\u265D'),
        WHITE_CABALLO(R.mipmap.ic_w_caballo_foreground, Color.WHITE, "caballo",3, '\u265E'),
        WHITE_TORRE(R.mipmap.ic_w_torre_foreground, Color.WHITE, "torre",5, '\u265C'),
        WHITE_REINA(R.mipmap.ic_w_reina_foreground, Color.WHITE, "reina",9, '\u265B'),
        WHITE_REY(R.mipmap.ic_w_rey_foreground, Color.WHITE, "rey",0, '\u265A');

        //Atributos
        int forma;
        /**
         * Color de la forma
         */
        Color color;
        /**
         * Nombre descriptivo de la forma
         */
        String nombre;
        /**
         * Valor en puntos de la pieza según el reglamento del ajedrez.
         */
        int valor;
        char unicode;

        /**
         * Constructor de PieceType
         * @param c color en el cual se imprimirá por pantalla la forma.
         * @param nombre texto descriptivo de la forma.
         * @param valor puntos que vale cada pieza.
         */
        PieceType(int shape, Color c, String nombre, int valor, char unicode) {
            forma = shape;
            color = c;
            this.nombre = nombre;
            this.valor = valor;
            this.unicode=unicode;
        }

        public int getForma(){
            return forma;
        }
        public char getUnicode(){
            return unicode;
        }

        @Override
        public String toString() {
            return String.valueOf(forma);
        }
    }
}
