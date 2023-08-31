package com.example.calculator;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorLogic {

    // c++ ndk library 설정
    static {
        System.loadLibrary("calculator");
    }

    private Context context;
    private TextView expressionEditText;
    private TextView resultTextView;

    // c++ ndk 함수
    public native String resultFromCalculator(String currentText);
    public native String stringFromJNI();

    public CalculatorLogic(Context context, TextView expressionEditText, TextView resultTextView) {
        this.context = context;
        this.expressionEditText = expressionEditText;
        this.resultTextView = resultTextView;
    }

    // c++ 계산 및 textview 세팅
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