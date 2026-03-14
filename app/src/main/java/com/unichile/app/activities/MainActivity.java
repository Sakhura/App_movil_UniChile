package com.unichile.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unichile.app.R;
import com.unichile.app.databinding.ActivityMainBinding;
import com.unichile.app.fragments.HomeFragment;
import com.unichile.app.fragments.HorarioFragment;
import com.unichile.app.fragments.MapaFragment;
import com.unichile.app.fragments.NotasFragment;
import com.unichile.app.fragments.NoticiasFragment;
import com.unichile.app.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new SessionManager(this);

        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        binding.bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();
            if      (id == R.id.nav_inicio)   fragment = new HomeFragment();
            else if (id == R.id.nav_notas)    fragment = new NotasFragment();
            else if (id == R.id.nav_horario)  fragment = new HorarioFragment();
            else if (id == R.id.nav_noticias) fragment = new NoticiasFragment();
            else if (id == R.id.nav_mapa)     fragment = new MapaFragment();
            if (fragment != null) {
                loadFragment(fragment);
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
    }

    public void showLogoutDialog() {
        new AlertDialog.Builder(this)
            .setTitle("Cerrar Sesión")
            .setMessage("¿Estás segura de que deseas cerrar sesión?")
            .setPositiveButton("Sí, salir", (d, w) -> {
                session.logout();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            })
            .setNegativeButton("Cancelar", null)
            .show();
    }

    public SessionManager getSession() {
        return session;
    }
}
