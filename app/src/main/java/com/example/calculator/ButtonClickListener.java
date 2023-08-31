package com.example.calculator;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ButtonClickListener {
    private Context context;
    private TextView expressionEditText;
    private TextView resultTextView;

    public ButtonClickListener(Context context, TextView expressionEditText, TextView resultTextView) {
        this.context = context;
        this.expressionEditText = expressionEditText;
        this.resultTextView = resultTextView;
    }

    public void setupDelButton() {
        // 현재 텍스트를 가져옴
        String currentText = expressionEditText.getText().toString();

        if (currentText.length()>0){
            if(currentText.endsWith(" ")){
                //  del 키로  띄어쓰기 부분도 같이 삭제 하도록
                if (currentText.length()>=3)
                {
                    currentText = currentText.substring(0, currentText.length() - 3);
                }
                else
                {
                    currentText = currentText.substring(0, currentText.length() - 1);
                }
            }
            else{
                currentText = currentText.substring(0, currentText.length() - 1);
            }

            // EditText에 업데이트
            expressionEditText.setText(currentText);
            resultTextView.setText("");
        }
    }

    public void op_buttonClickListener(int buttonId){
        // 버튼의 텍스트를 가져와서 EditText에 추가
        String currentText = expressionEditText.getText().toString();
        String operator = "";

        switch (buttonId) {
            case R.id.btn_div2:
                operator = "1 / (";
                break;
            case R.id.btn_square:
                operator = "^ 2";
                break;
            case R.id.btn_root:
                operator = "\u221A (";
                break;
            // 다른 버튼 ID에 대한 처리 추가
            case R.id.btn_per:
                operator = "%";
                break;
            case R.id.btn_mul:
                operator = "*";
                break;
            case R.id.btn_div:
                operator = "/";
                break;
            case R.id.btn_sub:
                operator = "-";
                break;
            case R.id.btn_add:
                operator = "+";
                break;
            default:
                Log.d("Button Click", "Unrecognized button ID: " + buttonId);
                break;
        }
        expressionEditText.setText(currentText + " " + operator + " ");
    }

    public void buttonClickListener(View view){
        // 버튼의 텍스트를 가져와서 EditText에 추가
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        String currentText = expressionEditText.getText().toString();

        // 'C' 버튼을 누르면 EditText를 초기화
        if (buttonText.equals("C")) {
            expressionEditText.setText("");
            resultTextView.setText("");
        }
        else if (buttonText.equals("(") || buttonText.equals(")")){
            // 그 외 버튼은 현재 텍스트에 추가
            expressionEditText.setText(currentText + " " + buttonText + " ");
        }
        else {
            expressionEditText.setText(currentText + buttonText);
        }
    }
}
