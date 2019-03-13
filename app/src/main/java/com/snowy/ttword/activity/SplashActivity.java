package com.snowy.ttword.activity;

import android.os.Bundle;

import com.snowy.common.activity.BaseActivity;
import com.snowy.ttword.R;

/**
 * @author guobaolun
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        startActivity(new Intent(this,IndexActivity.class));
        finish();
    }


    @Override
    protected int onContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }






}
