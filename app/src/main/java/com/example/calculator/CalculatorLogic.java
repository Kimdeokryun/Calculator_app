package com.example.calculator;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorLogic {

    static {
        System.loadLibrary("calculator");
    }

    private Context context;
    private TextView expressionEditText;
    private TextView resultTextView;

    public native String resultFromCalculator(String currentText);
    public native String stringFromJNI();

    public CalculatorLogic(Context context, TextView expressionEditText, TextView resultTextView) {
        this.context = context;
        this.expressionEditText = expressionEditText;
        this.resultTextView = resultTextView;
    }

    public String calculate(){
        String currentText = expressionEditText.getText().toString();
        String buttonText = "";
        Log.d("expression", currentText);
        if(currentText.length() > 0)
        {
            String result = resultFromCalculator(currentText);
            Log.d("result", result);
            resultTextView.setText(result);
            buttonText = currentText+"\n"+result;
        }
        return buttonText;
    }

}