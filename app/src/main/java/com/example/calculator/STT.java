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
        // ���⼭ ���� �ν��� �����ϵ��� ȣ���� �� ����
        mRecognizer=SpeechRecognizer.createSpeechRecognizer(context);
        mRecognizer.setRecognitionListener(listener);
        mRecognizer.startListening(intent);
        Log.d("mic off", "mic off");
    }


    public RecognitionListener listener = new RecognitionListener() {
        public void onReadyForSpeech(Bundle params) {
            Toast.makeText(getApplicationContext(),"�����ν��� �����մϴ�.",Toast.LENGTH_SHORT).show();
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
                    message = "����� ����";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "Ŭ���̾�Ʈ ����";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "�۹̼� ����";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "��Ʈ��ũ ����";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "��Ʈ�� Ÿ�Ӿƿ�";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "ã�� �� ����";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER�� �ٻ�";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "������ �̻���";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "���ϴ� �ð��ʰ�";
                    break;
                default:
                    message = "�� �� ���� ������";
                    break;
            }

            Toast.makeText(getApplicationContext(), "������ �߻��Ͽ����ϴ�. : " + message,Toast.LENGTH_SHORT).show();
        }

        public void onResults(Bundle results) {
            // ���� �ϸ� ArrayList�� �ܾ �ְ� textView�� �ܾ �̾��ݴϴ�.
            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            for(int i = 0; i < matches.size() ; i++){
                expressionEditText.setText(matches.get(i));
            }
        }

        public void onPartialResults(Bundle partialResults) {}

        public void onEvent(int eventType, Bundle params) {}
    };



    // �ʿ��� ���� �ν� ���� �޼��带 �߰��� �� ����
}

