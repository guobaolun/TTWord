package com.snowy.ttword.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

import com.snowy.common.activity.BaseActivity;
import com.snowy.ttword.R;
import com.snowy.ttword.adapter.PhoneticListAdapter;


/**
 * @author guobaolun
 */
public class PhoneticListActivity extends BaseActivity implements View.OnClickListener {


    public static final String PHONETIC = "Phonetic";


    private static final String[] VOWEL_ARR = {
            "i:", "i", "e", "æ", "",
            "ʌ", "ə:", "ə", "", "",
            "u:", "u", "ɔ:", "ɔ", "a:",
            "ei", "ai", "ɔi", "əu", "au",
            "iə", "εə", "uə", "", "",
    };



    private static final String[] CONSONANT_ARR = {
            "p", "t", "k", "", "",
            "b", "d", "g", "", "",
            "f", "s", "ʃ", "θ", "h",
            "v", "z", "ʒ", "ð", "r",
            "tʃ", "tr", "ts", "", "",
            "dʒ", "dr", "dz", "", "",
            "m", "n", "ŋ", "", "",
            "l", "", "", "", "",
            "w", "j", "", "", "",
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int onContentView() {
        return R.layout.activity_phonetic_list;
    }


    @Override
    protected void initView() {


        GridView vowelGridView = findViewById(R.id.vowel_gridview);
        vowelGridView.setAdapter(new PhoneticListAdapter(getApplicationContext(),VOWEL_ARR));

        GridView consonantGridview =  findViewById(R.id.consonant_gridview);
        consonantGridview.setAdapter(new PhoneticListAdapter(getApplicationContext(),CONSONANT_ARR));

    }

    @Override
    public void onClick(View v) {

    }






}
