package es.ieslavereda.ajedrezandroid;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class VisorPiezasMuertas extends LinearLayout {

    private List<Pieza> piezaList;
    private int color;

    public VisorPiezasMuertas(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        piezaList = new ArrayList<>();
    }

    public void setColor(int c){
        color = c;
    }
    public void addPiece(Pieza q) {
        piezaList.add(q);
        piezaList.sort(Pieza::compareTo);
        this.removeAllViews();
        for (Pieza p : piezaList) {
            this.addView(getTextView(p.getType().getUnicode()+""));
        }
    }

    public List<Pieza> getPiezaList() {
        return piezaList;
    }


    private TextView getTextView(String text) {

        TextView txtView = new TextView(getContext());

        txtView.setText(text);
//        txtView.setTextColor(getResources().getColor(R.color.white_piece, getContext().getTheme()));
//        txtView.setBackgroundColor(getResources().getColor(R.color.black_piece, getContext().getTheme()));
//        txtView.setWidth(widh / 10);
//        txtView.setHeight(widh / 10);
        txtView.setTextSize(32);
        txtView.setGravity(Gravity.CENTER);
        txtView.setTextColor(color);

        return txtView;
    }

    public int getAllValor(){
        int aux = 0;
        for (Pieza p:piezaList) {
            aux+=p.getValor();
        }
        return aux;
    }
}
