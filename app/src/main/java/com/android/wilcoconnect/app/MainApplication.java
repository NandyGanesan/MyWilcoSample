package com.android.wilcoconnect.app;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {

    /*
     * Initialize the value and variable
     * */
    public static String token_data=null;
    public static final String MY_PREFS_NAME = "Tokendata";
    Context context;

    /*
    * Get the application context
    * */
    private MainApplication() {
        this.context = this.getApplicationContext();
    }

    /*
    * Super oncreate method
    * */
    @Override
    public void onCreate() {
        super.onCreate();
    }

}
