package es.ieslavereda.ajedrezandroid;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.Map;

public class Menu extends AppCompatActivity {

    private Tablero tablero;
    private Button jugar, setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setting=findViewById(R.id.setting);
        jugar=findViewById(R.id.jugar);
        tablero = findViewById(R.id.tablero2);

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK){
                        int a, b;
                        Intent data = result.getData();
                        int x = (int) data.getExtras().getSerializable("n");
                        if (x==1) {
                            a = R.color.bosque_black;
                            b = R.color.bosque_white;
                        }else if (x==2){
                            a = R.color.medieval_black;
                            b = R.color.medieval_white;
                        }else{
                            a = R.color.marino_black;
                            b = R.color.marino_white;
                        }
                        tablero.changeColor(a, b);
                        tablero.updateCells();
                    }
                });

        setting.setOnClickListener(view -> {
            Intent intent = new Intent(this, Settings.class);
            someActivityResultLauncher.launch(intent);
        });

        //tablero.quitarPiezas();
    }
}