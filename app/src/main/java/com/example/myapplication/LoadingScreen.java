package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import java.util.Random;

public class LoadingScreen extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private ProgressBar mProgress;

    int i1=0;
    int setProgress=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_loading_screen);

        mProgress = findViewById(R.id.mProgress);

        new Thread(new Runnable() {
            @Override
            public void run() {
                onProgress();
                nextPage();
            }
        }).start();
    }

    private void onProgress(){
        for (int i=0;i<100;i+=i1){
            try {
                Thread.sleep(SPLASH_DISPLAY_LENGTH);
                mProgress.setProgress(i);
            }catch (Exception e){
                e.printStackTrace();
            }
            randomNumber();
        }
    }

    private void nextPage(){
        if(setProgress>=100){
            mProgress.setProgress(100);
        }
        Intent i = new Intent(LoadingScreen.this,LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void randomNumber(){
        Random r = new Random();
        i1 = r.nextInt(50 - 1) + 1;
    }
}