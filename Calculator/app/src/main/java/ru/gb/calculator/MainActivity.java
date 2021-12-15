package ru.gb.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "happy MainActivity";
    private static final String CALCULATOR_COMPONENTS_KEY = "CALCULATOR_COMPONENTS";
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

    private static final String ADDITION = "+";
    private static final String SUBTRACTION = "-";
    private static final String DIVISION = "÷";
    private static final String MULTIPLICATION = "×";
    private static final String PERCENT = "%";
    private static final String EQUAL = "=";
    private static final String DECIMAL_SEPARATOR = ",";

    private TextView calculatorIndicator;

    CalculatorComponents calculatorComponents = new CalculatorComponents();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null && savedInstanceState.containsKey(CALCULATOR_COMPONENTS_KEY)) {
            calculatorComponents = savedInstanceState.getParcelable(CALCULATOR_COMPONENTS_KEY);
        }
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");

        Button button1 = findViewById(R.id.button_1);
        Button button2 = findViewById(R.id.button_2);
        Button button3 = findViewById(R.id.button_3);
        Button button4 = findViewById(R.id.button_4);
        Button button5 = findViewById(R.id.button_5);
        Button button6 = findViewById(R.id.button_6);
        Button button7 = findViewById(R.id.button_7);
        Button button8 = findViewById(R.id.button_8);
        Button button9 = findViewById(R.id.button_9);
        Button button0 = findViewById(R.id.button_0);

        Button buttonAddition = findViewById(R.id.button_plus);
        Button buttonSubtraction = findViewById(R.id.button_minus);
        Button buttonDivision = findViewById(R.id.button_division);
        Button buttonMultiplication = findViewById(R.id.button_multiplication);
        Button buttonPercent = findViewById(R.id.button_percent);
        Button buttonEqual = findViewById(R.id.button_equal_sign);
        Button buttonDecimalSeparator = findViewById(R.id.button_decimal_separator);

        Button buttonResetIndicator = findViewById(R.id.button_reset_value);

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

        buttonAddition.setOnClickListener(v -> clickAction(ADDITION));
        buttonSubtraction.setOnClickListener(v -> clickAction(SUBTRACTION));
        buttonDivision.setOnClickListener(v -> clickAction(DIVISION));
        buttonMultiplication.setOnClickListener(v -> clickAction(MULTIPLICATION));
        buttonPercent.setOnClickListener(v -> clickAction(PERCENT));
        buttonEqual.setOnClickListener(v -> clickEqualSigh());
        buttonDecimalSeparator.setOnClickListener(v -> clickDecimalSeparator(DECIMAL_SEPARATOR));

        buttonResetIndicator.setOnClickListener(v -> clickReset());
    }

    private void clickReset() {
        calculatorComponents.reset();
        calculatorIndicator.setText(calculatorComponents.getCalculatorIndicatorText());
    }

    private void clickDigit(String value) {
        if (calculatorComponents.getAction() == null) {
            String value1 = calculatorComponents.getValue1();
            if (value1 == null) {
                calculatorComponents.setValue1(value);
            } else {
                calculatorComponents.setValue1(calculatorComponents.getValue1() + value);
            }
        } else {
            String value2 = calculatorComponents.getValue2();
            if (value2 == null) {
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