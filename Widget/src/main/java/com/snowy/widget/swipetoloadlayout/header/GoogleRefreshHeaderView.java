package com.snowy.widget.swipetoloadlayout.header;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.snowy.widget.R;
import com.snowy.widget.swipetoloadlayout.drawable.google.RingProgressDrawable;

/**
 * Created by aspsine on 15/9/10.
 */
public class GoogleRefreshHeaderView extends FrameLayout implements SwipeTrigger, SwipeRefreshTrigger {

    private int mTriggerOffset;

    private RingProgressDrawable ringProgressDrawable;

    public GoogleRefreshHeaderView(Context context) {
        this(context, null);
    }

    public GoogleRefreshHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoogleRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ringProgressDrawable = new RingProgressDrawable(context);
        Resources res = getResources();
        ringProgressDrawable.setColors(
                res.getColor(R.color.google_blue),
                res.getColor(R.color.google_red),
                res.getColor(R.color.google_yellow),
                res.getColor(R.color.google_green));
        mTriggerOffset = context.getResources().getDimensionPixelOffset(R.dimen.refresh_trigger_offset_google);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ImageView ivRefresh = findViewById(R.id.ivRefresh);
        ivRefresh.setBackground(ringProgressDrawable);
    }

    @Override
    public void onRefresh() {
        ringProgressDrawable.start();
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            ringProgressDrawable.setPercent(y / (float) mTriggerOffset);
        }
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        ringProgressDrawable.stop();
    }

    @Override
    public void onReset() {

    }
}
