package es.upm.miw.bantumi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import es.upm.miw.bantumi.activity.LeaderboardActivity;

public class DeleteAlertDialog extends AppCompatDialogFragment {

    int title;
    int message;


    public DeleteAlertDialog(int title, int message) {
        this.title = title;
        this.message = message;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LeaderboardActivity leaderboardActivity = (LeaderboardActivity) getActivity();

        assert leaderboardActivity != null;
        AlertDialog.Builder builder = new AlertDialog.Builder(leaderboardActivity);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(
                        getString(R.string.diálogoYes),
                        (dialog, which) -> {
                           MainActivity.puntuacionViewModel.delete();
                        }
                )
                .setNegativeButton(
                        getString(R.string.diálogoNo),
                        (dialog, which) -> {
                        }
                );
        return builder.create();
    }

}
