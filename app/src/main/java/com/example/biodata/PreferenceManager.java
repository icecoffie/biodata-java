package com.example.biodata;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_NAME = "BioAppPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    
    // Biodata keys
    private static final String KEY_FULL_NAME = "fullName";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_BIRTH_DATE = "birthDate";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_HOBBIES = "hobbies";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void saveUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }
    
    public void savePassword(String password) {
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }
    
    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, "");
    }

    public void saveBiodata(String fullName, String email, String phone, String address, String birthDate, String gender, String hobbies) {
        editor.putString(KEY_FULL_NAME, fullName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_BIRTH_DATE, birthDate);
        editor.putString(KEY_GENDER, gender);
        editor.putString(KEY_HOBBIES, hobbies);
        editor.apply();
    }

    public String getFullName() { return sharedPreferences.getString(KEY_FULL_NAME, ""); }
    public String getEmail() { return sharedPreferences.getString(KEY_EMAIL, ""); }
    public String getPhone() { return sharedPreferences.getString(KEY_PHONE, ""); }
    public String getAddress() { return sharedPreferences.getString(KEY_ADDRESS, ""); }
    public String getBirthDate() { return sharedPreferences.getString(KEY_BIRTH_DATE, ""); }
    public String getGender() { return sharedPreferences.getString(KEY_GENDER, ""); }
    public String getHobbies() { return sharedPreferences.getString(KEY_HOBBIES, ""); }

    public void logout() {
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.apply();
    }

    public void clearAll() {
        editor.clear();
        editor.apply();
    }
}
