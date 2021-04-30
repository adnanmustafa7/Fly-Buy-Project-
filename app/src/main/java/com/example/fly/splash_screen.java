package com.example.fly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splash_screen extends AppCompatActivity {


    private static int SPLASH_SCREEN = 3000;

    Animation top, bottom;
    ImageView imageView;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        top = AnimationUtils.loadAnimation(this, R.anim.animation_top);
        bottom = AnimationUtils.loadAnimation(this, R.anim.animation_buttom);

        imageView = (ImageView) findViewById(R.id.splash_img);
        textView = (TextView) findViewById(R.id.splash_text);

        imageView.setAnimation(top);
        textView.setAnimation(bottom);

//
//        Thread thread = new Thread(){
//            public void run(){
//
//                try {
//                    sleep(5000);
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                }
//                finally {
//                    Intent intent = new Intent(splash_screen.this, MainActivity.class);
//                    startActivity(intent);
//                }
//                finish();
//            }
//        };
//
//        thread.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash_screen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);

    }
}