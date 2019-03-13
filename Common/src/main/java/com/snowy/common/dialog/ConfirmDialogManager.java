package com.snowy.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snowy.common.R;
import com.snowy.common.utils.ScreenUtils;


public class ConfirmDialogManager implements View.OnClickListener {


    private final Dialog mDialog;
    private OnClickListener listener;
    private final Button cancel_bt;
    private final Button ok_bt;
    private final TextView textview;


    public ConfirmDialogManager(Activity activity, OnClickListener listener, String text) {
        this.listener = listener;
        mDialog = new Dialog(activity, R.style.ProgressDialog);

        View view = View.inflate(activity, R.layout.dialog_confirm, null);
        LinearLayout layout = view.findViewById(R.id.layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) (ScreenUtils.getScreenWidth(activity) * 0.7), LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(lp);

        textview = view.findViewById(R.id.textview);
        textview.setText(text);

        cancel_bt = view.findViewById(R.id.cancel_bt);
        cancel_bt.setOnClickListener(this);

        ok_bt = view.findViewById(R.id.ok_bt);
        ok_bt.setOnClickListener(this);

        mDialog.setContentView(view);

    }

    public void setText(String text){
        textview.setText(text);
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public void showDialog() {
        mDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == ok_bt.getId()) {
            listener.ok();
        } else if (v.getId() == cancel_bt.getId()) {
            listener.cancel();
        }
    }

    public interface OnClickListener {
        void ok();

        void cancel();
    }



}
