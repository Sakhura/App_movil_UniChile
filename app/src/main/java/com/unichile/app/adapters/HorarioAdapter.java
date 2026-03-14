package com.unichile.app.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unichile.app.R;
import com.unichile.app.models.Materia;

import java.util.ArrayList;
import java.util.List;

public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.VH> {

    private final Context ctx;
    private List<Materia> data;

    public HorarioAdapter(Context ctx, List<Materia> data) {
        this.ctx = ctx;
        this.data = data != null ? data : new ArrayList<>();
    }

    public void updateData(List<Materia> newData) {
        this.data = newData != null ? newData : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_horario, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Materia m = data.get(pos);
        h.tvHoraInicio.setText(m.getHoraInicio());
        h.tvHoraFin.setText(m.getHoraFin());
        h.tvNombre.setText(m.getNombre());
        h.tvSala.setText("📍 " + m.getSala());
        h.tvProfesor.setText("👤 " + m.getProfesor());
        try {
            h.viewColor.setBackgroundColor(Color.parseColor(m.getColor()));
        } catch (Exception e) {
            h.viewColor.setBackgroundColor(Color.GRAY);
        }
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvHoraInicio, tvHoraFin, tvNombre, tvSala, tvProfesor;
        View viewColor;
        VH(View v) {
            super(v);
            tvHoraInicio = v.findViewById(R.id.tvHoraInicio);
            tvHoraFin    = v.findViewById(R.id.tvHoraFin);
            tvNombre     = v.findViewById(R.id.tvNombre);
            tvSala       = v.findViewById(R.id.tvSala);
            tvProfesor   = v.findViewById(R.id.tvProfesor);
            viewColor    = v.findViewById(R.id.viewColor);
        }
    }
}
