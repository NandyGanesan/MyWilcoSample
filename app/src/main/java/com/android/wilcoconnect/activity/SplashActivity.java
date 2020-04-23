package com.android.wilcoconnect.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.android.wilcoconnect.R;
import com.android.wilcoconnect.app.MainApplication;

import static com.android.wilcoconnect.app.MainApplication.MY_PREFS_NAME;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);

        AppCenter.start(getApplication(), "Secret",
                Analytics.class, Crashes.class);
        /*
        * Check whether the Token is Enable or not
        * */
        SharedPreferences preferences = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        if(preferences!=null){
            MainApplication.token_data = preferences.getString("header","No name defined");
        }
            /*
             * Display the Splash Screen for Particular Time Period
             * */
            new Handler().postDelayed(() -> {
                Log.i(TAG,"GO for Login Screen");
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivityForResult(intent, 101);
                finish();
            },SPLASH_TIME_OUT);
    }

}