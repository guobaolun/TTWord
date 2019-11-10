package com.snowy.ttword.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snowy.common.activity.BaseActivity;
import com.snowy.ttword.R;

/**
 * @author guobaolun
 * @since 2018/4/17
 */
public class CommentSendManager implements View.OnClickListener {



    private LinearLayout voiceLl;
    private ImageButton recordingIb;
    private TextView recordingTimeTv;
    private Button reRecordBt;
    private Button recordingSendBt;
    private ImageButton recordingPlayIb;
    private EditText messageEt;
    private TextView commentTv;
    private RelativeLayout sendContentRl;


    private BaseActivity mActivity;

    public CommentSendManager(BaseActivity activity) {

        mActivity = activity;
        initView();
    }





    @SuppressLint("ClickableViewAccessibility")
    private void initView() {

        commentTv = mActivity.findViewById(R.id.comment_tv);
        commentTv.setOnClickListener(this);


        sendContentRl = mActivity.findViewById(R.id.send_content_rl);


        ImageButton showVoiceIb = mActivity.findViewById(R.id.show_voice_ib);
        showVoiceIb.setOnClickListener(this);

        Button sendContentBt = mActivity.findViewById(R.id.send_content_bt);
        sendContentBt.setOnClickListener(this);

        voiceLl = mActivity.findViewById(R.id.voice_ll);

        recordingTimeTv = mActivity.findViewById(R.id.recording_time_tv);

        recordingIb = mActivity.findViewById(R.id.recording_ib);
        recordingIb.setOnClickListener(this);

        reRecordBt = mActivity.findViewById(R.id.re_record_bt);
        reRecordBt.setOnClickListener(this);

        recordingSendBt = mActivity.findViewById(R.id.recording_send_bt);
        recordingSendBt.setOnClickListener(this);

        recordingPlayIb = mActivity.findViewById(R.id.recording_play_ib);

        messageEt = mActivity.findViewById(R.id.message_et);

        messageEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                voiceLl.setVisibility(View.GONE);
                return false;
            }
        });

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_voice_ib:
                commentTv.setVisibility(View.GONE);
                if (voiceLl.getVisibility() == View.VISIBLE) {
                    voiceLl.setVisibility(View.GONE);
                } else {
                    closeKeyboard();
                    voiceLl.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.recording_ib:
                break;
            case R.id.re_record_bt:
                break;
            case R.id.send_content_bt:
                break;
            case R.id.recording_send_bt:
                break;
            case R.id.comment_tv:
                commentTv.setVisibility(View.GONE);
                sendContentRl.setVisibility(View.VISIBLE);
                showSoftInputFromWindow(messageEt);
                break;
            default:
                break;

        }
    }



    private void closeKeyboard() {
//        messageEt.setInputType(InputType.TYPE_NULL); // 关闭软键盘
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(mActivity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }


    /**
     * EditText获取焦点并显示软键盘
     */
    private void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}
