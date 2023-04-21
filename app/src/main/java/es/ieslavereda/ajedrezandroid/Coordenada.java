package es.ieslavereda.ajedrezandroid;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase Coordenada que gestiona las coordenadas. Una cordenada se compone de una columna (A-H) y de una fila (1-8)
 * @author genguidanosn
 */
public class Coordenada implements Serializable {

    private char col;
    private int row;

    /**
     * Constructor de coordenadas
     * @param letra indica la columna.
     * @param num indica la fila.
     */
    public Coordenada(char letra, int num){
        this.col = Character.toUpperCase(letra);
        this.row = num;
    }

    /**
     * Método para obtener la columna.
     * @return char
     */
    public char getCol() {
        return col;
    }

    /**
     * Método para obtener la fila
     * @return número entero
     */
    public int getRow() {
        return row;
    }

    /**
     * Método para obtener la casilla superior a la actual.
     * @return nueva coordenada.
     */
    public Coordenada casillaUp(){
        return new Coordenada(col,(row-1));
    }
    /**
     * Método para obtener la casilla inferior a la actual.
     * @return nueva coordenada.
     */
    public Coordenada casillaDown(){
        return new Coordenada(col, row+1);
    }
    /**
     * Método para obtener la casilla a la izquierda de la actual.
     * @return nueva coordenada.
     */
    public Coordenada casillaLeft(){
        return new Coordenada((char)(col-1), row);
    }
    /**
     * Método para obtener la casilla a la derecha de la actual.
     * @return nueva coordenada.
     */
    public Coordenada casillaRight(){
        return new Coordenada((char)(col+1), row);
    }
    /**
     * Método para obtener la casilla diagonal derecha a la actual.
     * @return nueva coordenada.
     * Combina el método casillaUp() y casillaRight().
     */
    public Coordenada diagoUpRight(){
        return casillaUp().casillaRight();
    }
    /**
     * Método para obtener la casilla diagonal izquierda superior a la actual.
     * Combina el método casillaUp() y casillaLeft().
     * @return nueva coordenada.
     */
    public Coordenada diagoUpLeft(){
        return casillaUp().casillaLeft();
    }
    /**
     * Método para obtener la casilla diagonal derecha superior a la actual.
     * Combina el método casillaDown() y casillaRight().
     * @return nueva coordenada.
     */
    public Coordenada diagoDownRight(){
        return casillaDown().casillaRight();
    }
    /**
     * Método para obtener la casilla diagonal izquierda inferior a la actual.
     * Combina el método casillaDown() y casillaLeft().
     * @return nueva coordenada.
     */
    public Coordenada diagoDownLeft(){
        return casillaDown().casillaLeft();
    }

    /**
     * Método que me indica si la coordenada actual equivale a una coordenada de enroque, con la referencia siempre
     * en la coordenada del rey.
     * @param c coordenada del rey.
     * @return <ul>
     *     <li><b>True</b>: si la coordenada actual es igual a dos coordenadas a la derecha
     *     o dos coordenadas a la izquierda desde la coordenada del rey.</li>
     *     <li><b>False:</b> si la coordenada actual no coincide con dos coordenadas a la derecha o
     *     dos coordenadas a la izquierda desde la coordenada donde está el rey.</li>
     * </ul>
     */
    public boolean esEnroque(Coordenada c){
        return this.equals(c.casillaRight().casillaRight())
                || this.equals(c.casillaLeft().casillaLeft());
    }

    /**
     * Método que me indica si la coordenada actual es igual a la coordenada de enroque corto, con la referencia
     * siempre en la coordenada del rey.
     * @param c coordenada del rey.
     * @return <ul><li><b>True:</b> si la coordenada actual es igual a dos coordenadas a la derecha desde
     * la coordenadea del rey.</li><li><b>False:</b> si la coordenada actual es distinta a dos coordenadas
     * a la derecha desde la coordenada del rey.</li></ul>
     */
    public boolean esEnroqueCorto(Coordenada c){
        return this.equals(c.casillaRight().casillaRight());
    }

    /**
     * Método equals que compara dos coordenadas.
     * @param obj coordenada a comparar.
     * @return <ul><li><b>True:</b> si las columnas y las filas son las mismas en ambas coordenadas.</li>
     * <li><b>False:</b> si las columnas o las filas son distintas en ambas coordenadas.</li></ul>
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordenada){
            Coordenada c = (Coordenada) obj;
            return col==c.col && row == c.row;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(col);
    }

    @Override
    public String toString(){
        return "(" + this.col + "," + this.row + ")";
    }
}
