package com.example.biodata;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.biodata.databinding.ActivityRegisterBinding;
import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(this);

        binding.btnRegister.setOnClickListener(v -> {
            String username = binding.etRegUsername.getText().toString().trim();
            String password = binding.etRegPassword.getText().toString().trim();
            String confirmPassword = binding.etRegConfirmPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Snackbar.make(binding.getRoot(), "Harap isi semua field", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                binding.tilRegPassword.setError("Minimal 6 karakter");
                return;
            } else {
                binding.tilRegPassword.setError(null);
            }

            if (!password.equals(confirmPassword)) {
                binding.tilRegConfirmPassword.setError("Password tidak cocok");
                return;
            } else {
                binding.tilRegConfirmPassword.setError(null);
            }

            preferenceManager.saveUsername(username);
            preferenceManager.savePassword(password);

            Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
            finish();
        });

        binding.tvBackToLogin.setOnClickListener(v -> finish());
    }
}
