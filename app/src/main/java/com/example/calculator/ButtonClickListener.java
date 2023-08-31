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
        // ���� �ؽ�Ʈ�� ������
        String currentText = expressionEditText.getText().toString();

        if (currentText.length()>0){
            if(currentText.endsWith(" ")){
                //  del Ű��  ���� �κе� ���� ���� �ϵ���
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

            // EditText�� ������Ʈ
            expressionEditText.setText(currentText);
            resultTextView.setText("");
        }
    }

    public void op_buttonClickListener(int buttonId){
        // ��ư�� �ؽ�Ʈ�� �����ͼ� EditText�� �߰�
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
            // �ٸ� ��ư ID�� ���� ó�� �߰�
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
        // ��ư�� �ؽ�Ʈ�� �����ͼ� EditText�� �߰�
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        String currentText = expressionEditText.getText().toString();

        // 'C' ��ư�� ������ EditText�� �ʱ�ȭ
        if (buttonText.equals("C")) {
            expressionEditText.setText("");
            resultTextView.setText("");
        }
        else if (buttonText.equals("(") || buttonText.equals(")")){
            // �� �� ��ư�� ���� �ؽ�Ʈ�� �߰�
            expressionEditText.setText(currentText + " " + buttonText + " ");
        }
        else {
            expressionEditText.setText(currentText + buttonText);
        }
    }
}
