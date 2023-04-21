package es.ieslavereda.ajedrezandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    private RadioButton classic, bosque, med, marino;
    private Button caca;
    private Tablero tablero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        classic = findViewById(R.id.classic);
        bosque=findViewById(R.id.bosque);
        med=findViewById(R.id.medieval);
        marino=findViewById(R.id.marino);
        caca =findViewById(R.id.guardar);
        tablero=findViewById(R.id.tablero3);


        caca.setOnClickListener(view -> {
            Intent i = new Intent();
            if (bosque.isChecked())
                i.putExtra("n", 1);
            else if(med.isChecked())
                i.putExtra("n", 2);
            else
                i.putExtra("n", 3);
            setResult(RESULT_OK, i);
            finish();
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.classic)
            tablero.changeColor(R.color.black, R.color.white);
        else if(view.getId()==R.id.bosque)
            tablero.changeColor(R.color.bosque_black, R.color.bosque_white);
        else if (view.getId()==R.id.medieval)
            tablero.changeColor(R.color.medieval_black, R.color.medieval_white);
        else
            tablero.changeColor(R.color.marino_black, R.color.marino_white);
    }
}