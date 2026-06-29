package com.example.biodata;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.biodata.databinding.ActivityBiodataBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class BiodataActivity extends AppCompatActivity {

    private ActivityBiodataBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBiodataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(this);

        initUI();
        loadData();

        binding.etBirthDate.setOnClickListener(v -> showDatePicker());
        binding.btnSave.setOnClickListener(v -> saveData());
        binding.btnLogout.setOnClickListener(v -> showLogoutDialog());
    }

    private void initUI() {
        String username = preferenceManager.getUsername();
        binding.tvGreeting.setText(getString(R.string.greeting_placeholder, (username.isEmpty() ? "User" : username)));
    }

    private void loadData() {
        String name = preferenceManager.getFullName();
        String email = preferenceManager.getEmail();
        String phone = preferenceManager.getPhone();
        String address = preferenceManager.getAddress();
        String birthDate = preferenceManager.getBirthDate();
        String gender = preferenceManager.getGender();
        String hobbies = preferenceManager.getHobbies();

        binding.etFullName.setText(name);
        binding.etEmail.setText(email);
        binding.etPhone.setText(phone);
        binding.etAddress.setText(address);
        binding.etBirthDate.setText(birthDate);

        if (gender.equals("Laki-laki")) {
            binding.rbMale.setChecked(true);
        } else if (gender.equals("Perempuan")) {
            binding.rbFemale.setChecked(true);
        }

        // Update preview
        updatePreview(name, email);

        // Load hobbies
        if (!hobbies.isEmpty()) {
            String[] hobbyList = hobbies.split(", ");
            for (int i = 0; i < binding.cgHobbies.getChildCount(); i++) {
                Chip chip = (Chip) binding.cgHobbies.getChildAt(i);
                for (String h : hobbyList) {
                    if (chip.getText().toString().equals(h)) {
                        chip.setChecked(true);
                    }
                }
            }
        }
    }

    private void updatePreview(String name, String email) {
        binding.tvPreviewName.setText(name.isEmpty() ? getString(R.string.not_set) : name);
        binding.tvPreviewEmail.setText(email.isEmpty() ? getString(R.string.example_email) : email);
        
        if (!name.isEmpty()) {
            binding.tvAvatarInitials.setText(name.substring(0, 1).toUpperCase());
        } else {
            binding.tvAvatarInitials.setText("?");
        }
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                    binding.etBirthDate.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void saveData() {
        String name = binding.etFullName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String phone = binding.etPhone.getText().toString().trim();
        String address = binding.etAddress.getText().toString().trim();
        String birthDate = binding.etBirthDate.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || birthDate.isEmpty()) {
            Snackbar.make(binding.getRoot(), "Harap isi semua field", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError("Email tidak valid");
            return;
        }

        String gender = "";
        if (binding.rbMale.isChecked()) gender = "Laki-laki";
        else if (binding.rbFemale.isChecked()) gender = "Perempuan";

        StringBuilder hobbies = new StringBuilder();
        for (int i = 0; i < binding.cgHobbies.getChildCount(); i++) {
            Chip chip = (Chip) binding.cgHobbies.getChildAt(i);
            if (chip.isChecked()) {
                if (hobbies.length() > 0) hobbies.append(", ");
                hobbies.append(chip.getText().toString());
            }
        }

        preferenceManager.saveBiodata(name, email, phone, address, birthDate, gender, hobbies.toString());
        updatePreview(name, email);
        
        Snackbar.make(binding.getRoot(), "Biodata berhasil disimpan", Snackbar.LENGTH_SHORT).show();
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    preferenceManager.logout();
                    startActivity(new Intent(BiodataActivity.this, LoginActivity.class));
                    finish();
                })
                .setNegativeButton("Tidak", null)
                .show();
    }
}
