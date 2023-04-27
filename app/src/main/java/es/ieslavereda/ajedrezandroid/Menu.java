package es.ieslavereda.ajedrezandroid;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Menu extends AppCompatActivity {

    private Tablero tablero;
    private ImageButton jugar, setting;
    int colorW, colorB;
    int colorFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        colorFlag=0;
        setting=findViewById(R.id.settings);
        jugar=findViewById(R.id.play);
        tablero = findViewById(R.id.tablero2);

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                        tablero.changeColor();
                });

        setting.setOnClickListener(view -> {
            Intent intent = new Intent(this, Settings.class);
            someActivityResultLauncher.launch(intent);
        });

        jugar.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("colorF", colorFlag);
            someActivityResultLauncher.launch(intent);
        });

        //tablero.quitarPiezas();
    }
}