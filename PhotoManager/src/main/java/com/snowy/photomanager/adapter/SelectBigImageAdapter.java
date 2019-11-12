package com.snowy.photomanager.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.snowy.common.utils.ScreenUtils;
import com.snowy.glide.GlideUtils;
import com.snowy.photomanager.R;
import com.snowy.photomanager.entity.SelectImage;

import java.util.ArrayList;

public class SelectBigImageAdapter extends PagerAdapter {
    private Context context;

    private ArrayList<SelectImage> imageList;
    private ArrayList<String> selectList;

    public SelectBigImageAdapter(Context context) {
        this.context = context;
    }


    public void setImageList(ArrayList<SelectImage> imageList, ArrayList<String> selectList) {
        this.imageList = imageList;
        if (selectList != null) {
            this.selectList = selectList;

        } else {
            this.selectList = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        if (imageList == null) {
            return 0;
        }
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.item_big_image, null);
        ImageView imageView = view.findViewById(R.id.imageview);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        lp.height = ScreenUtils.getScreenHeight(context);
        lp.width = ScreenUtils.getScreenWidth(context);
        imageView.setLayoutParams(lp);

        GlideUtils.loadAsBitmap(context, imageList.get(position % imageList.size()).getPath(), imageView, 0, 0);
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}