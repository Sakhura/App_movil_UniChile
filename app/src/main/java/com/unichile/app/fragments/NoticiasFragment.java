package com.unichile.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.unichile.app.adapters.NoticiasAdapter;
import com.unichile.app.database.DBHelper;
import com.unichile.app.databinding.FragmentNoticiasBinding;
import com.unichile.app.models.Noticia;

import java.util.List;

public class NoticiasFragment extends Fragment {

    private FragmentNoticiasBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNoticiasBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DBHelper db = new DBHelper(requireContext());
        List<Noticia> noticias = db.getAllNoticias();
        NoticiasAdapter adapter = new NoticiasAdapter(requireContext(), noticias);
        binding.rvNoticias.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvNoticias.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
