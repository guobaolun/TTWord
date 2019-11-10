package com.snowy.ttword.listener;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.widget.ImageView;

import com.snowy.ttword.manager.MediaPlayerManager;

/**
 * 监听播放完成
 */
public class MyPlayerStateListener implements MediaPlayerManager.PlayerStateListener {

    private ImageView soundIv;
    private AnimationDrawable animationDrawable;

    private int playingResId;
    private int loadingResId;

    private int completionResId;


    public void setPlayingResId(int playingResId) {
        onCompletion();
        this.playingResId = playingResId;
    }


    public void setLoadingResId(int loadingResId) {
        this.loadingResId = loadingResId;
    }

    public void setCompletionResId(int completionResId) {
        onCompletion();
        this.completionResId = completionResId;
    }


    public void setImageView(ImageView soundIv) {
        onCompletion();
        this.soundIv = soundIv;
    }

    @Override
    public void onCompletion() {
        if (soundIv != null  &&completionResId != 0) {
            soundIv.setImageResource(completionResId);
            if (animationDrawable != null) {
                animationDrawable.stop();
                animationDrawable = null;
            }
        }
    }

    @Override
    public void onLoading() {
        if (soundIv != null && loadingResId != 0) {
            soundIv.setImageResource(loadingResId);
            animationDrawable = (AnimationDrawable) soundIv.getDrawable();
            animationDrawable.start();
        }
    }

    @Override
    public void onError(MediaPlayer mp, int what, int extra) {
        soundIv.setImageResource(completionResId);
        if (animationDrawable != null) {
            animationDrawable.stop();
            animationDrawable = null;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        soundIv.setImageResource(playingResId);
        animationDrawable = (AnimationDrawable) soundIv.getDrawable();
        animationDrawable.start();
    }


}