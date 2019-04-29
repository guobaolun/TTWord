package com.snowy.ttword.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.snowy.common.activity.BaseActivity;
import com.snowy.ttword.R;

/**
 * @author guobaolun
 */
public class PhoneticDetailsActivity extends BaseActivity {

    private String phoneticStr;

    private static final String LEFT_BRACKET = "[ ";
    private static final String RIGHT_BRACKET = " ]";
    private String combination;
    private String regex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int onContentView() {
        return R.layout.activity_phonetic_details;
    }


    @Override
    protected void initView() {

    }



}
