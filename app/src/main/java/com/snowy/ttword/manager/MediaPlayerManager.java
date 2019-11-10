package com.snowy.ttword.manager;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.widget.Toast;

import java.io.IOException;

public class MediaPlayerManager {

    private static MediaPlayer mediaPlayer = new MediaPlayer();

    private static String playingURL;
    private static PlayerStateListener mStateListener;


    public static void play(Context context, String musicURL) {
        play(context, musicURL, null);
    }


    // 开始播放
    public static void play(Context context, String musicURL, PlayerStateListener listener) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        if (mStateListener != null) {
            mStateListener.onCompletion();
        }
        mStateListener = listener;


        mediaPlayer.setOnCompletionListener(mCompletionListener);
        mediaPlayer.setOnPreparedListener(mPreparedListener);
        mediaPlayer.setOnErrorListener(mErrorListener);

        // 判断URL是否与正在播放的url相等,如果相等就跳出方法
        if (musicURL.equals(playingURL) && mediaPlayer != null && mediaPlayer.isPlaying()) {
            return;
        }


        try {
            Uri uri = Uri.parse(musicURL);

            mediaPlayer.reset();
            mediaPlayer.setDataSource(context, uri);
            mediaPlayer.prepareAsync();// 准备播放
            playingURL = musicURL;
            if (mStateListener!= null){
                mStateListener.onLoading();
            }
        } catch (IOException e) {
            Toast.makeText(context,"播放失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static String getPlayingURL() {
        return playingURL;
    }


    /**
     * 停止播放并释放资源
     */
    public static void stop() {

        if (mStateListener != null) {
            mStateListener.onCompletion();
        }

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            playingURL = "";
        }

    }

    public static int getDuration() {
        return mediaPlayer == null ? 0 : mediaPlayer.getDuration();
    }

    public static int getCurrentPosition() {
        return mediaPlayer == null ? 0 : mediaPlayer.getCurrentPosition();
    }


    private static OnCompletionListener mCompletionListener = new OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            if (mStateListener != null) {
                mStateListener.onCompletion();
                stop();
            }
        }
    };

    private static MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if (mStateListener != null) {
                mStateListener.onPrepared(mp);
            }
            mediaPlayer.start(); // 开始播放
        }
    };


    private static MediaPlayer.OnErrorListener mErrorListener = new MediaPlayer.OnErrorListener() {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            if (mStateListener != null) {
                mStateListener.onError(mp, what, extra);
            }
            return false;
        }
    };


    public interface PlayerStateListener {
        void onCompletion();

        void onLoading();

        void onError(MediaPlayer mp, int what, int extra);

        void onPrepared(MediaPlayer mp);
    }
}
