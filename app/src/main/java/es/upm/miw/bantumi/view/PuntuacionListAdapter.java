package es.upm.miw.bantumi.view;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import es.upm.miw.bantumi.db.Puntuacion;


public class PuntuacionListAdapter extends ListAdapter<Puntuacion, PuntuacionViewHolder> {

    public PuntuacionListAdapter(@NonNull DiffUtil.ItemCallback<Puntuacion> diffCallback) {
        super(diffCallback);
    }

    @Override
    public PuntuacionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PuntuacionViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(PuntuacionViewHolder holder, int position) {
        Puntuacion current = getItem(position);
        holder.bind(current.getPuntuacion());
    }

    public static class WordDiff extends DiffUtil.ItemCallback<Puntuacion> {

        @Override
        public boolean areItemsTheSame(@NonNull Puntuacion oldItem, @NonNull Puntuacion newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Puntuacion oldItem, @NonNull Puntuacion newItem) {
            return oldItem.getPuntuacion().equals(newItem.getPuntuacion());
        }
    }
}