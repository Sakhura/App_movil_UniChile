package com.unichile.app.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.unichile.app.adapters.HorarioAdapter;
import com.unichile.app.database.DBHelper;
import com.unichile.app.databinding.FragmentHorarioBinding;
import com.unichile.app.models.Materia;

import java.util.List;

public class HorarioFragment extends Fragment {

    private FragmentHorarioBinding binding;
    private DBHelper db;
    private HorarioAdapter adapter;
    private Button selectedButton;
    private final String[] DIAS = {"Lunes","Martes","Miércoles","Jueves","Viernes"};
    private final String[] DIAS_SHORT = {"Lun","Mar","Mié","Jue","Vie"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHorarioBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new DBHelper(requireContext());

        adapter = new HorarioAdapter(requireContext(), null);
        binding.rvHorario.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvHorario.setAdapter(adapter);

        // Build day buttons dynamically
        for (int i = 0; i < DIAS.length; i++) {
            final String dia = DIAS[i];
            Button btn = new Button(requireContext());
            btn.setText(DIAS_SHORT[i]);
            btn.setAllCaps(false);
            btn.setTextSize(13f);
            android.widget.LinearLayout.LayoutParams lp =
                new android.widget.LinearLayout.LayoutParams(
                    android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                    android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(8, 0, 8, 0);
            btn.setLayoutParams(lp);
            btn.setPadding(32, 16, 32, 16);
            setButtonStyle(btn, false);
            btn.setOnClickListener(v -> {
                if (selectedButton != null) setButtonStyle(selectedButton, false);
                setButtonStyle(btn, true);
                selectedButton = btn;
                loadDia(dia);
            });
            binding.llDias.addView(btn);
        }

        // Default: today or Lunes
        String today = db.getTodayDayName();
        int defIdx = 0;
        for (int i = 0; i < DIAS.length; i++) {
            if (DIAS[i].equals(today)) { defIdx = i; break; }
        }
        Button firstBtn = (Button) binding.llDias.getChildAt(defIdx);
        if (firstBtn != null) {
            setButtonStyle(firstBtn, true);
            selectedButton = firstBtn;
        }
        loadDia(DIAS[defIdx]);
    }

    private void loadDia(String dia) {
        List<Materia> clases = db.getHorarioByDia(dia);
        if (clases.isEmpty()) {
            binding.tvSinClases.setVisibility(View.VISIBLE);
            binding.rvHorario.setVisibility(View.GONE);
        } else {
            binding.tvSinClases.setVisibility(View.GONE);
            binding.rvHorario.setVisibility(View.VISIBLE);
            adapter.updateData(clases);
        }
    }

    private void setButtonStyle(Button btn, boolean selected) {
        if (selected) {
            btn.setBackgroundColor(Color.parseColor("#1A237E"));
            btn.setTextColor(Color.WHITE);
        } else {
            btn.setBackgroundColor(Color.parseColor("#E8EAF6"));
            btn.setTextColor(Color.parseColor("#1A237E"));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
