package com.snowy.photomanager.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.snowy.common.activity.BaseActivity;
import com.snowy.common.manager.PermissionManager;
import com.snowy.common.utils.ScreenUtils;
import com.snowy.glide.GlideUtils;
import com.snowy.photomanager.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 图片列表
 */
public class SelectSinglePhotoActivity extends BaseActivity implements View.OnClickListener {

    public static final int CROP_PORTRAIT = 101;
    public static final int SET_IMAGE_PATH = 1;
    public static final String TITLE = "title";

    private Handler mHandler = new MyHandler(this);
    private MyBaseAdapter mAdapter;
    private GridView gridview;
    private ProgressBar progress;
    private MyOnItemClickListener mItemClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
        Intent intent = getIntent();
        String titleStr = intent.getStringExtra(TITLE);
        ImageButton back_ib = findViewById(R.id.back_ib);
        back_ib.setOnClickListener(this);
        TextView title_tv = findViewById(R.id.title_tv);
        if (!TextUtils.isEmpty(titleStr)) {
            title_tv.setText(titleStr);
        }

        progress = findViewById(R.id.progress);

        gridview = findViewById(R.id.gridview);
        mAdapter = new MyBaseAdapter();
        gridview.setAdapter(mAdapter);
        mItemClickListener = new MyOnItemClickListener();
        gridview.setOnItemClickListener(mItemClickListener);
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
        int id = v.getId();
        if (id == R.id.back_ib) {
            finish();

        }
    }

    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        private ArrayList<String> mPathList;

        void setPathList(ArrayList<String> pathList) {
            this.mPathList = pathList;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            intent.putExtra(CropPortraitActivity.IMAGE_PATH, mPathList.get(position));
            intent.setClass(getApplicationContext(), CropPortraitActivity.class);
            startActivityForResult(intent, CROP_PORTRAIT);
        }
    }


    class MyBaseAdapter extends BaseAdapter {

        private ArrayList<String> pathList;

        void setPathList(ArrayList<String> pathList) {
            this.pathList = pathList;
        }

        @Override
        public int getCount() {
            if (pathList == null) {
                return 0;
            }
            return pathList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_photo, null);
                holder = new ViewHolder();
                holder.imageView = convertView.findViewById(R.id.imageview);

                RelativeLayout.LayoutParams itemParams = (RelativeLayout.LayoutParams) holder.imageView.getLayoutParams();
                itemParams.height = (ScreenUtils.getScreenWidth(getApplicationContext()) - ScreenUtils.dip2px(getApplicationContext(), 8)) / 3;//
                holder.imageView.setLayoutParams(itemParams);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            File file = new File(pathList.get(position));

            GlideUtils.loadAsBitmap(getApplicationContext(), file, holder.imageView, 0, 0);
            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
        }
    }


    private void setImageData() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }


        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> pathList = getImagePathList();
                Message msg = new Message();

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("list", pathList);
                msg.setData(bundle);
                msg.what = SET_IMAGE_PATH;
                mHandler.sendMessage(msg);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CROP_PORTRAIT:
                setResult(resultCode, data);
                finish();
                break;
            default:
                break;
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
        private final SelectSinglePhotoActivity mActivity;

        MyHandler(SelectSinglePhotoActivity activity) {
            mActivity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SET_IMAGE_PATH:
                    Bundle bundle = msg.getData();
                    ArrayList<String> pathList = bundle.getStringArrayList("list");
                    mActivity.mAdapter.setPathList(pathList);
                    mActivity.mItemClickListener.setPathList(pathList);
                    mActivity.gridview.setVisibility(View.VISIBLE);
                    mActivity.progress.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }

        }
    }

}