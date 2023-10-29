package es.upm.miw.bantumi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import es.upm.miw.bantumi.model.BantumiViewModel;

public class NewGameActivity extends AppCompatActivity {
    public JuegoBantumi juegoBantumi;
    BantumiViewModel bantumiVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);


        RadioGroup playerSelectGroup = findViewById(R.id.playerSelectGroup);
        EditText seedCountEditText = findViewById(R.id.seedCountEditText);
        Button startGameButton = findViewById(R.id.startGameButton);

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedPlayerId = playerSelectGroup.getCheckedRadioButtonId();
                int initialSeedCount = Integer.parseInt(seedCountEditText.getText().toString());


                initializeGame(selectedPlayerId, initialSeedCount);

                Intent intent = new Intent(NewGameActivity.this, MainActivity.class);
                intent.putExtra("selectedPlayerId", selectedPlayerId);
                intent.putExtra("initialSeedCount", initialSeedCount);

                startActivity(intent);

            }
        });
    }

    void initializeGame(int selectedPlayerId, int initialSeedCount) {

        if (selectedPlayerId == R.id.player1RadioButton) {
            juegoBantumi = new JuegoBantumi(bantumiVM, JuegoBantumi.Turno.turnoJ1, initialSeedCount);

        } else if (selectedPlayerId == R.id.player2RadioButton) {
            juegoBantumi = new JuegoBantumi(bantumiVM, JuegoBantumi.Turno.turnoJ2, initialSeedCount);
        }

    }
}

