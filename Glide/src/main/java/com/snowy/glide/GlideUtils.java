package com.snowy.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.snowy.glide.progress.OnProgressListener;
import com.snowy.glide.progress.ProgressManager;


public class GlideUtils {


    public static void loadImage(Context context, final Object url, final ImageView imageView, int placeholderId, int errorId) {

//        Glide.with(context)
//                .load(url)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })
//                .apply(getRequestOptions(placeholderId, errorId))
//                .into(imageView);


    }

    public static void loadAsBitmap(Context context, final Object url, ImageView imageView, int placeholderId, int errorId) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .apply(getRequestOptions(placeholderId, errorId))
                .into(imageView);
    }


    public static void loadProgressImage(Context context, final Object url, Object thumbnailUrl, final ImageView imageView, int placeholderId, int errorId, final OnProgressListener listener) {
        ProgressManager.addProgressListener(url, listener);

        Glide.with(context)
                .load(url)
                .apply(getRequestOptions(placeholderId, errorId))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ProgressManager.removeProgressListener(listener);
                        listener.onLoadFailed(e, model, target, isFirstResource);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        ProgressManager.removeProgressListener(listener);
                        listener.onResourceReady(resource, model, target, dataSource, isFirstResource);

                        return false;
                    }
                }).thumbnail(Glide.with(context) // 加载缩略图
                .load(thumbnailUrl)
                .apply(getRequestOptions(placeholderId, errorId)))
                .transition(DrawableTransitionOptions.withCrossFade()) // 动画渐变加载
                .into(imageView);


    }


    private static RequestOptions getRequestOptions(int placeholderId, int errorId) {
        RequestOptions options = new RequestOptions();
        options.centerInside()
                .placeholder(placeholderId)//等待加载
//                .priority( Priority.HIGH )       //图片请求的优先级
                .error(errorId); //加载出错
        return options;
    }


}
