package es.ieslavereda.ajedrezandroid;

import com.diogonunes.jcolor.Attribute;

import static com.diogonunes.jcolor.Ansi.colorize;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import java.io.Serializable;

/**
 * Clase Celda que se encarga de gestionar las celdas del tablero.
 * @author genguidanosn
 */
public class Celda extends androidx.appcompat.widget.AppCompatImageView implements Serializable {
    /**
     * Color que adquiere la celda en cada momento de la partida.
     */
    private int color;
    /**
     * Color original de la celda, que solo podrá ser blanco o negro y será inmutable.
     */
    private int colorOriginal;
    private Coordenada coordenada;
    private Pieza pieza;
    private Tablero tablero;

    /**
     * Constructor de Celda.
     * @param coordenada la coordenada en el tablero correspondiente a esta celda.
     * @param tablero el tablero al cuál pertenece esta celda.
     */
    public Celda(Context context, Coordenada coordenada, Tablero tablero) {
        super(context);
        this.coordenada = coordenada;
        this.tablero = tablero;
        this.pieza = null;
        if (((coordenada.getCol() - 'A') + (coordenada.getRow() - 1)) % 2 == 0) {
            this.colorOriginal = DynamicColor.getInstance().getWhiteCell();
        } else {
            this.colorOriginal = DynamicColor.getInstance().getBlackCell();
        }
        color = colorOriginal;

        DisplayMetrics displayMetrics = new DisplayMetrics();

        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int widh = displayMetrics.widthPixels;

        setMaxWidth(widh/10);
        setMinimumWidth(widh/10);
        setMaxHeight(widh/10);
        setMinimumHeight(widh/10);

        setPadding(0,0,0,0);
        setScaleType(ScaleType.FIT_CENTER);
        setAdjustViewBounds(true);
        updateCellView();

    }
    public void updateCellView() {
        setBackgroundColor(getResources().getColor(color,getContext().getTheme()));
    }

    public void setColorOriginal(int color){
        colorOriginal = color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Método modificador para cambiar la pieza que hay sobre una celda.
     * @param pieza nueva pieza. Puede ser null si queremos quitar una pieza de una celda.
     */
    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
        if(pieza!=null)
            setImageResource(pieza.getType().getForma());
        else
            setImageResource(0);
    }

    /**
     * Devuelve la coordenada correspondiente a la celda.
     * @return coordenada.
     */
    public Coordenada getCoordenada() {
        return coordenada;
    }

    /**
     * Getter de la pieza que hay en la celda.
     * @return Pieza.
     */
    public Pieza getPiece() {
        return pieza;
    }

    /**
     * Getter del tablero al cuál pertenece la celda.
     * @return tablero
     */
    public Tablero getTablero() {
        return tablero;
    }

    /**
     * Getter del color actual de la celda, que puede variar según el contexto de la partida.
     * @return color.
     */
    public int getColor(){
        return color;
    }

    /**
     * Método que me indica si la celda esta vacía o no, es decir, si hay alguna pieza en ella o no.
     * @return true: si hay alguna pieza.
     * @return false: si no hay ninguna pieza.
     */
    public boolean isEmpty() {
        return pieza == null;
    }

    /**
     * Método para cambiar el color de la celda cuando queremos resaltar los posibles movimientos de una pieza.
     * Si la celda contiene una pieza, se resaltará de rojo, indicando al jugador que puede comer una pieza enemiga.
     * En caso contrario se resaltará de verde, indicando al jugador que puede moverse a una celda vacía.
     */
    public void resaltar() {
        if (isEmpty()){
            if (colorOriginal==DynamicColor.getInstance().getWhiteCell())
                color=R.color.white_move;
            else
                color=R.color.black_move;
        }else{
            if(colorOriginal==DynamicColor.getInstance().getWhiteCell())
                color=R.color.white_cell_enemy;
            else
                color=R.color.black_cell_enemy;
        }
        updateCellView();
    }

    /**
     * Método para resaltar una celda de color azulado, que indica la celda de origen y destino
     * del movimiento de una pieza.
     */
    public void resaltarLastMovement(){
        if (colorOriginal==DynamicColor.getInstance().getWhiteCell())
            color=R.color.white_lastMove;
        else
            color=R.color.black_lastMove;
        updateCellView();
    }

    /**
     * Método para resaltar la celda del rey de color azul, indicando qeu está en jaque.
     */
    public void resaltarJaque(){
        color=R.color.checked_cell;
        updateCellView();
    }

    /**
     * Método para que el color actual de la celda sea igual al color original de la celda.
     */
    public void resetColor() {
        color=colorOriginal;
        updateCellView();
    }
}
