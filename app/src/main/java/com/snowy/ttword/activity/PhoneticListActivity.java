package com.snowy.ttword.activity;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snowy.common.activity.BaseActivity;
import com.snowy.common.utils.ScreenUtils;
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
            "iə", "εə", "uə", "a", "",
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
        int itemHeight = ScreenUtils.getScreenWidth(getApplicationContext()) / 8 - ScreenUtils.dip2px(getApplicationContext(), 2);
        TextView vowelTv = findViewById(R.id.vowel_tv);
        TextView consonantTv = findViewById(R.id.consonant_tv);

        ViewGroup.LayoutParams params = vowelTv.getLayoutParams();
        params.height = itemHeight;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        vowelTv.setLayoutParams(params);
        consonantTv.setLayoutParams(params);


        LinearLayout vowelLl = findViewById(R.id.vowel_ll);
        ViewGroup.LayoutParams vowelParams = vowelLl.getLayoutParams();
        vowelParams.height = itemHeight * 5;
//        vowelParams.width = ScrollView.LayoutParams.MATCH_PARENT;
        vowelLl.setLayoutParams(vowelParams);


        LinearLayout consonantLl = findViewById(R.id.consonant_ll);
        ViewGroup.LayoutParams consonantParams = consonantLl.getLayoutParams();
        consonantParams.height = itemHeight * 9;
        consonantParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        consonantLl.setLayoutParams(consonantParams);


        GridView vowelGridView = findViewById(R.id.vowel_gridview);
        vowelGridView.setAdapter(new PhoneticListAdapter(getApplicationContext(), VOWEL_ARR,itemHeight));

        GridView consonantGridview = findViewById(R.id.consonant_gridview);
        consonantGridview.setAdapter(new PhoneticListAdapter(getApplicationContext(), CONSONANT_ARR,itemHeight));

    }

    @Override
    public void onClick(View v) {

    }


}
