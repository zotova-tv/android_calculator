package ru.gb.calculator;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "happy MainActivity";
    private static final String CALCULATOR_COMPONENTS_KEY = "CALCULATOR_COMPONENTS";
    protected static final String CALCULATOR_SETTINGS_KEY = "CALCULATOR_SETTINGS";
    protected static final String DARK_THEME_IS_CHECKED_KEY = "DARK_THEME_IS_CHECKED";
    private static final String VALUE_1 = "1";
    private static final String VALUE_2 = "2";
    private static final String VALUE_3 = "3";
    private static final String VALUE_4 = "4";
    private static final String VALUE_5 = "5";
    private static final String VALUE_6 = "6";
    private static final String VALUE_7 = "7";
    private static final String VALUE_8 = "8";
    private static final String VALUE_9 = "9";
    private static final String VALUE_0 = "0";

    private static final String DECIMAL_SEPARATOR = ",";

    private TextView calculatorIndicator;

    private CalculatorComponents calculatorComponents = new CalculatorComponents();
    private SharedPreferences prefs;
    private ActivityResultLauncher<Intent> calculatorSettingsLauncher;
    Boolean darkThemeIsChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences(CALCULATOR_SETTINGS_KEY, MODE_PRIVATE);
        darkThemeIsChecked = prefs.getBoolean(DARK_THEME_IS_CHECKED_KEY, false);

        if(darkThemeIsChecked){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(CALCULATOR_COMPONENTS_KEY)) {
            calculatorComponents = savedInstanceState.getParcelable(CALCULATOR_COMPONENTS_KEY);
        }
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");

        MaterialButton button1 = findViewById(R.id.button_1);
        MaterialButton button2 = findViewById(R.id.button_2);
        MaterialButton button3 = findViewById(R.id.button_3);
        MaterialButton button4 = findViewById(R.id.button_4);
        MaterialButton button5 = findViewById(R.id.button_5);
        MaterialButton button6 = findViewById(R.id.button_6);
        MaterialButton button7 = findViewById(R.id.button_7);
        MaterialButton button8 = findViewById(R.id.button_8);
        MaterialButton button9 = findViewById(R.id.button_9);
        MaterialButton button0 = findViewById(R.id.button_0);

        MaterialButton buttonAddition = findViewById(R.id.button_plus);
        MaterialButton buttonSubtraction = findViewById(R.id.button_minus);
        MaterialButton buttonDivision = findViewById(R.id.button_division);
        MaterialButton buttonMultiplication = findViewById(R.id.button_multiplication);
        MaterialButton buttonPercent = findViewById(R.id.button_percent);
        MaterialButton buttonEqual = findViewById(R.id.button_equal_sign);
        MaterialButton buttonDecimalSeparator = findViewById(R.id.button_decimal_separator);
        MaterialButton buttonChangeSign = findViewById(R.id.button_change_sign);

        MaterialButton buttonResetIndicator = findViewById(R.id.button_reset_value);

        MaterialButton buttonSettings = findViewById(R.id.settings_button);

        calculatorIndicator = findViewById(R.id.calculatorIndicator);

        calculatorIndicator.setText(calculatorComponents.getCalculatorIndicatorText());
        button1.setOnClickListener(v -> clickDigit(VALUE_1));
        button2.setOnClickListener(v -> clickDigit(VALUE_2));
        button3.setOnClickListener(v -> clickDigit(VALUE_3));
        button4.setOnClickListener(v -> clickDigit(VALUE_4));
        button5.setOnClickListener(v -> clickDigit(VALUE_5));
        button6.setOnClickListener(v -> clickDigit(VALUE_6));
        button7.setOnClickListener(v -> clickDigit(VALUE_7));
        button8.setOnClickListener(v -> clickDigit(VALUE_8));
        button9.setOnClickListener(v -> clickDigit(VALUE_9));
        button0.setOnClickListener(v -> clickDigit(VALUE_0));

        buttonAddition.setOnClickListener(v -> clickAction(CalculatorComponents.ADDITION));
        buttonSubtraction.setOnClickListener(v -> clickAction(CalculatorComponents.SUBTRACTION));
        buttonDivision.setOnClickListener(v -> clickAction(CalculatorComponents.DIVISION));
        buttonMultiplication.setOnClickListener(v -> clickAction(CalculatorComponents.MULTIPLICATION));
        buttonPercent.setOnClickListener(v -> clickAction(CalculatorComponents.PERCENT));
        buttonEqual.setOnClickListener(v -> clickEqualSigh());
        buttonDecimalSeparator.setOnClickListener(v -> clickDecimalSeparator(DECIMAL_SEPARATOR));
        buttonChangeSign.setOnClickListener(v -> clickChangeSign());

        buttonResetIndicator.setOnClickListener(v -> clickReset());

        buttonSettings.setOnClickListener(v -> clickButtonSettings());

        calculatorSettingsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent data = result.getData();
                    darkThemeIsChecked = data.getBooleanExtra(DARK_THEME_IS_CHECKED_KEY, darkThemeIsChecked);
                    prefs.edit().putBoolean(DARK_THEME_IS_CHECKED_KEY, darkThemeIsChecked).apply();
                    if (darkThemeIsChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }

                }
        );
    }

    private void clickButtonSettings() {
        Intent calculatorSettingsActivityIntent = new Intent(this, CalculatorSettingsActivity.class);
        calculatorSettingsLauncher.launch(calculatorSettingsActivityIntent);
        // startActivity(calculatorSettingsActivityIntent);
    }

    private void clickChangeSign() {
        if(calculatorComponents.getValue1() == null){
            return;
        }else if(calculatorComponents.getValue1() != null && calculatorComponents.getValue2() == null){
            if(calculatorComponents.getValue1().startsWith("-")){
                String value1 = calculatorComponents.getValue1();
                calculatorComponents.setValue1(value1.substring(1, value1.length()));
            }else{
                calculatorComponents.setValue1("-" + calculatorComponents.getValue1());
            }
        }else if(calculatorComponents.getValue2() != null){
            if(calculatorComponents.getValue2().startsWith("-")){
                String value1 = calculatorComponents.getValue2();
                calculatorComponents.setValue1(value1.substring(1, value1.length()));
            }else{
                calculatorComponents.setValue1(CalculatorComponents.SUBTRACTION + calculatorComponents.getValue2());
            }
        }
        calculatorIndicator.setText(calculatorComponents.getCalculatorIndicatorText());
    }

    private void clickReset() {
        calculatorComponents.reset();
        calculatorIndicator.setText(calculatorComponents.getCalculatorIndicatorText());
    }

    private void clickDigit(String value) {
        if (calculatorComponents.getAction() == null) {
            if (calculatorComponents.getValue1() == null) {
                calculatorComponents.setValue1(value);
            } else {
                calculatorComponents.setValue1(calculatorComponents.getValue1() + value);
            }
        } else {
            if (calculatorComponents.getValue2() == null) {
                calculatorComponents.setValue2(value);
            } else {
                calculatorComponents.setValue2(calculatorComponents.getValue2() + value);
            }
        }
        calculatorIndicator.setText(calculatorComponents.getCalculatorIndicatorText());
    }

    private void clickAction(String value) {

        // обрабатываю на знак операции только если уже заполнено первое число
        // если уже заполнен знак операции, то он меняется на новый
        if (calculatorComponents.getValue1() != null && calculatorComponents.getAction() == null || calculatorComponents.getValue2() == null) {
            calculatorComponents.setAction(value);
        }
        // пока что подразумеваю, что может быть только одна операция с числами
        calculatorIndicator.setText(calculatorComponents.getCalculatorIndicatorText());
    }

    private void clickEqualSigh() {
        calculatorComponents.calculate();
        calculatorIndicator.setText(calculatorComponents.getCalculatorIndicatorText());
    }

    private void clickDecimalSeparator(String value) {
        Log.d(TAG, "clickDecimalSeparator() called with: value = [" + value + "]");
        if (calculatorComponents.getValue1() != null && calculatorComponents.getAction() == null) {
            String value1 = calculatorComponents.getValue1();
            if(!value1.contains(value)) {
                calculatorComponents.setValue1(value1 + value);
                calculatorIndicator.setText(calculatorComponents.getCalculatorIndicatorText());
            }
        } else if (calculatorComponents.getValue2() != null) {
            String value2 = calculatorComponents.getValue2();
            if(!value2.contains(value)) {
                calculatorComponents.setValue2(value2 + value);
                calculatorIndicator.setText(calculatorComponents.getCalculatorIndicatorText());
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CALCULATOR_COMPONENTS_KEY, calculatorComponents);
    }

}