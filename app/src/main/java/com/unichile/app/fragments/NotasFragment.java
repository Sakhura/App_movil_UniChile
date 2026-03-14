package com.unichile.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.unichile.app.adapters.NotasAdapter;
import com.unichile.app.database.DBHelper;
import com.unichile.app.databinding.FragmentNotasBinding;
import com.unichile.app.models.Materia;

import java.util.List;

public class NotasFragment extends Fragment {

    private FragmentNotasBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNotasBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DBHelper db = new DBHelper(requireContext());
        List<Materia> materias = db.getDistinctMaterias();

        NotasAdapter adapter = new NotasAdapter(requireContext(), materias, db);
        binding.rvNotas.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvNotas.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
