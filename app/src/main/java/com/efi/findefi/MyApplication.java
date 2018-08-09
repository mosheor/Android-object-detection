package com.efi.findefi;

import android.app.Application;
import android.content.Context;

/**
 * MyApplication in order to get the app context
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
