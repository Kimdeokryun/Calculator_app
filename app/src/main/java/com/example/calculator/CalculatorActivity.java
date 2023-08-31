package com.example.calculator;

import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.Manifest;
import android.content.Intent;
import android.speech.SpeechRecognizer;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class CalculatorActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private ActionBarDrawerToggle drawerToggle;
    private TextView resultTextView;
    private TextView expressionEditText;

    Intent intent;
    private SpeechRecognizer mRecognizer;
    final int PERMISSION = 1;
    private Button btnResult;

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

        btnResult = findViewById(R.id.btn_result);
        ImageButton btnMic = findViewById(R.id.btn_mic);

        ButtonClickListener BCL = new ButtonClickListener(this, expressionEditText, resultTextView);
        CalculatorLogic CL = new CalculatorLogic(this, expressionEditText, resultTextView);

        if ( Build.VERSION.SDK_INT >= 23 ){
            // 퍼미션 체크
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO},PERMISSION);
        }

        intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");


        // mic 버튼 클릭 리스너 설정
        btnMic.setOnClickListener(view ->  {
            Log.d("mic on", "mic on");

            mRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(intent);


            //stt.setupMic(intent);
        });

        // del 버튼 클릭 리스너 설정
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BCL.setupDelButton();
            }
        });

        // = (result) 버튼 클릭 리스너 설정
        btnResult.setOnClickListener(view ->  {
            String buttonText = CL.calculate();
            if (!buttonText.equals("")){

                // calculation history  drawer 부분에 버튼형 결과 히스토리 생성
                Button button = new Button(this);
                button.setText(buttonText);

                // 히스토리 버튼 클릭 리스터 생성
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 버튼을 클릭했을 때 실행할 작업을 여기에 추가합니다.
                        String[] parts = buttonText.split("\n");
                        String part1 = parts[0];    // 계산식
                        String part2 = parts[1];    // 결과값
                        expressionEditText.setText(part1);
                        resultTextView.setText(part2);
                        // drawer 닫기
                        drawerLayout.close();
                    }
                });
                // 생성한 Button을 네비게이션 드로어 레이아웃에 추가합니다.
                LinearLayout drawerButtonfield = findViewById(R.id.drawer_buttonfield); // 네비게이션 드로어의 레이아웃을 가져옵니다.
                drawerButtonfield.addView(button); // Button을 레이아웃에 추가합니다.
            }
        });

        // 숫자 및 else 버튼 클릭 리스너 설정
        // 각 숫자 및 else 버튼에 클릭 리스너 설정
        Button[] numberButtons = {
                btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnC, btnOpen, btnClose, btnDot
        };

        View.OnClickListener commonButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BCL.buttonClickListener(view);
            }
        };

        for (Button button : numberButtons) {
            button.setOnClickListener(commonButtonClickListener);
        }

        // 연산자 버튼 클릭 리스너 설정
        // 각 연산자 버튼에 클릭 리스너 설정
        Button[] op_Buttons = {
                btnAdd, btnSub, btnMul, btnDiv, btnPer, btnDiv2, btnSquare, btnRoot
        };

        View.OnClickListener op_ButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                BCL.op_buttonClickListener(button.getId());

            }
        };

        for (Button button : op_Buttons) {
            button.setOnClickListener(op_ButtonClickListener);
        }
    }

    // 음성인식 api 부분
    public RecognitionListener listener = new RecognitionListener() {
        public void onReadyForSpeech(Bundle params) {
            Toast.makeText(getApplicationContext(),"음성인식을 시작합니다.",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {}

        @Override
        public void onRmsChanged(float rmsdB) {}

        @Override
        public void onBufferReceived(byte[] buffer) {}

        @Override
        public void onEndOfSpeech() {}

        @Override
        public void onError(int error) {
            String message;

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류임";
                    break;
            }

            Toast.makeText(getApplicationContext(), "에러가 발생하였습니다. : " + message,Toast.LENGTH_SHORT).show();
        }

        public void onResults(Bundle results) {
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줍니다.
            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            // 계산식 및 결과 값 초기화 후 다시 입력
            expressionEditText.setText("");
            resultTextView.setText("");
            for(int i = 0; i < matches.size() ; i++){
                expressionEditText.setText(matches.get(i));
            }
            Log.d("mic off", "mic off");

            // 결과 값 버튼 강제 클릭
            btnResult.performClick();
        }
        public void onPartialResults(Bundle partialResults) {}

        public void onEvent(int eventType, Bundle params) {}
    };
}
