package com.snowy.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;


public class ClickButton extends android.support.v7.widget.AppCompatButton {

    private Animator anim1;
    private Animator anim2;
    private int mHeight;
    private int mWidth;
    private float mX, mY;
    private Handler mHandler = new Handler();

    private ClickListener listener;

    public ClickButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        mWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        mX = getX();
        mY = getY();
    }

    private void init() {
        PropertyValuesHolder valueHolder1 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.9f);
        PropertyValuesHolder valuesHolder2 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.9f);
        anim1 = ObjectAnimator.ofPropertyValuesHolder(this, valueHolder1, valuesHolder2);
        anim1.setDuration(200);
        anim1.setInterpolator(new LinearInterpolator());

        PropertyValuesHolder valueHolder3 = PropertyValuesHolder.ofFloat("scaleX", 0.9f, 1f);
        PropertyValuesHolder valuesHolder4 = PropertyValuesHolder.ofFloat("scaleY", 0.9f, 1f);
        anim2 = ObjectAnimator.ofPropertyValuesHolder(this, valueHolder3, valuesHolder4);
        anim2.setDuration(200);
        anim2.setInterpolator(new LinearInterpolator());
    }

    public void setOnClickListener(ClickListener clickListener) {
        this.listener = clickListener;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        anim2.end();
                        anim1.start();
                    }
                });
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        anim1.end();
                        anim2.start();
                    }
                });
                if (listener != null) {
                    listener.onClick(this);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        anim1.end();
                        anim2.start();
                    }
                });
                break;
            default:
        }

        return true;
    }

    /**
     * 按下的点是否在View内
     */
    protected boolean innerImageView(float x, float y) {
        return x >= mX && y <= mX + mWidth && y >= mY && y <= mY + mHeight;
    }

    /**
     * 点击事件处理回调
     *
     * @author renzhiqiang
     */
    public interface ClickListener {
         void onClick(View view);
    }



}
