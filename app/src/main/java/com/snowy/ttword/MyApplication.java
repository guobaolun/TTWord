package com.snowy.ttword;

import android.support.v7.app.AppCompatDelegate;

import com.snowy.common.BaseApplication;
import com.snowy.common.manager.CrashHandler;
import com.snowy.common.utils.CacheUtils;


public class MyApplication extends BaseApplication {


    public static final String SP_KEY_THEME_MODE = "themeMode";

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);

       int mode = CacheUtils.getInstance(getApplicationContext()).getInt(SP_KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_NO);
//       int mode = CacheUtils.getInstance(getApplicationContext()).getInt(SP_KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_YES);
        AppCompatDelegate.setDefaultNightMode(mode);
//        AppCompatDelegate.setDefaultNightMode(isNight ?AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }


}
