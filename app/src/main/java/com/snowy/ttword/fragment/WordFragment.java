package com.snowy.ttword.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snowy.ttword.R;

/**
 * @author guobaolun
 */
public class WordFragment extends BaseMVPFragment<WordPresenter> implements View.OnClickListener,IWordView {


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
        View view = inflater.inflate(R.layout.fragment_word, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {


//        int height = (ScreenUtils.getScreenWidth(getContext()) - 6) / 3;
//        LinearLayout dataLayout = view.findViewById(R.id.data_layout);
//
//
//        LinearLayout.LayoutParams dataParams = (LinearLayout.LayoutParams) dataLayout.getLayoutParams();
//        dataParams.height = height*2;
//        dataLayout.setLayoutParams(dataParams);

        RelativeLayout phoneticLl = view.findViewById(R.id.phonetic_ll);
        phoneticLl.setOnClickListener(this);

        RelativeLayout itemIgnoreWordRl = view.findViewById(R.id.item_ignore_word_rl);
        itemIgnoreWordRl.setOnClickListener(this);

        RelativeLayout itemAddWordRl = view.findViewById(R.id.item_add_word_rl);
        itemAddWordRl.setOnClickListener(this);

        RelativeLayout itemStudySettingRl = view.findViewById(R.id.item_study_setting_rl);
        itemStudySettingRl.setOnClickListener(this);


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

        int hieght = (ScreenUtils.getScreenWidth(getActivity()) - ScreenUtils.dip2px(getActivity(), 2)) / 3;



        LinearLayout.LayoutParams dayWordCountParams = (LinearLayout.LayoutParams) dayWordCountLl.getLayoutParams();
        dayWordCountParams.height = hieght;
        dayWordCountLl.setLayoutParams(dayWordCountParams);

        LinearLayout.LayoutParams monthWordCParams = (LinearLayout.LayoutParams) monthWordCountLl.getLayoutParams();
        monthWordCParams.height = hieght;
        monthWordCountLl.setLayoutParams(monthWordCParams);


        LinearLayout.LayoutParams reciteWordCountParams = (LinearLayout.LayoutParams) reciteWordCountLl.getLayoutParams();
        reciteWordCountParams.height = hieght;
        reciteWordCountLl.setLayoutParams(reciteWordCountParams);


        LinearLayout.LayoutParams unfinishedCountParams = (LinearLayout.LayoutParams) unfinishedCountLl.getLayoutParams();
        unfinishedCountParams.height = hieght;
        unfinishedCountLl.setLayoutParams(unfinishedCountParams);



        LinearLayout.LayoutParams totalCountParams = (LinearLayout.LayoutParams) totalCountLl.getLayoutParams();
        totalCountParams.height = hieght;
        totalCountLl.setLayoutParams(totalCountParams);


        LinearLayout.LayoutParams accomplishCountParams = (LinearLayout.LayoutParams) accomplishCountLl.getLayoutParams();
        accomplishCountParams.height = hieght;
        accomplishCountLl.setLayoutParams(accomplishCountParams);




        swipeToLoadLayout = view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(new MyOnRefreshListener());
    }

    @Override
    protected WordPresenter createPresenter() {
        return new WordPresenter(this);
    }



    @Override
    public void onResume() {
        super.onResume();
        mPresenter.setData();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
//            case R.id.wordbook_rl:
//                intent.setClass(getActivity(), WordbookListActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.word_progress_rl:
//                break;
            case R.id.phonetic_ll:
                intent.setClass(getActivity(), PhoneticListActivity.class);
                startActivity(intent);
                break;
            case R.id.recite_bt:
                mPresenter.startSelectWord();
                break;
            case R.id.day_word_count_ll:
                intent.setClass(getActivity(), DayWordActivity.class);
                startActivity(intent);
                break;
            case R.id.month_word_count_ll:
                intent.setClass(getActivity(), MonthWordActivity.class);
                startActivity(intent);
                break;
            case R.id.recite_word_count_ll:
                startProgressActivity(WordProgressActivity.TITLE_ALL_RECITE_WORD, Constants.WORD_PROGRESS_FIND_ALL_RECITE_WORD_URL);
                break;
            case R.id.unfinished_count_ll:
                startProgressActivity(WordProgressActivity.TITLE_UNFINISH_WORD,Constants.WORD_PROGRESS_FIND_UNFINISH_WORD_URL);
                break;
            case R.id.total_count_ll:
                startProgressActivity(WordProgressActivity.TITLE_ALL_WORD,Constants.WORD_PROGRESS_FIND_ALL_WORD_URL);
                break;
            case R.id.accomplish_count_ll:
                startProgressActivity(WordProgressActivity.TITLE_ACCOMPLISH_WORD,Constants.WORD_PROGRESS_FIND_ACCOMPLISH_WORD_URL);
                break;

            case R.id.item_ignore_word_rl:
                intent.setClass(getActivity(), IgnoreWordActivity.class);
                startActivity(intent);
                break;

            case R.id.item_add_word_rl:
                intent.setClass(getActivity(), AddWordActivity.class);
                startActivity(intent);
                break;
            case R.id.item_study_setting_rl:
                intent.setClass(getActivity(), StudySettingActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }


    private void startProgressActivity(String title ,String url){
        Intent intent = new Intent();
        intent.putExtra(WordProgressActivity.TITLE,title);
        intent.putExtra(WordProgressActivity.URL,url);
        intent.setClass(getActivity(), WordProgressActivity.class);
        startActivity(intent);
    }




    @Override
    public void setRefreshing(boolean loadingMore) {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(false);
            }
        }, 300);
    }

    @Override
    public void setTextView(String reciteWordCount, String unfinishedCount, String totalCount, String accomplishCount, String dayWordCount, String monthWordCount) {
        reciteWordCountTv.setText(reciteWordCount);
        unfinishedCountTv.setText(unfinishedCount);
        totalCountTv.setText(totalCount);
        accomplishCountTv.setText(accomplishCount);
        dayWordCountTv.setText(dayWordCount);
        monthWordCountTv.setText(monthWordCount);
    }



    class MyOnRefreshListener implements OnRefreshListener {

        @Override
        public void onRefresh() {
            mPresenter.setData();
        }
    }
}