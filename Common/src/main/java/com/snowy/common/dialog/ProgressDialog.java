package com.snowy.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.snowy.common.R;


public class ProgressDialog extends Dialog {

	private final TextView textview;


	public ProgressDialog(Context context ) {
		super(context, R.style.ProgressDialog);
		View view = View.inflate(context, R.layout.dialog_progress,null);
		textview =  view.findViewById(R.id.textview);
		setContentView(view);
	}




	public void setText(String text) {
		textview.setText(text);
		textview.setVisibility(View.VISIBLE);
	}




}
