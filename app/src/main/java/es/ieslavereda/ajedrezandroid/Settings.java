package es.ieslavereda.ajedrezandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;

import java.io.Serializable;
import java.util.concurrent.CancellationException;

public class Settings extends AppCompatActivity implements View.OnClickListener {
    private ImageButton save;

    private Tablero tablero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        save=findViewById(R.id.guardar);
        tablero=findViewById(R.id.tablero3);

        save.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.classic) {
            DynamicColor.getInstance().setWhiteCell(R.color.white);
            DynamicColor.getInstance().setBlackCell(R.color.black);

            tablero.changeColor();
        }else if(view.getId()==R.id.bosque) {
            DynamicColor.getInstance().setWhiteCell(R.color.bosque_white);
            DynamicColor.getInstance().setBlackCell(R.color.bosque_black);
            tablero.changeColor();
        }else if (view.getId()==R.id.medieval) {
            DynamicColor.getInstance().setWhiteCell(R.color.medieval_white);
            DynamicColor.getInstance().setBlackCell(R.color.medieval_black);
            tablero.changeColor();
        }else {
            DynamicColor.getInstance().setWhiteCell(R.color.marino_white);
            DynamicColor.getInstance().setBlackCell(R.color.marino_black);
            tablero.changeColor();
        }
    }
}