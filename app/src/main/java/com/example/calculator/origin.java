package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Log;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;


public class origin extends AppCompatActivity {

    static {
        System.loadLibrary("calculator");
    }

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private ActionBarDrawerToggle drawerToggle;
    private TextView resultTextView;
    private TextView expressionEditText; // 계산식을 표시할 EditText

    public native String resultFromCalculator(String currentText);
    public native String stringFromJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.layout_drawer);
        navigationView = findViewById(R.id.nav);
        navigationView.setItemIconTintList(null);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        expressionEditText = findViewById(R.id.txt_expression); // 계산식을 표시할 EditText를 가져옴
        resultTextView = findViewById(R.id.txt_result);

        // 콘텐츠 영역
        // 숫자 및 연산자 버튼들을 가져와서 클릭 리스너를 설정
        Button btn0 = findViewById(R.id.btn_0);
        Button btn1 = findViewById(R.id.btn_1);
        Button btn2 = findViewById(R.id.btn_2);
        Button btn3 = findViewById(R.id.btn_3);
        Button btn4 = findViewById(R.id.btn_4);
        Button btn5 = findViewById(R.id.btn_5);
        Button btn6 = findViewById(R.id.btn_6);
        Button btn7 = findViewById(R.id.btn_7);
        Button btn8 = findViewById(R.id.btn_8);
        Button btn9 = findViewById(R.id.btn_9);

        Button btnC = findViewById(R.id.btn_C);
        Button btnOpen = findViewById(R.id.btn_open);
        Button btnClose = findViewById(R.id.btn_close);
        ImageButton btnDel = findViewById(R.id.btn_del);

        Button btnAdd = findViewById(R.id.btn_add);
        Button btnSub = findViewById(R.id.btn_sub);
        Button btnMul = findViewById(R.id.btn_mul);
        Button btnDiv = findViewById(R.id.btn_div);
        Button btnPer = findViewById(R.id.btn_per);
        Button btnDot = findViewById(R.id.btn_dot);

        Button btnDiv2 = findViewById(R.id.btn_div2);
        Button btnSquare = findViewById(R.id.btn_square);
        Button btnRoot = findViewById(R.id.btn_root);

        Button btnResult = findViewById(R.id.btn_result);
        ImageButton btnMic = findViewById(R.id.btn_mic);

        // mic 버튼 클릭 리스너 설정
        btnMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("mic on","mic on");


                String currentText = expressionEditText.getText().toString();
                expressionEditText.setText(stringFromJNI());
                Log.d("mic off","mic off");


            }
        });

        // 숫자 및 else 버튼 클릭 리스너 설정
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼의 텍스트를 가져와서 EditText에 추가
                Button button = (Button) view;
                String buttonText = button.getText().toString();
                String currentText = expressionEditText.getText().toString();

                // 'C' 버튼을 누르면 EditText를 초기화
                if (buttonText.equals("C")) {
                    expressionEditText.setText("");
                    resultTextView.setText("");
                }
                else if (buttonText.equals("del")){
                    // 띄어쓰기가 없을 때까지 문자열 끝에서 한 글자씩 삭제
                    while (currentText.length() > 0 && !currentText.endsWith(" ")) {
                        currentText = currentText.substring(0, currentText.length() - 1);
                    }
                    // EditText에 업데이트
                    expressionEditText.setText(currentText);
                }
                else if (buttonText.equals("(") || buttonText.equals(")")){
                    // 그 외 버튼은 현재 텍스트에 추가
                    expressionEditText.setText(currentText + " " + buttonText + " ");
                }
                else {
                    expressionEditText.setText(currentText + buttonText);
                }
            }
        };
        // del 버튼 클릭 리스너 설정
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재 텍스트를 가져옴
                String currentText = expressionEditText.getText().toString();

                if (currentText.length()>0){
                    if(currentText.endsWith(" ")){
                        currentText = currentText.substring(0, currentText.length() - 3);
                    }
                    else{
                        currentText = currentText.substring(0, currentText.length() - 1);
                    }
                }

                // EditText에 업데이트
                expressionEditText.setText(currentText);
                resultTextView.setText("");
            }
        });


        // 연산자 버튼 클릭 리스너 설정
        View.OnClickListener op_buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼의 텍스트를 가져와서 EditText에 추가
                Button button = (Button) view;
                String buttonText = button.getText().toString();
                String currentText = expressionEditText.getText().toString();

                // '1/x' 버튼
                if (buttonText.equals("?\uD835\uDCCD")) {
                    expressionEditText.setText(currentText + " 1 / ( ");
                }
                // 'x²' 버튼
                else if (buttonText.equals("\uD835\uDCCD²")){
                    expressionEditText.setText(currentText + " ^ 2 ");
                }
                // '√x' 버튼
                else if (buttonText.equals("√\uD835\uDCCD")){
                    expressionEditText.setText(currentText + " √( ");
                }
                // 그 외 기본 operator
                else {
                    if (buttonText.equals("%")){
                        buttonText = "%";
                    }
                    else if (buttonText.equals("×")){
                        buttonText = "*";
                    }
                    else if (buttonText.equals("÷")){
                        buttonText = "/";
                    }
                    else if (buttonText.equals("-")){
                        buttonText = "-";
                    }
                    else if (buttonText.equals("+")){
                        buttonText = "+";
                    }
                    expressionEditText.setText(currentText + " " + buttonText + " ");
                }
            }
        };

        // 결과 버튼 클릭 리스너 설정
        View.OnClickListener result_buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText = expressionEditText.getText().toString();
                Log.d("expression", currentText);
                if(currentText.length() > 0)
                {
                    String result = resultFromCalculator(currentText);
                    Log.d("result", result);
                    resultTextView.setText(result);
                }
            }
        };



        // 모든 숫자 및 연산자 버튼에 클릭 리스너를 설정

        btn0.setOnClickListener(buttonClickListener);
        btn1.setOnClickListener(buttonClickListener);
        btn2.setOnClickListener(buttonClickListener);
        btn3.setOnClickListener(buttonClickListener);
        btn4.setOnClickListener(buttonClickListener);
        btn5.setOnClickListener(buttonClickListener);
        btn6.setOnClickListener(buttonClickListener);
        btn7.setOnClickListener(buttonClickListener);
        btn8.setOnClickListener(buttonClickListener);
        btn9.setOnClickListener(buttonClickListener);

        btnC.setOnClickListener(buttonClickListener);
        btnOpen.setOnClickListener(buttonClickListener);
        btnClose.setOnClickListener(buttonClickListener);
        btnDot.setOnClickListener(buttonClickListener);

        btnAdd.setOnClickListener(op_buttonClickListener);
        btnSub.setOnClickListener(op_buttonClickListener);
        btnMul.setOnClickListener(op_buttonClickListener);
        btnDiv.setOnClickListener(op_buttonClickListener);
        btnPer.setOnClickListener(op_buttonClickListener);

        btnDiv2.setOnClickListener(op_buttonClickListener);
        btnSquare.setOnClickListener(op_buttonClickListener);
        btnRoot.setOnClickListener(op_buttonClickListener);

        btnResult.setOnClickListener(result_buttonClickListener);
    }

}
