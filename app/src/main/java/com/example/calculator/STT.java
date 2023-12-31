package com.example.calculator;

// SpeechRecognitionHelper.java

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.util.Log;
import android.speech.RecognitionListener;

import android.speech.SpeechRecognizer;

import android.os.Bundle;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class STT extends AppCompatActivity {
    private Context context;
    private TextView expressionEditText;

    private SpeechRecognizer mRecognizer;

    public STT(Context context, TextView expressionEditText) {
        this.context = context;
        this.expressionEditText = expressionEditText;
    }

    public void setupMic(Intent intent)
    {
        String currentText = expressionEditText.getText().toString();
        // 여기서 음성 인식을 수행하도록 호출할 수 있음
        mRecognizer=SpeechRecognizer.createSpeechRecognizer(context);
        mRecognizer.setRecognitionListener(listener);
        mRecognizer.startListening(intent);
        Log.d("mic off", "mic off");
    }


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

            for(int i = 0; i < matches.size() ; i++){
                expressionEditText.setText(matches.get(i));
            }
        }

        public void onPartialResults(Bundle partialResults) {}

        public void onEvent(int eventType, Bundle params) {}
    };



    // 필요한 음성 인식 관련 메서드를 추가할 수 있음
}

