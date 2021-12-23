package ru.gb.calculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class CalculatorSettingsActivity extends AppCompatActivity {

    private CheckBox checkboxDarkTheme;
    private MaterialButton buttonSaveSettings;
    private Boolean checkboxDarkThemeIsChecked;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_settings);

        checkboxDarkTheme = findViewById(R.id.dark_theme_checkbox);
        buttonSaveSettings = findViewById(R.id.save_settings_button);

        prefs = getSharedPreferences(MainActivity.CALCULATOR_SETTINGS_KEY, MODE_PRIVATE);
        checkboxDarkThemeIsChecked = prefs.getBoolean(MainActivity.DARK_THEME_IS_CHECKED_KEY, false);
        checkboxDarkTheme.setChecked(checkboxDarkThemeIsChecked);

        checkboxDarkTheme.setOnClickListener(v -> checkDarkTheme(v));
        buttonSaveSettings.setOnClickListener(v -> clickSaveSettings());

    }

    private void clickSaveSettings() {
        prefs.edit().putBoolean(MainActivity.DARK_THEME_IS_CHECKED_KEY, checkboxDarkThemeIsChecked).apply();
        Intent intent = new Intent();
        intent.putExtra(MainActivity.DARK_THEME_IS_CHECKED_KEY, checkboxDarkThemeIsChecked);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void checkDarkTheme(View v) {
        checkboxDarkThemeIsChecked = ((CheckBox) v).isChecked();
    }
}
