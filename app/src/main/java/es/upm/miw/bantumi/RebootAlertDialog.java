package es.upm.miw.bantumi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class RebootAlertDialog extends AppCompatDialogFragment {
    interface Back {
        void onSuccess();
    }

    int title;
    int message;
    Back back;

    public RebootAlertDialog(int title, int message, Back back) {
        this.title = title;
        this.message = message;
        this.back = back;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MainActivity main = (MainActivity) getActivity();

        assert main != null;
        AlertDialog.Builder builder = new AlertDialog.Builder(main);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(
                        getString(R.string.diálogoYes),
                        (dialog, which) -> {
                            back.onSuccess();
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
