package com.example.kookmin.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;

import com.example.kookmin.R;
import com.example.kookmin.login.LoginForm;
import com.example.kookmin.mainactivity.MainActivity;

/**
 * Created by 보운 on 2015-10-31.
 */
public class Splash extends Activity {
    private static final String MY_DB = "my_db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        SharedPreferences sp = getSharedPreferences(MY_DB, Context.MODE_PRIVATE);

        boolean loginCheck = sp.getBoolean("loginCheck", false);
        if(!loginCheck){
            android.os.Handler handler = new android.os.Handler() {
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    startActivity(new Intent(Splash.this, LoginForm.class));
//                    startActivity(new Intent(Splash.this, ImageSdcardActivity.class));
                    finish();
                }
            };
            handler.sendEmptyMessageDelayed(0,1000);
        }
        else{
            android.os.Handler handler = new android.os.Handler() {
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    startActivity(new Intent(Splash.this, MainActivity.class));
//                    startActivity(new Intent(Splash.this, ImageSdcardActivity.class));
                    finish();
                }
            };
            handler.sendEmptyMessageDelayed(0,1000);        }
    }
}