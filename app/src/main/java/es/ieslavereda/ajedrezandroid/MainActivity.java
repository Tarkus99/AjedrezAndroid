package es.ieslavereda.ajedrezandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Tablero board;
    private TextView textView, txtTime;

    private Pieza piezaActual;

    private Color turno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board = findViewById(R.id.board);
        textView = findViewById(R.id.textView);
        txtTime = findViewById(R.id.txtTime);
        turno = Color.WHITE;

        board.setCellListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        if (!board.estaMateColor(turno)) {

            if (view instanceof Celda) {
                Celda c = (Celda) view;
                if (esCeldaConPiezaDelTurno(c)) {
                    if (piezaActual != null)
                        board.desResaltarTablero(piezaActual.getComplexMoves());
                    seleccionarPieza(c);
                } else {
                    if (piezaActual != null) {
                        if (esMovimientoValido(c)) {
                            piezaActual.moveTo(c.getCoordenada());
                        } else {
                            board.resetColors();
                        }
                        piezaActual = null;
                        turno = turno.next();
                        if (board.estaMateColor(turno))
                            textView.setText(turno.toString() + " pierden por jaque mate");
                    }
                }
            }
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

    private class Timer {
        private int time;
        //private Partida partida;
        Integer min;
        Integer seg;
        Integer centSeg;
        private boolean exit;

        public Timer(int time) {
            this.time = time;
            //partida = p;
            exit = false;
        }

        public void start() {
            run();
        }

        public void stop() {
            parar();
        }

        public void parar() {
            exit = true;
        }

        public void run() {
            int a = (min == null) ? time : min;
            int b = (seg == null) ? 0 : seg;
            int c = (centSeg == null) ? 0 : centSeg;
            int i = a;
            while (i >= 0 && !exit) {
                //minutos.setText("" + i);
                int j = b;
                while (j >= 0 && !exit) {
                    //segundos.setText("" + j);
                    int k = c;
                    while (k >= 0 && !exit) {
                        seg = j;
                        min = i;
                        centSeg = k;
                        // centesimas.setText((k < 10) ? 0 + "" + k : "" + k);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        k--;
                    }
                    c = 99;
                    j--;
                }
                b = 59;
                i--;
            }
//            if (!exit)
//                partida.finish();
        }
    }
}