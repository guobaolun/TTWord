package com.snowy.photomanager.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.snowy.common.activity.BaseActivity;
import com.snowy.common.dialog.BottomSlideDialog;
import com.snowy.common.manager.PermissionManager;
import com.snowy.photomanager.PhotoConstants;
import com.snowy.photomanager.R;
import com.snowy.photomanager.adapter.SelectBigImageAdapter;
import com.snowy.photomanager.adapter.SelectManyPhotoGridAdapter;
import com.snowy.photomanager.entity.SelectImage;
import com.snowy.photomanager.listener.SelectPhotoClickListener;

import java.util.ArrayList;
import java.util.Collections;

public class SelectManyPhotoActivity extends BaseActivity implements View.OnClickListener {

    public static final int SET_IMAGE_PATH = 1;

    private Handler mHandler = new MyHandler(this);
    private SelectManyPhotoGridAdapter mGridAdapte;
    private GridView gridview;
    private ProgressBar progress;
    private BottomSlideDialog pagerDialog;
    private SelectBigImageAdapter pagerAdapter;
    private ViewPager viewPager;
    private View rootview;
    private CheckBox checkbox;
    private Button ok_bt;
    private SelectPhotoClickListener clickListener;
    private ArrayList<String> mSelectImgList;
    private ArrayList<Integer> mSelectPositionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSelectImgList = getIntent().getStringArrayListExtra(PhotoConstants.IMAGE_SELECT_LIST);
        mSelectPositionList = getIntent().getIntegerArrayListExtra(PhotoConstants.IMAGE_POSITION_LIST);

        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
        };

        mPermissionManager.checkPermission(this, permissions, new MyPermissionCallback());

    }

    @Override
    protected int onContentView() {
        return R.layout.activity_select_photo;
    }


    @Override
    protected void initView() {

        rootview = findViewById(R.id.rootview);
        ImageButton back_ib = findViewById(R.id.back_ib);
        back_ib.setOnClickListener(this);

        ok_bt = findViewById(R.id.ok_bt);
        ok_bt.setVisibility(View.VISIBLE);
        ok_bt.setOnClickListener(this);

        progress = findViewById(R.id.progress);

        gridview = findViewById(R.id.gridview);
        mGridAdapte = new SelectManyPhotoGridAdapter(getApplicationContext(), ok_bt);

        gridview.setAdapter(mGridAdapte);
        MyOnItemClickListener mItemClickListener = new MyOnItemClickListener();
        gridview.setOnItemClickListener(mItemClickListener);

        if (mSelectImgList != null) {
            ok_bt.setText(String.valueOf("确定(" + mSelectImgList.size() + ")"));
        }
    }


    /**
     * 获取图片路径集合
     */
    private ArrayList<String> getImagePathList() {
        ArrayList<String> list = new ArrayList<>();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = getContentResolver();

        //只查询jpeg和png的图片 gif
        Cursor mCursor = mContentResolver.query(mImageUri, null, MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?", new String[]{"image/jpeg", "image/png", "image/gif"}, MediaStore.Images.Media.DATE_MODIFIED);

        if (mCursor == null) {
            return list;
        }

        while (mCursor.moveToNext()) {
            //获取图片的路径

            String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            list.add(path);
        }

        mCursor.close();
        Collections.reverse(list);
        return list;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        int id = v.getId();
        if (id == R.id.back_ib) {
            finish();
        } else if (id == R.id.ok_bt) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(PhotoConstants.IMAGE_SELECT_LIST, mGridAdapte.getSelectList());
            bundle.putIntegerArrayList(PhotoConstants.IMAGE_POSITION_LIST, mGridAdapte.getSelectPositionList());
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        } else if (id == R.id.cancel_iv) {
            rootview.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.theme_bg_b));
            if (pagerDialog.isShowing()) {
                pagerDialog.dismiss();
            }

        }
    }

    public void showViewPagerDialog(final int position) {

        if (pagerDialog == null) {
            View view = View.inflate(getApplicationContext(), R.layout.dialog_big_pager, null);
            viewPager = view.findViewById(R.id.viewpager);
            TextView pageText = view.findViewById(R.id.textview);
            ImageView cancel_iv = view.findViewById(R.id.cancel_iv);
            checkbox = view.findViewById(R.id.checkbox);
            cancel_iv.setOnClickListener(this);
            pagerAdapter = new SelectBigImageAdapter(getApplicationContext());
            viewPager.setAdapter(pagerAdapter);
            pagerDialog = new BottomSlideDialog(this, R.style.ActionSheetDialogStyle);
            pagerDialog.setContentView(view);
            pagerDialog.setDispatchListener(new BottomSlideDialog.OnDispatchListener() {
                @Override
                public void onDispatch() {
                    rootview.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.theme_bg_b));
                }
            });

            clickListener = new SelectPhotoClickListener(getApplicationContext(), ok_bt, mGridAdapte);

            checkbox.setOnClickListener(clickListener);
            viewPager.addOnPageChangeListener(new MyOnPageChangeListener(pageText, checkbox));

        }
        clickListener.setPosition(position);
        int num = mGridAdapte.getImageList().get(position).getNum();
        if (num > 0) {
            checkbox.setText(String.valueOf(num));
        } else {
            checkbox.setText("");
        }

        checkbox.setChecked(mGridAdapte.getImageList().get(position).isChecked());
        rootview.setBackgroundColor(Color.BLACK);
        pagerAdapter.setImageList(mGridAdapte.getImageList(), mGridAdapte.getSelectList());
        viewPager.setCurrentItem(position);
        pagerAdapter.notifyDataSetChanged();
        pagerDialog.show();

    }


    private void setImageData() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }


        //TODO
        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> pathList = getImagePathList();
                ArrayList<SelectImage> imageList = new ArrayList<>();


                for (String path : pathList) {
                    SelectImage selectImage = new SelectImage();
                    selectImage.setPath(path);
                    if (mSelectImgList != null && mSelectImgList.contains(path)) {
                        selectImage.setChecked(true);
                        selectImage.setNum(mSelectImgList.indexOf(path) + 1);
                    } else {
                        selectImage.setChecked(false);
                    }
                    imageList.add(selectImage);
                }

                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putSerializable("imageList", imageList);
                bundle.putStringArrayList(PhotoConstants.IMAGE_SELECT_LIST, mSelectImgList);
                bundle.putIntegerArrayList(PhotoConstants.IMAGE_POSITION_LIST, mSelectPositionList);
                msg.setData(bundle);
                msg.what = SET_IMAGE_PATH;
                mHandler.sendMessage(msg);

            }
        });


    }


    class MyOnItemClickListener implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            showViewPagerDialog(position);
        }
    }


    class MyPermissionCallback implements PermissionManager.PermissionCallback {

        @Override
        public void grant() {
            setImageData();
        }

        @Override
        public void refuse(String permission) {
            finish();

        }

    }

    private static class MyHandler extends Handler {
        private final SelectManyPhotoActivity mActivity;

        MyHandler(SelectManyPhotoActivity activity) {
            mActivity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SET_IMAGE_PATH:
                    Bundle bundle = msg.getData();
                    ArrayList<SelectImage> imageList = (ArrayList<SelectImage>) bundle.getSerializable("imageList");
                    ArrayList<String> selectList = bundle.getStringArrayList(PhotoConstants.IMAGE_SELECT_LIST);
                    ArrayList<Integer> mSelectPositionList = bundle.getIntegerArrayList(PhotoConstants.IMAGE_POSITION_LIST);
                    mActivity.mGridAdapte.setImageList(imageList, selectList, mSelectPositionList);
                    mActivity.gridview.setVisibility(View.VISIBLE);
                    mActivity.progress.setVisibility(View.GONE);
                    break;
                default:
                    break;

            }

        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        private TextView pageText;
        private CheckBox checkbox;

        MyOnPageChangeListener(TextView pageText, CheckBox checkbox) {
            this.pageText = pageText;
            this.checkbox = checkbox;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            pageText.setText((position + 1) + "/" + mGridAdapte.getImageList().size());

            boolean isChecked = mGridAdapte.getImageList().get(position).isChecked();
            int num = mGridAdapte.getImageList().get(position).getNum();
            checkbox.setChecked(isChecked);

            if (isChecked && num > 0) {
                checkbox.setText(String.valueOf(num));
            } else {
                checkbox.setText("");
            }
            clickListener.setPosition(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}