package com.snowy.ttword;

import android.support.v7.app.AppCompatDelegate;

import com.snowy.common.BaseApplication;
import com.snowy.common.manager.CrashHandler;
import com.snowy.common.utils.SharedPreferencesManager;


public class MyApplication extends BaseApplication {



    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);

        int mode = SharedPreferencesManager.getInstance(getApplicationContext()).getInt(Constants.SP_KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_NO);
//        mode = SharedPreferencesManager.getInstance(getApplicationContext()).getInt(Constants.SP_KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_YES);
        AppCompatDelegate.setDefaultNightMode(mode);
    }


}