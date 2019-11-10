package com.snowy.ttword.listener;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.snowy.ttword.R;
import com.snowy.ttword.manager.MediaPlayerManager;

/**
 * @author guobaolun
 * @since 2018/4/25
 */
public class CommentVoiceClickListener implements View.OnClickListener {

    private MyPlayerStateListener stateListener;

    private Context context;

    public CommentVoiceClickListener(Context context) {

        this.context = context;
        stateListener = new MyPlayerStateListener();
    }

    private String url;
    private ImageView iconView;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setVoiceIconView(ImageView iconView) {
        this.iconView = iconView;
    }

    @Override
    public void onClick(View v) {
        if (url.equals(MediaPlayerManager.getPlayingURL())) {
            MediaPlayerManager.stop();
        } else {
            stateListener.setImageView(iconView);
            stateListener.setLoadingResId(R.drawable.anim_loading);
            stateListener.setPlayingResId(R.drawable.anim_voice);
            stateListener.setCompletionResId(R.drawable.voice_3);
            MediaPlayerManager.play(context, url, stateListener);

        }
    }
}
