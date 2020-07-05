package com.destinyapp.absensi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.destinyapp.absensi.R;
import com.destinyapp.absensi.SharedPreferance.DB_Helper;

public class SplashActivity extends AppCompatActivity {
    LottieAnimationView anim;
    DB_Helper dbHelper;
    String username,nama,divisi,level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        dbHelper = new DB_Helper(SplashActivity.this);
        Cursor cursor = dbHelper.checkSession();
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                username = cursor.getString(0);
                nama = cursor.getString(1);
                divisi = cursor.getString(2);
                level = cursor.getString(3);
            }
        }
        if (username != null){
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        anim = findViewById(R.id.animation);
        anim.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}
