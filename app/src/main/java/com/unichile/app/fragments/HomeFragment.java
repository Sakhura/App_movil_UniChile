package com.unichile.app.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.unichile.app.R;
import com.unichile.app.activities.MainActivity;
import com.unichile.app.database.DBHelper;
import com.unichile.app.databinding.FragmentHomeBinding;
import com.unichile.app.models.Materia;
import com.unichile.app.models.Noticia;
import com.unichile.app.utils.SessionManager;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SessionManager session = ((MainActivity) requireActivity()).getSession();
        DBHelper db = new DBHelper(requireContext());

        // Student info
        String nombre = session.getNombre();
        binding.tvNombreEstudiante.setText(nombre);
        binding.tvCarrera.setText(session.getCarrera() + " · Semestre " + session.getSemestre());

        // Greeting by time
        int hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY);
        String saludo;
        if (hour < 12) saludo = "Buenos días 🌅";
        else if (hour < 19) saludo = "Buenas tardes ☀️";
        else saludo = "Buenas noches 🌙";
        binding.tvSaludo.setText(saludo);

        // Promedio general
        double prom = db.getPromedioGeneral();
        binding.tvPromedio.setText(String.format("%.1f", prom));

        // Total materias
        List<com.unichile.app.models.Materia> mats = db.getDistinctMaterias();
        binding.tvTotalMaterias.setText(String.valueOf(mats.size()));

        // Proxima clase hoy
        String today = db.getTodayDayName();
        List<Materia> clasesHoy = db.getHorarioByDia(today);
        if (!clasesHoy.isEmpty()) {
            Materia m = clasesHoy.get(0);
            binding.tvProximaMateria.setText(m.getNombre());
            binding.tvProximaInfo.setText(m.getHoraInicio() + " - " + m.getHoraFin()
                    + "  |  " + m.getSala() + "  |  " + m.getProfesor());
            binding.tvProximaInfo.setVisibility(View.VISIBLE);
        } else {
            binding.tvProximaMateria.setText(getString(R.string.sin_clases_hoy));
        }

        // Últimas 2 noticias
        List<Noticia> noticias = db.getAllNoticias();
        int count = Math.min(2, noticias.size());
        for (int i = 0; i < count; i++) {
            Noticia n = noticias.get(i);
            View card = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_noticia, binding.llNoticiasHome, false);
            ((TextView) card.findViewById(R.id.tvTitulo)).setText(n.getTitulo());
            ((TextView) card.findViewById(R.id.tvDescripcion)).setText(n.getDescripcion());
            ((TextView) card.findViewById(R.id.tvFecha)).setText(n.getFecha());
            TextView tvCat = card.findViewById(R.id.tvCategoria);
            tvCat.setText(n.getCategoria());
            tvCat.setBackgroundColor(getCategoriaColor(n.getCategoria()));
            binding.llNoticiasHome.addView(card);
        }

        // Ver todas noticias
        binding.tvVerNoticias.setOnClickListener(v ->
                ((MainActivity) requireActivity()).navegarA(R.id.nav_noticias));
              
        // Long click header = logout
        binding.tvNombreEstudiante.setOnLongClickListener(v -> {
            ((MainActivity) requireActivity()).showLogoutDialog();
            return true;
        });
    }

    private int getCategoriaColor(String cat) {
        if (cat == null) return Color.parseColor("#607D8B");
        switch (cat) {
            case "Académico":      return Color.parseColor("#1565C0");
            case "Eventos":        return Color.parseColor("#2E7D32");
            case "Infraestructura":return Color.parseColor("#E65100");
            case "Becas":          return Color.parseColor("#6A1B9A");
            case "Deportes":       return Color.parseColor("#00695C");
            case "Servicios":      return Color.parseColor("#4527A0");
            default:               return Color.parseColor("#607D8B");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
