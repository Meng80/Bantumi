package es.upm.miw.bantumi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.google.android.material.snackbar.Snackbar;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;


import es.upm.miw.bantumi.activity.LeaderboardActivity;
import es.upm.miw.bantumi.db.Puntuacion;
import es.upm.miw.bantumi.db.PuntuacionViewModel;
import es.upm.miw.bantumi.model.BantumiViewModel;


public class MainActivity extends AppCompatActivity {

    protected static final String LOG_TAG = "MiW";
    public JuegoBantumi juegoBantumi;
    BantumiViewModel bantumiVM;
    int numInicialSemillas;
    boolean changed = false;
    boolean began = false;

    SharedPreferences sharedPreferences;

    public static PuntuacionViewModel puntuacionViewModel;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instancia el ViewModel y el juego, y asigna observadores a los huecos
        numInicialSemillas = getResources().getInteger(R.integer.intNumInicialSemillas);
        bantumiVM = new ViewModelProvider(this).get(BantumiViewModel.class);
        juegoBantumi = new JuegoBantumi(bantumiVM, JuegoBantumi.Turno.turnoJ1, numInicialSemillas);
        crearObservadores();

        puntuacionViewModel = new ViewModelProvider(this).get(PuntuacionViewModel.class);

    }
    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }


    /**
     * Crea y subscribe los observadores asignados a las posiciones del tablero.
     * Si se modifica el contenido del tablero -> se actualiza la vista.
     */
    private void crearObservadores() {
        for (int i = 0; i < JuegoBantumi.NUM_POSICIONES; i++) {
            int finalI = i;
            bantumiVM.getNumSemillas(i).observe(    // Huecos y almacenes
                    this,
                    new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            mostrarValor(finalI, juegoBantumi.getSemillas(finalI));
                            if(began) changed = true;
                        }
                    });
        }
        bantumiVM.getTurno().observe(   // Turno
                this,
                new Observer<JuegoBantumi.Turno>() {
                    @Override
                    public void onChanged(JuegoBantumi.Turno turno) {
                        marcarTurno(juegoBantumi.turnoActual());
                        if(!began) began = true;
                    }
                }
        );
    }

    /**
     * Indica el turno actual cambiando el color del texto
     *
     * @param turnoActual turno actual
     */
    private void marcarTurno(@NonNull JuegoBantumi.Turno turnoActual) {
        TextView tvJugador1 = findViewById(R.id.tvPlayer1);
        TextView tvJugador2 = findViewById(R.id.tvPlayer2);
        switch (turnoActual) {
            case turnoJ1:
                tvJugador1.setTextColor(getColor(R.color.white));
                tvJugador1.setBackgroundColor(getColor(android.R.color.holo_blue_light));
                tvJugador2.setTextColor(getColor(R.color.black));
                tvJugador2.setBackgroundColor(getColor(R.color.white));
                break;
            case turnoJ2:
                tvJugador1.setTextColor(getColor(R.color.black));
                tvJugador1.setBackgroundColor(getColor(R.color.white));
                tvJugador2.setTextColor(getColor(R.color.white));
                tvJugador2.setBackgroundColor(getColor(android.R.color.holo_blue_light));
                break;
            default:
                tvJugador1.setTextColor(getColor(R.color.black));
                tvJugador2.setTextColor(getColor(R.color.black));
        }
    }

    /**
     * Muestra el valor <i>valor</i> en la posición <i>pos</i>
     *
     * @param pos posición a actualizar
     * @param valor valor a mostrar
     */
    private void mostrarValor(int pos, int valor) {
        String num2digitos = String.format(Locale.getDefault(), "%02d", pos);
        // Los identificadores de los huecos tienen el formato casilla_XX
        int idBoton = getResources().getIdentifier("casilla_" + num2digitos, "id", getPackageName());
        if (0 != idBoton) {
            TextView viewHueco = findViewById(idBoton);
            viewHueco.setText(String.valueOf(valor));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcAjustes: // @todo Preferencias
                startActivity(new Intent(this, BantumiPrefs.class));
                return true;

            case R.id.opcAcercaDe:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.aboutTitle)
                        .setMessage(R.string.aboutMessage)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                return true;

            case R.id.opcReiniciarPartida:
                RebootAlertDialog.Back callBack = () ->{
                    changed = false;
                    began = false;
                    bantumiVM.clear();
                    juegoBantumi.clear(JuegoBantumi.Turno.turnoJ1);
                    crearObservadores();
                };
                if(changed){
                    new RebootAlertDialog(R.string.títuloDiálogoReiniciar, R.string.msgDiálogo, callBack).show(getSupportFragmentManager(), "DIALOGO_REINICIAR");
                    resetBackgrounds();
                }
                else
                    callBack.onSuccess();
                return true;

            case R.id.opcGuardarPartida:
                String jsonSave = juegoBantumi.serializa();
                try {
                    Context context = getApplicationContext();
                    FileOutputStream outStream = context.openFileOutput("InformacionTablero.json", Context.MODE_PRIVATE);
                    outStream.write(jsonSave.getBytes(StandardCharsets.UTF_8));
                    outStream.close();

                    Toast.makeText(context, context.getString(R.string.títuloDiálogoGuardar), Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

                return true;

            case R.id.opcRecuperarPartida:
                RebootAlertDialog.Back callback = () -> {
                    try {
                        FileInputStream in = openFileInput("InformacionTablero.json");
                        InputStreamReader inputStreamReader = new InputStreamReader(in);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            sb.append(line);
                        }
                        inputStreamReader.close();
                        juegoBantumi.deserializa(sb.toString());

                        Toast.makeText(MainActivity.this, R.string.títuloDiálogoRecuperar, Toast.LENGTH_LONG).show();
                        changed = false;
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                };
                if (changed) {
                    new RebootAlertDialog(R.string.msgDialogoRecuperar, R.string.diálogoRecuperar, callback).show(getSupportFragmentManager(), "ALERT_DIALOG");
                } else {
                    callback.onSuccess();
                }
                return true;

            case R.id.opcMejoresResultados:
                Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
                startActivity(intent);
                return true;


            // @TODO!!! resto opciones

            default:
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.txtSinImplementar),
                        Snackbar.LENGTH_LONG
                ).show();
        }
        return true;
    }

    /**
     * Acción que se ejecuta al pulsar sobre cualquier hueco
     *
     * @param v Vista pulsada (hueco)
     */
    public void huecoPulsado(@NonNull View v) {
        String resourceName = getResources().getResourceEntryName(v.getId()); // pXY
        int num = Integer.parseInt(resourceName.substring(resourceName.length() - 2));
        Log.i(LOG_TAG, "huecoPulsado(" + resourceName + ") num=" + num);
        switch (juegoBantumi.turnoActual()) {
            case turnoJ1:
                juegoBantumi.jugar(num);
                v.setBackground(getResources().getDrawable(R.drawable.marca_jugador2_background));
                break;
            case turnoJ2:
                juegaComputador();
                v.setBackground(getResources().getDrawable(R.drawable.marca_jugador2_background));
                v.setBackground(getResources().getDrawable(R.drawable.semi_transparent_background));
                break;
            default:    // JUEGO TERMINADO
                finJuego();
        }
        if (juegoBantumi.juegoTerminado()) {
            finJuego();
        }
    }

    private void resetBackgrounds() {
        for (int i = 0; i < JuegoBantumi.NUM_POSICIONES; i++) {
            String num2digitos = String.format(Locale.getDefault(), "%02d", i);
            int idBoton = getResources().getIdentifier("casilla_" + num2digitos, "id", getPackageName());
            if (idBoton != 0) {
                View viewHueco = findViewById(idBoton);
                viewHueco.setBackgroundResource(R.drawable.bantumi_button_background);
                viewHueco.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        huecoPulsado(v);
                    }
                });
            }
        }
    }

    /**
     * Elige una posición aleatoria del campo del jugador2 y realiza la siembra
     * Si mantiene turno -> vuelve a jugar
     */
    void juegaComputador() {
        while (juegoBantumi.turnoActual() == JuegoBantumi.Turno.turnoJ2) {
            int pos = 7 + (int) (Math.random() * 6);    // posición aleatoria [7..12]
            Log.i(LOG_TAG, "juegaComputador(), pos=" + pos);
            if (juegoBantumi.getSemillas(pos) != 0 && (pos < 13)) {
                juegoBantumi.jugar(pos);
            } else {
                Log.i(LOG_TAG, "\t posición vacía");
            }
        }
    }

    /**
     * El juego ha terminado. Volver a jugar?
     */
    private void finJuego() {
        resetBackgrounds();
        TextView tvJugador2 = findViewById(R.id.tvPlayer2);
        String texto = (juegoBantumi.getSemillas(6) > 6 * numInicialSemillas)
                ? "Gana Jugador 1"
                : "Gana Jugador 2";
        if (juegoBantumi.getSemillas(6) == 6 * numInicialSemillas) {
            texto = "¡¡¡ EMPATE !!!";
        }
        Snackbar.make(
                findViewById(android.R.id.content),
                texto,
                Snackbar.LENGTH_LONG
        )
        .show();

        // @TODO guardar puntuación
        //Terminar
        new FinalAlertDialog().show(getSupportFragmentManager(), "ALERT_DIALOG");

        //Ajuste
        String nombreJugador = sharedPreferences.getString(
                "nombre",
                getString(R.string.prefNameplayer)
        );

        //save
        Puntuacion puntuacion = new Puntuacion(nombreJugador, juegoBantumi.getSemillas(6), tvJugador2.getText().toString(), juegoBantumi.getSemillas(13));
        puntuacionViewModel.insert(puntuacion);

        }


}