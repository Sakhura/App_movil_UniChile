package com.unichile.app.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unichile.app.R;
import com.unichile.app.database.DBHelper;
import com.unichile.app.models.Materia;
import com.unichile.app.models.Nota;

import java.util.ArrayList;
import java.util.List;

public class NotasAdapter extends RecyclerView.Adapter<NotasAdapter.VH> {

    private final Context ctx;
    private final List<Materia> materias;
    private final DBHelper db;
    private final List<Boolean> expanded;

    public NotasAdapter(Context ctx, List<Materia> materias, DBHelper db) {
        this.ctx = ctx;
        this.materias = materias;
        this.db = db;
        this.expanded = new ArrayList<>();
        for (int i = 0; i < materias.size(); i++) expanded.add(false);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_materia_nota, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Materia m = materias.get(pos);
        double prom = db.getPromedioMateria(m.getId());

        h.tvNombre.setText(m.getNombre());
        h.tvProfesor.setText(m.getProfesor());

        // Color left bar
        try {
            h.viewColor.setBackgroundColor(Color.parseColor(m.getColor()));
        } catch (Exception e) {
            h.viewColor.setBackgroundColor(Color.GRAY);
        }

        // Promedio badge
        String promStr = String.format("%.1f", prom);
        h.tvPromedio.setText(promStr);
        int badgeColor;
        if (prom >= 5.5) badgeColor = Color.parseColor("#2E7D32");
        else if (prom >= 4.0) badgeColor = Color.parseColor("#F57F17");
        else badgeColor = Color.parseColor("#C62828");
        GradientDrawable circle = new GradientDrawable();
        circle.setShape(GradientDrawable.OVAL);
        circle.setColor(badgeColor);
        h.tvPromedio.setBackground(circle);

        // Expand/collapse
        boolean isExp = expanded.get(pos);
        h.tvExpand.setText(isExp ? "▲" : "▼");
        h.llNotas.setVisibility(isExp ? View.VISIBLE : View.GONE);

        if (isExp && h.llNotas.getChildCount() == 0) {
            buildNotasView(h.llNotas, m.getId());
        }

        h.llHeader.setOnClickListener(v -> {
            int p = h.getAdapterPosition();
            if (p == RecyclerView.NO_ID) return;
            boolean wasExp = expanded.get(p);
            expanded.set(p, !wasExp);
            if (!wasExp) h.llNotas.removeAllViews(); // force rebuild
            notifyItemChanged(p);
        });
    }

    private void buildNotasView(LinearLayout container, int materiaId) {
        List<Nota> notas = db.getNotasByMateriaId(materiaId);
        // Header row
        LinearLayout header = makeRow(ctx, "Evaluación", "Nota", "%", true);
        container.addView(makeDivider());
        container.addView(header);
        container.addView(makeDivider());
        for (Nota n : notas) {
            LinearLayout row = makeRow(ctx,
                n.getEvaluacion(),
                String.format("%.1f", n.getValor()),
                n.getPorcentaje() + "%", false);
            // Color nota text
            TextView tvNota = (TextView) ((LinearLayout) row.getChildAt(1)).getChildAt(0);
            double val = n.getValor();
            if (val >= 5.5) tvNota.setTextColor(Color.parseColor("#2E7D32"));
            else if (val >= 4.0) tvNota.setTextColor(Color.parseColor("#F57F17"));
            else tvNota.setTextColor(Color.parseColor("#C62828"));
            container.addView(row);
        }
        container.addView(makeDivider());
    }

    private LinearLayout makeRow(Context ctx, String col1, String col2, String col3, boolean bold) {
        LinearLayout row = new LinearLayout(ctx);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(0, 8, 0, 8);
        row.addView(makeCell(ctx, col1, 1f, bold));
        row.addView(makeCell(ctx, col2, 0.4f, bold));
        row.addView(makeCell(ctx, col3, 0.4f, bold));
        return row;
    }

    private LinearLayout makeCell(Context ctx, String text, float weight, boolean bold) {
        LinearLayout cell = new LinearLayout(ctx);
        cell.setLayoutParams(new LinearLayout.LayoutParams(0,
            LinearLayout.LayoutParams.WRAP_CONTENT, weight));
        TextView tv = new TextView(ctx);
        tv.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT));
        tv.setText(text);
        tv.setTextSize(13f);
        tv.setTextColor(bold ? Color.parseColor("#212121") : Color.parseColor("#424242"));
        if (bold) tv.setTypeface(null, android.graphics.Typeface.BOLD);
        cell.addView(tv);
        return cell;
    }

    private View makeDivider() {
        View v = new View(ctx);
        v.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 1));
        v.setBackgroundColor(Color.parseColor("#E0E0E0"));
        return v;
    }

    @Override
    public int getItemCount() { return materias.size(); }

    static class VH extends RecyclerView.ViewHolder {
        LinearLayout llHeader, llNotas;
        TextView tvNombre, tvProfesor, tvPromedio, tvExpand;
        View viewColor;
        VH(View v) {
            super(v);
            llHeader   = v.findViewById(R.id.llHeader);
            llNotas    = v.findViewById(R.id.llNotas);
            tvNombre   = v.findViewById(R.id.tvMateriaNombre);
            tvProfesor = v.findViewById(R.id.tvProfesor);
            tvPromedio = v.findViewById(R.id.tvPromedio);
            tvExpand   = v.findViewById(R.id.tvExpand);
            viewColor  = v.findViewById(R.id.viewColor);
        }
    }
}
