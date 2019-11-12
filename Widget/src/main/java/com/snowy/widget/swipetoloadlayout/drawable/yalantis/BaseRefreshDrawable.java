package com.snowy.widget.swipetoloadlayout.drawable.yalantis;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

/**
 * Copy from https://github.com/Yalantis/Phoenix
 * Aspsine makes some changes
 */
public abstract class BaseRefreshDrawable extends Drawable implements Drawable.Callback, Animatable {

    private Context mContext;

    private Handler mHandler;

    BaseRefreshDrawable(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public abstract void setPercent(float percent, boolean invalidate);

    public abstract void offsetTopAndBottom(int offset);

    @Override
    public void invalidateDrawable(@NonNull Drawable who) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override
    public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, what, when);
        }
    }

    @Override
    public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, what);
        }
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }


    protected void post(Runnable runnable) {
        postDelayed(runnable);
    }

    private void postDelayed(Runnable runnable) {
        if (mHandler == null) {
            synchronized (BaseRefreshDrawable.class) {
                mHandler = new Handler(Looper.getMainLooper());
            }
        }
        mHandler.postDelayed(runnable, 0);

    }
}
