package com.example.previous_dd.electronic_dictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jwt on 2017/6/1.
 */

public class RecognizerIntentActivity extends Activity {

    private ImageButton btnReconizer;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_page1);

        btnReconizer= (ImageButton) this.findViewById(R.id.btnRecognizer);
        btnReconizer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try{
                    //通过Intent传递语音识别的模式，开启语音
                    Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    //语言模式和自由模式的语音识别
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    //提示语音开始
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "开始语音");
                    //开始语音识别
                    startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
                }catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "找不到语音设备", 1).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        //回调获取从谷歌得到的数据
        if(requestCode==VOICE_RECOGNITION_REQUEST_CODE && resultCode==RESULT_OK){
            //取得语音的字符
            ArrayList<String> results=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String resultString="";
            for(int i=0;i<results.size();i++){
                resultString+=results.get(i);
            }
            Toast.makeText(this, resultString, 1).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}