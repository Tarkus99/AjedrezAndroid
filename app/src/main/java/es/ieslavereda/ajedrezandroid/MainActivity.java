package es.ieslavereda.ajedrezandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Tablero board;
    private TextView textView;
    private EditText puntB,puntW;

    private Pieza piezaActual;

    private Color turno;
    private VisorPiezasMuertas deadWhites;
    private VisorPiezasMuertas deadBlacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board = findViewById(R.id.board);
        textView = findViewById(R.id.textView);
        turno = Color.WHITE;
        puntB = findViewById(R.id.puntB);
        puntW= findViewById(R.id.puntW);
        deadWhites = findViewById(R.id.whiteDeads);
        deadWhites.setColor(getResources().getColor(R.color.black_piece));
        deadBlacks = findViewById(R.id.blackDeads);
        deadBlacks.setColor(getResources().getColor(R.color.white_piece));

        board.setCellListener(this);
        int flag = (int)getIntent().getExtras().getSerializable("colorF");
        if (flag==0){
            deadBlacks.setBackgroundColor(getColor(R.color.classic_visor));
            deadWhites.setBackgroundColor(getColor(R.color.classic_visor));
        }else if (flag==1){
            board.changeColor(R.color.bosque_black, R.color.bosque_white);
            deadBlacks.setBackgroundColor(getColor(R.color.bosque_visor));
            deadWhites.setBackgroundColor(getColor(R.color.bosque_visor));
        }else if (flag==2){
            board.changeColor(R.color.medieval_black, R.color.medieval_white);
            deadBlacks.setBackgroundColor(getColor(R.color.medieval_visor));
            deadWhites.setBackgroundColor(getColor(R.color.medieval_visor));
        }else{
            board.changeColor(R.color.marino_black, R.color.marino_white);
            deadBlacks.setBackgroundColor(getColor(R.color.marino_visor));
            deadWhites.setBackgroundColor(getColor(R.color.marino_visor));
        }
        board.updateCells();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        if (!board.estaMateColor(turno)) {

            if (view instanceof Celda) {
                Celda c = (Celda) view;
                if (esCeldaConPiezaDelTurno(c)) {
                    if (piezaActual!=null)
                        board.resetColors(piezaActual.getComplexMoves());
                    seleccionarPieza(c);
                } else {
                    if (piezaActual != null) {
                        if (esMovimientoValido(c)) {
                            piezaActual.moveTo(c.getCoordenada(), getVisor(turno));
                            turno = turno.next();
                            if (board.estaMateColor(turno))
                                textView.setText(turno.toString() + " pierden por jaque mate");
                        } else {
                            board.resetColors(piezaActual.getComplexMoves());
                        }
                        piezaActual = null;
                    }
                }
            }
            setPuntuacion();
        }
    }

    private boolean esMovimientoValido(Celda c) {
        return piezaActual.getComplexMoves().contains(c.getCoordenada());
    }

    private void seleccionarPieza(Celda c) {
        piezaActual = c.getPiece();
        board.resaltarTablero(c.getPiece().getComplexMoves());
    }

    private boolean esCeldaConPiezaDelTurno(Celda c) {
        return c.getPiece() != null && c.getPiece().getColor() == turno;
    }

    public VisorPiezasMuertas getVisor(Color c){
        if (c==Color.WHITE)
            return deadWhites;
        return deadBlacks;
    }

    public void setPuntuacion(){
        int w = deadWhites.getAllValor();
        int b = deadBlacks.getAllValor();
        puntB.setText("");
        puntW.setText("");

        if (w>b)
            puntW.setText("+" + (w-b));
        else if (b>w)
            puntB.setText("+" + (b-w));
    }
}