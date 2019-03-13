package com.snowy.widget.ninegridview;

import android.content.Context;
import android.widget.ImageView;


class DefaultImageCreator implements NineGridView.ImageCreator {

    private static DefaultImageCreator defaultImageCreator;

    private DefaultImageCreator(){
    }

//    public static DefaultImageCreator getInstance(){
//        if(defaultImageCreator == null){
//            synchronized (GlideUtils.class){
//                if(defaultImageCreator == null)
//                    defaultImageCreator = new DefaultImageCreator();
//            }
//        }
//        return defaultImageCreator;
//    }

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void loadImage(Context context, String url, ImageView imageView) {
//        GlideUtils.loadAsBitmap(context,url,imageView,R.color.gray, R.color.gray);
    }


}
