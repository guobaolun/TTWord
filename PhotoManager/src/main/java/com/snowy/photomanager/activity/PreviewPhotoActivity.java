package com.snowy.photomanager.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.snowy.common.dialog.ConfirmDialogManager;
import com.snowy.common.utils.ScreenUtils;
import com.snowy.photomanager.PhotoConstants;
import com.snowy.photomanager.R;

import java.util.ArrayList;

public class PreviewPhotoActivity extends BigImageBaseActivity implements View.OnClickListener {


    private ArrayList<String> mSelectImgList;
    private ArrayList<Integer> selectPositionList;
    private ConfirmDialogManager dialogFactory;

    public static final int delete_ib_id = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSelectImgList = getIntent().getStringArrayListExtra(PhotoConstants.IMAGE_SELECT_LIST);
        selectPositionList = getIntent().getIntegerArrayListExtra(PhotoConstants.IMAGE_POSITION_LIST);

    }



    @Override
    protected void initView() {
        ImageButton delete_ib = new ImageButton(getApplicationContext());
        delete_ib.setId(delete_ib_id);
        delete_ib.setPadding(8, 8, 8, 8);
        delete_ib.setBackgroundColor(Color.alpha(0));
        delete_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.selector_delete_bt);
        delete_ib.setImageDrawable(drawable);

        topRl.addView(delete_ib);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, delete_ib.getId());
        int margin = ScreenUtils.dip2px(getApplicationContext(), 10f);
        params.setMargins(margin, margin, margin, margin);
        delete_ib.setLayoutParams(params);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case delete_ib_id:
                showConfirmationDialog();
                break;
        }
    }


    public void showConfirmationDialog() {
        final ConfirmDialogManager.OnClickListener listener = new ConfirmDialogManager.OnClickListener() {
            @Override
            public void ok() {
                dialogFactory.dismiss();

                int position = mViewPager.getCurrentItem();
                mImageInfoList.remove(position);
                mSelectImgList.remove(position);
                selectPositionList.remove(Integer.valueOf(position));
                if (mSelectImgList.size() == 0) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList(PhotoConstants.IMAGE_SELECT_LIST, mSelectImgList);
                    bundle.putIntegerArrayList(PhotoConstants.IMAGE_POSITION_LIST, selectPositionList);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    mTextView.setText(String.valueOf((position + 1) + "/" + mImageInfoList.size()));
                    mPagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void cancel() {
                dialogFactory.dismiss();
            }

        };

        if (dialogFactory == null) {
            dialogFactory = new ConfirmDialogManager(this, listener, "确定删除这张照片吗?");
        }
        dialogFactory.showDialog();
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(PhotoConstants.IMAGE_SELECT_LIST, mSelectImgList);
        bundle.putIntegerArrayList(PhotoConstants.IMAGE_POSITION_LIST, selectPositionList);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        super.finish();
    }
}
