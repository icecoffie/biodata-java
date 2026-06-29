package com.example.biodata;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.biodata.databinding.ActivityLoginBinding;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(this);

        binding.btnLogin.setOnClickListener(v -> {
            String username = binding.etUsername.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Snackbar.make(binding.getRoot(), "Harap isi semua field", Snackbar.LENGTH_SHORT).show();
                return;
            }

            // Simple validation logic
            String savedUser = preferenceManager.getUsername();
            String savedPass = preferenceManager.getPassword();

            if ((username.equals("admin") && password.equals("admin123")) || 
                (username.equals(savedUser) && password.equals(savedPass))) {
                
                if (binding.cbRememberMe.isChecked()) {
                    preferenceManager.setLogin(true);
                }
                
                // Even if not "Remember Me", we might want to store username for greeting
                if (savedUser.isEmpty()) {
                    preferenceManager.saveUsername(username);
                }

                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, BiodataActivity.class));
                finish();
            } else {
                Snackbar.make(binding.getRoot(), "Username atau Password salah", Snackbar.LENGTH_SHORT).show();
            }
        });

        binding.tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }
}
