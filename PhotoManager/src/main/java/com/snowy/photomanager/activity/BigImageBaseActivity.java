package com.snowy.photomanager.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.target.Target;
import com.snowy.common.activity.BaseActivity;
import com.snowy.common.utils.ScreenUtils;
import com.snowy.glide.GlideUtils;
import com.snowy.glide.progress.OnProgressListener;
import com.snowy.photomanager.PhotoConstants;
import com.snowy.photomanager.R;
import com.snowy.photomanager.entity.ImageLocationInfo;
import com.snowy.widget.CircleProgressView;
import com.snowy.widget.TouchImageView;

import java.util.ArrayList;

/**
 * @author guobaolun
 */
public class BigImageBaseActivity extends BaseActivity {


    public ViewPager mViewPager;
    public TextView mTextView;
    public MyPagerAdapter mPagerAdapter;

    public ArrayList<ImageLocationInfo> mImageInfoList;
    public int mPosition;

    public boolean isFirstIn = true;
    public RelativeLayout topRl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mImageInfoList = (ArrayList<ImageLocationInfo>) intent.getSerializableExtra(PhotoConstants.IMAGE_INFO_LIST);
        mPosition = intent.getIntExtra(PhotoConstants.POSITION, 0);


        if (mImageInfoList.size() == 1) {
            mTextView.setVisibility(View.GONE);
        }
        initData();
    }

    private void initData() {
        mTextView.setText(String.valueOf((mPosition + 1) + "/" + mImageInfoList.size()));

        mPagerAdapter = new MyPagerAdapter();
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(mPosition);
        mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }


    @Override
    protected int onContentView() {
        return R.layout.activity_base_big_image;
    }


    @Override
    protected void initView() {
        topRl = findViewById(R.id.top_rl);
        mTextView = findViewById(R.id.textview);
        mViewPager = findViewById(R.id.viewpager);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(0, 0);
        }
    }


    @Override
    public void onBackPressed() {
        finishImageView();
    }


    public void finishImageView() {

        View view = mPagerAdapter.getPrimaryItem();
        TouchImageView imageView = view.findViewById(R.id.imageview);
        if (imageView != null) {
            if (imageView.getDrawable().getIntrinsicHeight() == -1) {
                finish();
            } else {
                imageView.setOnTransformListener(new TouchImageView.TransformListener() {
                    @Override
                    public void onTransformComplete(int mode) {
                        if (mode == TouchImageView.STATE_TRANSFORM_OUT) {
                            finish();
                        }
                    }
                });
                imageView.transformOut();
            }
        } else {
            finish();
        }
    }


    public class MyPagerAdapter extends PagerAdapter {

        private View mCurrentView;

        @Override
        public int getCount() {
            if (mImageInfoList == null) {
                return 0;
            }
            return mImageInfoList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }


        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            mCurrentView = (View) object;
        }

        View getPrimaryItem() {
            return mCurrentView;
        }

        private int mChildCount = 0;

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            if (mChildCount > 0) {
                mChildCount--;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            View view = View.inflate(BigImageBaseActivity.this, R.layout.item_big_image, null);
            final TouchImageView imageView = view.findViewById(R.id.imageview);

            CircleProgressView progressView = view.findViewById(R.id.progressView);
            progressView.setVisibility(View.VISIBLE);

            ImageLocationInfo imageLocationInfo = mImageInfoList.get(position % mImageInfoList.size());

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.height = ScreenUtils.getScreenHeight(getApplicationContext());
            lp.width = ScreenUtils.getScreenWidth(getApplicationContext());
            imageView.setLayoutParams(lp);

            imageView.setOriginalInfo(imageLocationInfo.getWidth(), imageLocationInfo.getHeight(), imageLocationInfo.getLocationX(), imageLocationInfo.getLocationY());
            if (isFirstIn) {
                imageView.transformIn();
                isFirstIn = false;
            }
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageView.getDrawable().getIntrinsicHeight() == -1) {
                        finish();
                    } else {
                        finishImageView();
                    }
                }
            });

            String imageUrl = imageLocationInfo.getImageUrl();
            String thumbnailUrl = imageLocationInfo.getImageThumbnailUrl();

            GlideUtils.loadProgressImage(getApplicationContext(), imageUrl, thumbnailUrl, imageView, android.R.color.black, 0, new ImageProgressListener(progressView));
//            final Object url, Object thumbnailUrl,
            container.addView(view);


            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mTextView.setText(String.valueOf((position + 1) + "/" + mImageInfoList.size()));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    class ImageProgressListener implements OnProgressListener {

        private CircleProgressView progressView;

        ImageProgressListener(CircleProgressView progressView) {
            this.progressView = progressView;
        }

        @Override
        public void onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            progressView.setVisibility(View.GONE);
        }

        @Override
        public void onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            progressView.setVisibility(View.GONE);
        }

        @Override
        public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, GlideException exception) {
            int percent = (int) (bytesRead * 100 / totalBytes);
            progressView.setProgress(percent);
            progressView.setVisibility(View.VISIBLE);

        }
    }


}
