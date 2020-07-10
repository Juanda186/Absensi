package com.destinyapp.absensi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.animation.Animator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.destinyapp.absensi.R;
import com.destinyapp.absensi.SharedPreferance.DB_Helper;

public class SplashActivity extends AppCompatActivity {
    LottieAnimationView anim;
    DB_Helper dbHelper;
    String username,nama,divisi,level;
    private static final int PERMISSION_STORAGE_CODE = 1000;
    int READ_PHONE_STATE_PERMISSION = 1;
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
            ChangeActivity();
        }
        anim = findViewById(R.id.animation);
        anim.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED){
                        //
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_STORAGE_CODE);
                        if(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) ==
                                PackageManager.PERMISSION_DENIED){
                            //
                            String[] permissionss = {Manifest.permission.READ_PHONE_STATE};
                            requestPermissions(permissionss,READ_PHONE_STATE_PERMISSION);
                        }
                    }else{
                        ChangeActivity();
                    }
                }else{
                    ChangeActivity();
                }
                ChangeActivity();



            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    private void ChangeActivity(){
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
