package com.unichile.app.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unichile.app.R;
import com.unichile.app.models.Noticia;

import java.util.List;

public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.VH> {

    private final Context ctx;
    private final List<Noticia> data;

    public NoticiasAdapter(Context ctx, List<Noticia> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_noticia, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Noticia n = data.get(pos);
        h.tvTitulo.setText(n.getTitulo());
        h.tvDescripcion.setText(n.getDescripcion());
        h.tvFecha.setText(n.getFecha());
        h.tvCategoria.setText(n.getCategoria());

        // Category badge
        GradientDrawable bg = new GradientDrawable();
        bg.setCornerRadius(24f);
        bg.setColor(getCategoriaColor(n.getCategoria()));
        h.tvCategoria.setBackground(bg);
    }

    private int getCategoriaColor(String cat) {
        if (cat == null) return Color.parseColor("#607D8B");
        switch (cat) {
            case "Académico":       return Color.parseColor("#1565C0");
            case "Eventos":         return Color.parseColor("#2E7D32");
            case "Infraestructura": return Color.parseColor("#E65100");
            case "Becas":           return Color.parseColor("#6A1B9A");
            case "Deportes":        return Color.parseColor("#00695C");
            case "Servicios":       return Color.parseColor("#4527A0");
            default:                return Color.parseColor("#607D8B");
        }
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvDescripcion, tvFecha, tvCategoria;
        VH(View v) {
            super(v);
            tvTitulo      = v.findViewById(R.id.tvTitulo);
            tvDescripcion = v.findViewById(R.id.tvDescripcion);
            tvFecha       = v.findViewById(R.id.tvFecha);
            tvCategoria   = v.findViewById(R.id.tvCategoria);
        }
    }
}
