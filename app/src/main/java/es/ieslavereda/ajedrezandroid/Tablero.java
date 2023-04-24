package es.ieslavereda.ajedrezandroid;

import com.diogonunes.jcolor.Attribute;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static com.diogonunes.jcolor.Ansi.colorize;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Tablero extends TableLayout implements Serializable {
    private Map<Coordenada, Celda> cellsMap;
    private int idDeadWhite;
    private int idDeadBlack;
    //private IDeletePieceManager deletePieceManager;

    public Tablero(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.cellsMap = new HashMap<>();
        idDeadWhite = 1;
        idDeadBlack = 20;
        addDeadCamp(1);
        addTextViews();

        TableRow row;
        for (int r = 0; r <= 7; r++) {
            row = new TableRow(getContext());
            row.addView(getTextView(""+(r+1)));

            for (int col = 0; col <= 7; col++) {
                Coordenada c = new Coordenada((char)('A'+col), r+1);
                cellsMap.put(c, new Celda(context, c, this));
                row.addView(cellsMap.get(c));
            }

            row.addView(getTextView(""+r));
            addView(row);
        }
        addTextViews();
        addDeadCamp(20);
        rellenarTablero();
    }

    private void addTextViews() {
        TableRow row;
        row = new TableRow(getContext());
        row.addView(getTextView(""));

        for (int i = 0; i < 8; i++)
            row.addView(getTextView(String.valueOf((char) ('A' + i))));

        row.addView(getTextView(""));

        addView(row);
    }
    @SuppressLint("ResourceAsColor")
    public void addDeadCamp(int campo){
        TableRow tableRowAux = new TableRow(getContext());
        addView(tableRowAux);
        int id = campo+0;
        TextView tx = getTextView("");
        tx.setBackgroundColor(R.color.white);
        tableRowAux.addView(tx);
        for (int x = 0;x<8;x++){
            tableRowAux.addView(addCamp(id));
            id++;
        }
        tx = getTextView("");
        tx.setBackgroundColor(R.color.white);
        tableRowAux.addView(tx);
        tableRowAux = new TableRow(getContext());
        addView(tableRowAux);
        tx = getTextView("");
        tx.setBackgroundColor(R.color.white);
        tableRowAux.addView(tx);
        for (int x = 0;x<8;x++){
            tableRowAux.addView(addCamp(id));
            id++;
        }
        tx = getTextView("");
        tx.setBackgroundColor(R.color.white);
        tableRowAux.addView(tx);
    }

    @SuppressLint("ResourceType")
    public ImageView addCamp(int id){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widh = displayMetrics.widthPixels;
        ImageView exit = new ImageView(getContext());
        exit.setMaxHeight(widh/10);
        exit.setMinimumHeight(widh/10);
        exit.setMaxWidth(widh/10);
        exit.setMinimumWidth(widh/10);
        exit.setPadding(0,0,0,0);
        exit.setScaleType(ImageView.ScaleType.FIT_CENTER);
        exit.setAdjustViewBounds(true);
        exit.setId(id);
        return exit;
    }

    private TextView getTextView(String text) {

        DisplayMetrics displayMetrics = new DisplayMetrics();

        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int widh = displayMetrics.widthPixels;

        TextView txtView = new TextView(getContext());

        txtView.setText(text);
        txtView.setTextColor(getResources().getColor(R.color.white_piece, getContext().getTheme()));
        txtView.setBackgroundColor(getResources().getColor(R.color.black_piece, getContext().getTheme()));
        txtView.setWidth(widh / 10);
        txtView.setHeight(widh / 10);
        txtView.setGravity(Gravity.CENTER);

        return txtView;
    }


    public void quitarPiezas(){
        for (Celda c: cellsMap.values()) {
            c.setPieza(null);
        }
    }
    public void changeColor(int black, int white){
        for (Celda c: cellsMap.values()) {
            if (((c.getCoordenada().getCol() - 'A') + (c.getCoordenada().getRow() - 1)) % 2 == 0){
                c.setColorOriginal(white);
                c.setColor(white);
                c.updateCellView();
            }else{
                c.setColorOriginal(black);
                c.setColor(black);
                c.updateCellView();
            }
        }
    }

    public void updateCells(){
        for (Celda c:cellsMap.values()) {
            c.updateCellView();
        }
    }

    public void cambiarColores(int colorsRes){

    }

    private void rellenarTablero() {
        new TorreBlanca(getCelda(new Coordenada('A',8)));
        new TorreBlanca(getCelda(new Coordenada('H',8)));
        new CaballoBlanco(getCelda(new Coordenada('B', 8)));
        new CaballoBlanco(getCelda(new Coordenada('G', 8)));
        new AlfilBlanco(getCelda(new Coordenada('C',8)));
        new AlfilBlanco(getCelda(new Coordenada('F',8)));
        new ReinaBlanca(getCelda(new Coordenada('D',8)));
        new ReyBlanco(getCelda(new Coordenada('E',8)));

        new TorreNegra(getCelda(new Coordenada('A',1)));
        new TorreNegra(getCelda(new Coordenada('H',1)));
        new CaballoNegro(getCelda(new Coordenada('B', 1)));
        new CaballoNegro(getCelda(new Coordenada('G', 1)));
        new AlfilNegro(getCelda(new Coordenada('C',1)));
        new AlfilNegro(getCelda(new Coordenada('F',1)));
        new ReinaNegra(getCelda(new Coordenada('D',1)));
        new ReyNegro(getCelda(new Coordenada('E',1)));

        for(int i='A';i<='H';i++)
            new PeonBlanco(getCelda(new Coordenada((char)i,7)));

        for(int i='A';i<='H';i++)
            new PeonNegro(getCelda(new Coordenada((char)i,2)));
    }

    public int getIdDeadBlack() {
        int id = idDeadBlack;
        idDeadBlack++;
        return id;
    }

    public int getIdDeadWhite() {
        int id = idDeadWhite;
        idDeadWhite++;
        return id;
    }

    public Map<Coordenada, Celda> getCellsMap() {
        return cellsMap;
    }

    public Celda getCelda(Coordenada coordenada) {
        return cellsMap.get(coordenada);
    }

//    public DeletePieceManager getManager() {
//        return (DeletePieceManager) deletePieceManager;
//    }

    public void resaltarTablero(Set<Coordenada> aux) {
        for (Coordenada c : aux)
            getCelda(c).resaltar();
    }

    public void desResaltarTablero(Set<Coordenada> aux){
        for (Coordenada c : aux)
            getCelda(c).resetColor();
    }

    public void resaltarTableroOne(Coordenada c){
        getCelda(c).resaltarJaque();
    }

    public void resetColors() {
        for (Celda c : cellsMap.values()) {
            c.resetColor();
        }
    }

    public List<Pieza> piezasEnemigas(Color colorAux) { //Devolver en una lista las fichas del color contrario al del parametro
        return cellsMap.values().stream()
                .map(Celda::getPiece)
                .filter((p) -> p != null && p.getColor() != colorAux)
                .collect(Collectors.toList());
    }

    public List<Pieza> piezasAliadas(Color colorAux) { //Devolver en una lista las fichas del color igual al del parametro
        return cellsMap.values().stream()
                .map(Celda::getPiece)
                .filter((p) -> p != null && p.getColor() == colorAux)
                .collect(Collectors.toList());
    }

    public boolean movesAliadosVacios(Color color) {
        List<Pieza> aliadas = piezasAliadas(color);
        Set<Coordenada> aux;

        for (Pieza p : aliadas) {
            aux = p.getComplexMoves();
            if (!aux.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean estaJaqueColor(Color c) {
        if (c == Color.WHITE) {
            return reyJaque(Pieza.PieceType.WHITE_REY);
        } else {
            return reyJaque(Pieza.PieceType.BLACK_REY);
        }
    }

    public boolean reyJaque(Pieza.PieceType p) {
        boolean ayuda;
        Optional<Pieza> aux = cellsMap.values().stream()
                .map(Celda::getPiece)
                .filter((z) -> z != null && z.getType() == p)
                .findFirst();
        Rey reyAux;
        if (aux.isPresent()) {
            reyAux = ((Rey) aux.get());
            ayuda = reyAux.estaEnJaque(reyAux.getCelda().getCoordenada());
            if (ayuda)
                resaltarTableroOne(reyAux.getCelda().getCoordenada());
            return ayuda;
        } else {
            return false;
        }
    }

    public boolean estaMateColor(Color c) {
        if (c == Color.WHITE) {
            return reyMate(Pieza.PieceType.WHITE_REY);
        } else {
            return reyMate(Pieza.PieceType.BLACK_REY);
        }
    }

    public boolean reyMate(Pieza.PieceType p) {
        for (Celda col : cellsMap.values()) {
            if (!col.isEmpty()) {
                if (col.getPiece().getType() == p) {
                    Rey reyAux = (Rey) col.getPiece();
                    return reyAux.mate();
                }
            }
        }
        return false;
    }

    public boolean hayTablasColor(Color c) {
        if (c == Color.WHITE) {
            return reyTablas(Pieza.PieceType.WHITE_REY);
        } else {
            return reyTablas(Pieza.PieceType.BLACK_REY);
        }
    }

    public boolean reyTablas(Pieza.PieceType p) {
        for (Celda col : cellsMap.values()) {
            if (!col.isEmpty()) {
                if (col.getPiece().getType() == p) {
                    Rey reyAux = (Rey) col.getPiece();
                    return reyAux.tablas(reyAux.getCelda().getCoordenada());
                }
            }
        }
        return false;
    }

    public boolean getTorreCorta(Color color, Coordenada coordenada) {
        Coordenada coordAux = coordenada.casillaRight().casillaRight().casillaRight();
        Pieza piezaAux = getCelda(coordAux).getPiece();

        if (piezaAux instanceof Torre) {
            if (piezaAux.getColor() == color) {
                return !((Torre) (piezaAux)).seHaMovido();
            }
        }
        return false;
    }

    public boolean getTorreLarga(Color color, Coordenada coordenada) {
        Coordenada coordAux = coordenada.casillaLeft().casillaLeft().casillaLeft().casillaLeft();
        Pieza piezaAux = getCelda(coordAux).getPiece();

        if (piezaAux instanceof Torre) {
            if (piezaAux.getColor() == color) {
                return !((Torre) (piezaAux)).seHaMovido();
            }
        }
        return false;
    }

    public void setCellListener(View.OnClickListener viewOnClickListener){
        for(Celda celda : cellsMap.values())
            celda.setOnClickListener(viewOnClickListener);
    }
}
