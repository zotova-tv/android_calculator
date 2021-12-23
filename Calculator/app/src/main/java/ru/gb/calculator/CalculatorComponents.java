package ru.gb.calculator;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class CalculatorComponents implements Parcelable {

    private static final String TAG = "happy ";
    private static final String EMPTY_STRING = "";

    protected static final String ADDITION = "+";
    protected static final String SUBTRACTION = "-";
    protected static final String DIVISION = "÷";
    protected static final String MULTIPLICATION = "×";
    protected static final String PERCENT = "%";

    private String value1;
    private String value2;
    private String action;

    public CalculatorComponents() {

    }

    protected CalculatorComponents(Parcel in) {
        value1 = in.readString();
        value2 = in.readString();
        action = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value1);
        dest.writeString(value2);
        dest.writeString(action);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CalculatorComponents> CREATOR = new Creator<CalculatorComponents>() {
        @Override
        public CalculatorComponents createFromParcel(Parcel in) {
            return new CalculatorComponents(in);
        }

        @Override
        public CalculatorComponents[] newArray(int size) {
            return new CalculatorComponents[size];
        }
    };

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCalculatorIndicatorText() {
        Log.d(TAG, "getCalculatorIndicatorText() called");
        if (value1 == null) {
            return EMPTY_STRING;
        } else if (action == null) {
            return value1;
        } else if (value2 == null) {
            return value1 + action;
        }
        return value1 + action + value2;
    }

    public void reset() {
        this.value1 = null;
        this.value2 = null;
        this.action = null;
    }

    public void calculate() {
        // считаем результат, записываем в value 1. value 2 и action сбрасываем
        this.value1 = this.value1.replace(",", ".");
        this.value2 = this.value2.replace(",", ".");
        Double operand1 = Double.valueOf(this.value1);
        Double operand2 = Double.valueOf(this.value2);
        Double result;
        switch (this.action) {
            case ADDITION:
                result = operand1 + operand2;
                break;
            case SUBTRACTION:
                result = operand1 - operand2;
                break;
            case MULTIPLICATION:
                result = operand1 * operand2;
                break;
            case DIVISION:
                result = operand1 / operand2;
                break;
            case PERCENT:
                result = operand1 / 100 * operand2;
                break;
            default:
                result = 0.0;
        }
        this.value1 = String.valueOf(result);
        this.value2 = null;
        this.action = null;
    }
}
