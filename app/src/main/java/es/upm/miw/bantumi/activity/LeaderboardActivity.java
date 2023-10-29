package es.upm.miw.bantumi.activity;

import static es.upm.miw.bantumi.MainActivity.puntuacionViewModel;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import es.upm.miw.bantumi.DeleteAlertDialog;
import es.upm.miw.bantumi.R;

import es.upm.miw.bantumi.view.PuntuacionListAdapter;


public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PuntuacionListAdapter adapter = new PuntuacionListAdapter(new PuntuacionListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        puntuacionViewModel.getAllPuntuaciones().observe(this, words -> {
            adapter.submitList(words);
        });

        Button delete = findViewById(R.id.button_delete);
        delete.setOnClickListener(view->{
            DeleteAlertDialog deleteAlertDialog = new DeleteAlertDialog(R.string.títuloDiálogoDelete, R.string.diálogoDelete);
            deleteAlertDialog.show(getSupportFragmentManager(), "DIALOGO_DELETE");
        });

    }

}
