package es.ieslavereda.ajedrezandroid.Timer;

//import es.ieslavereda.model.GAME.Partida;

import android.content.AsyncTaskLoader;
import android.os.AsyncTask;

import java.io.Serializable;

import es.ieslavereda.ajedrezandroid.Partida;

public class Timer extends AsyncTask {
    private int time;
    private Partida partida;
    Integer min;
    Integer seg;
    Integer centSeg;
    private boolean exit;

    public Timer(int time, Partida p) {
        this.time = time;
        partida = p;
        exit=false;
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
        if (!exit)
            partida.finish();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }
}
