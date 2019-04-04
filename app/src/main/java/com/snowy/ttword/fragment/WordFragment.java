package com.snowy.ttword.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.snowy.common.utils.ScreenUtils;
import com.snowy.ttword.R;
import com.snowy.ttword.activity.PhoneticListActivity;
import com.snowy.widget.ItemView;

/**
 * @author guobaolun
 */
public class WordFragment extends Fragment implements View.OnClickListener {

    private SwipeToLoadLayout swipeToLoadLayout;
    private TextView reciteWordCountTv;
    private TextView unfinishedCountTv;
    private TextView totalCountTv;
    private TextView accomplishCountTv;
    private TextView dayWordCountTv;
    private TextView monthWordCountTv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

//        return inflater.inflate(R.layout.fragment_word, container,false);

        View view = inflater.inflate(R.layout.fragment_word, container, false);
        initView(view);
        return view;


    }


    private void initView(View view) {

//        phonetic_item
        ItemView phoneticItem = view.findViewById(R.id.phonetic_item);
        phoneticItem.setOnClickListener(this);
//        RelativeLayout phoneticLl = view.findViewById(R.id.phonetic_ll);
//        phoneticLl.setOnClickListener(this);
//
//        RelativeLayout itemIgnoreWordRl = view.findViewById(R.id.item_ignore_word_rl);
//        itemIgnoreWordRl.setOnClickListener(this);
//
//        RelativeLayout itemAddWordRl = view.findViewById(R.id.item_add_word_rl);
//        itemAddWordRl.setOnClickListener(this);
//
//        RelativeLayout itemStudySettingRl = view.findViewById(R.id.item_study_setting_rl);
//        itemStudySettingRl.setOnClickListener(this);


        Button reciteBt = view.findViewById(R.id.recite_bt);
        reciteBt.setOnClickListener(this);


        reciteWordCountTv = view.findViewById(R.id.recite_word_count_tv);
        unfinishedCountTv = view.findViewById(R.id.unfinished_count_tv);
        totalCountTv = view.findViewById(R.id.total_count_tv);
        accomplishCountTv = view.findViewById(R.id.accomplish_count_tv);
        dayWordCountTv = view.findViewById(R.id.day_word_count_tv);
        monthWordCountTv = view.findViewById(R.id.month_word_count_tv);

        LinearLayout dayWordCountLl = view.findViewById(R.id.day_word_count_ll);
        dayWordCountLl.setOnClickListener(this);

        LinearLayout monthWordCountLl = view.findViewById(R.id.month_word_count_ll);
        monthWordCountLl.setOnClickListener(this);

        LinearLayout reciteWordCountLl = view.findViewById(R.id.recite_word_count_ll);
        reciteWordCountLl.setOnClickListener(this);

        LinearLayout unfinishedCountLl = view.findViewById(R.id.unfinished_count_ll);
        unfinishedCountLl.setOnClickListener(this);

        LinearLayout totalCountLl = view.findViewById(R.id.total_count_ll);
        totalCountLl.setOnClickListener(this);

        LinearLayout accomplishCountLl = view.findViewById(R.id.accomplish_count_ll);
        accomplishCountLl.setOnClickListener(this);

        int height = (ScreenUtils.getScreenWidth(getActivity()) - ScreenUtils.dip2px(getActivity(), 2)) / 3;



        LinearLayout.LayoutParams dayWordCountParams = (LinearLayout.LayoutParams) dayWordCountLl.getLayoutParams();
        dayWordCountParams.height = height;
        dayWordCountLl.setLayoutParams(dayWordCountParams);

        LinearLayout.LayoutParams monthWordCParams = (LinearLayout.LayoutParams) monthWordCountLl.getLayoutParams();
        monthWordCParams.height = height;
        monthWordCountLl.setLayoutParams(monthWordCParams);


        LinearLayout.LayoutParams reciteWordCountParams = (LinearLayout.LayoutParams) reciteWordCountLl.getLayoutParams();
        reciteWordCountParams.height = height;
        reciteWordCountLl.setLayoutParams(reciteWordCountParams);


        LinearLayout.LayoutParams unfinishedCountParams = (LinearLayout.LayoutParams) unfinishedCountLl.getLayoutParams();
        unfinishedCountParams.height = height;
        unfinishedCountLl.setLayoutParams(unfinishedCountParams);



        LinearLayout.LayoutParams totalCountParams = (LinearLayout.LayoutParams) totalCountLl.getLayoutParams();
        totalCountParams.height = height;
        totalCountLl.setLayoutParams(totalCountParams);


        LinearLayout.LayoutParams accomplishCountParams = (LinearLayout.LayoutParams) accomplishCountLl.getLayoutParams();
        accomplishCountParams.height = height;
        accomplishCountLl.setLayoutParams(accomplishCountParams);




//        swipeToLoadLayout = view.findViewById(R.id.swipeToLoadLayout);
//        swipeToLoadLayout.setOnRefreshListener(new MyOnRefreshListener());
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.phonetic_item:
                Intent intent = new Intent();
                intent.setClass(getActivity(), PhoneticListActivity.class);
                startActivity(intent);
                break;
        }
    }
}