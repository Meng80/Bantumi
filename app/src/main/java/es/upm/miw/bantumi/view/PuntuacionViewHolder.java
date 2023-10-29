package es.upm.miw.bantumi.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import es.upm.miw.bantumi.R;

import androidx.recyclerview.widget.RecyclerView;

public class PuntuacionViewHolder extends RecyclerView.ViewHolder {
    private final TextView puntuacionItemView;

    private PuntuacionViewHolder(View itemView) {
        super(itemView);
        puntuacionItemView = itemView.findViewById(R.id.item_textView);
    }

    public void bind(String text) {
        puntuacionItemView.setText(text);
    }

    static PuntuacionViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new PuntuacionViewHolder(view);
    }
}