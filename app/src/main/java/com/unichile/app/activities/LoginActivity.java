package com.unichile.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.unichile.app.database.DBHelper;
import com.unichile.app.databinding.ActivityLoginBinding;
import com.unichile.app.models.Estudiante;
import com.unichile.app.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private DBHelper db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DBHelper(this);
        session = new SessionManager(this);

        binding.btnLogin.setOnClickListener(v -> attemptLogin());

        // Allow pressing Enter on password to login
        binding.etPassword.setOnEditorActionListener((v, actionId, event) -> {
            attemptLogin();
            return true;
        });
    }

    private void attemptLogin() {
        String email = binding.etEmail.getText() != null
            ? binding.etEmail.getText().toString().trim() : "";
        String password = binding.etPassword.getText() != null
            ? binding.etPassword.getText().toString().trim() : "";

        binding.tilEmail.setError(null);
        binding.tilPassword.setError(null);

        if (TextUtils.isEmpty(email)) {
            binding.tilEmail.setError("Ingresa tu correo");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            binding.tilPassword.setError("Ingresa tu contraseña");
            return;
        }

        binding.btnLogin.setEnabled(false);
        binding.btnLogin.setText("Ingresando...");

        Estudiante est = db.login(email, password);

        if (est != null) {
            session.createSession(est.getId(), est.getNombre(), est.getApellido(),
                    est.getEmail(), est.getCarrera(), est.getSemestre());
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, getString(com.unichile.app.R.string.error_credenciales),
                    Toast.LENGTH_LONG).show();
            binding.btnLogin.setEnabled(true);
            binding.btnLogin.setText(getString(com.unichile.app.R.string.btn_login));
        }
    }
}
