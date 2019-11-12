package com.snowy.ttword.adapter;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.snowy.ttword.R;

/**
 * @author guobaolun
 * @since 2018/5/3
 */
public class FirstLoaderViewHolder extends RecyclerView.ViewHolder {

    public ImageView loadingIv;
    public TextView enptyTv;
    public  Button reloadBt;
    private AnimationDrawable animationDrawable;


    public FirstLoaderViewHolder(View view, final LoaderListener listener) {
        super(view);
        loadingIv = view.findViewById(R.id.loading_iv);
        enptyTv = view.findViewById(R.id.enpty_tv);
        reloadBt = view.findViewById(R.id.reload_bt);

        reloadBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.loadData();
                reloadBt.invalidate();
            }
        });

        loadingIv.setImageResource(R.drawable.anim_loading);
        animationDrawable = (AnimationDrawable) loadingIv.getDrawable();
        animationDrawable.start();
    }


   public interface LoaderListener {
        void loadData();
    }
}
