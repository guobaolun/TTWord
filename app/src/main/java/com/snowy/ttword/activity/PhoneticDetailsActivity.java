package com.snowy.ttword.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.snowy.common.activity.BaseActivity;
import com.snowy.ttword.R;
import com.snowy.ttword.adapter.PhoneticWordBaseAdapter;
import com.snowy.ttword.dao.PhoneticDao;
import com.snowy.ttword.dao.WordDao;
import com.snowy.ttword.entity.Word;
import com.snowy.ttword.manager.CommentListManager;
import com.snowy.ttword.manager.CommentSendManager;

import java.util.List;

/**
 * @author guobaolun
 */
public class PhoneticDetailsActivity extends BaseActivity implements View.OnClickListener {

    private String phoneticStr;

    private static final String LEFT_BRACKET = "[ ";
    private static final String RIGHT_BRACKET = " ]";
    private String combination;
    private String regex;
    private PhoneticWordBaseAdapter wordAdapter;


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

        Intent intent = getIntent();
        phoneticStr = intent.getStringExtra(PhoneticListActivity.PHONETIC);

        PhoneticDao phoneticDao = new PhoneticDao(getApplicationContext());
        combination = phoneticDao.queryCombination(phoneticStr);
        regex = phoneticDao.queryRegex(phoneticStr);

        ImageButton backIb = findViewById(R.id.back_ib);
        backIb.setOnClickListener(this);

        new CommentSendManager(this);

        CommentListManager commentManager = new CommentListManager(getBaseActivity());


        View headerView = View.inflate(this, R.layout.item_phonetic_details_header, null);
        HeadViewHolder headViewHolder = new HeadViewHolder(headerView);
        commentManager.setHeadView(headViewHolder);
//        commentManager.setLoaderListener();


        queryPhonetic();
    }


    private void queryPhonetic() {
        WordDao wordDao = new WordDao(getApplicationContext());
        List<Word> wordList = wordDao.queryPhoneticWordList(regex);
        wordAdapter.setWordList(wordList);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_ib:
                finish();
            case R.id.refresh_ll:
                queryPhonetic();
                break;
        }
    }


    public class HeadViewHolder extends RecyclerView.ViewHolder {
        public TextView commentCountTv;
        HeadViewHolder(View itemView) {
            super(itemView);

            commentCountTv = itemView.findViewById(R.id.comment_count_tv);
            TextView combinationTv = itemView.findViewById(R.id.combination_tv);
            combinationTv.setText(combination);

            TextView phoneticTv = itemView.findViewById(R.id.phonetic_tv);
            phoneticTv.setText(LEFT_BRACKET + phoneticStr + RIGHT_BRACKET);
            phoneticTv.setOnClickListener(PhoneticDetailsActivity.this);

            LinearLayout refreshLl = itemView.findViewById(R.id.refresh_ll);
            refreshLl.setOnClickListener(PhoneticDetailsActivity.this);

            GridView gridview = itemView.findViewById(R.id.gridview);

            wordAdapter = new PhoneticWordBaseAdapter(getApplicationContext());
            wordAdapter.setRegex(regex);
            gridview.setAdapter(wordAdapter);



        }
    }







}
