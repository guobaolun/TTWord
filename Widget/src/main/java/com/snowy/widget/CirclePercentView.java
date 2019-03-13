package com.snowy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


/**
 * 一个圆形百分比进度 View
 * 用于展示简易的图标
 * Created by Administrator on 2015/12/16.
 */
public class CirclePercentView extends View {

    //圆的半径
    private float mRadius;

    //色带的宽度
    private float mStripeWidth;
    //总体大小
    private int mHeight;
    private int mWidth;

    //动画位置百分比进度
    private int mCurPercent;

    //圆心坐标
    private float x;
    private float y;

    //小圆的颜色
    private int mSmallColor;
    //大圆颜色
    private int mBigColor;

    //中心百分比文字大小
    private float mCenterTextSize;

    public CirclePercentView(Context context) {
        this(context, null);
    }

    public CirclePercentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePercentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);



        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CirclePercentView, defStyleAttr, 0);
        try {
            mStripeWidth = typedArray.getDimension(R.styleable.CirclePercentView_stripeWidth, dpToPx(30, context));
            mCurPercent = typedArray.getInteger(R.styleable.CirclePercentView_percent, 0);
            mSmallColor = typedArray.getColor(R.styleable.CirclePercentView_smallColor, 0xffafb4db);
            mBigColor = typedArray.getColor(R.styleable.CirclePercentView_bigColor, 0xff6950a1);
            mCenterTextSize = typedArray.getDimensionPixelSize(R.styleable.CirclePercentView_centerTextSize, spToPx(20, context));
            mRadius = typedArray.getDimensionPixelSize(R.styleable.CirclePercentView_pradius, dpToPx(30, context));
        }finally {
            typedArray.recycle();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取测量模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取测量大小
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            mRadius = widthSize >> 1;
            x = widthSize >> 1;
            y = heightSize >> 1;
            mWidth = widthSize;
            mHeight = heightSize;
        }

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            mWidth = (int) (mRadius * 2);
            mHeight = (int) (mRadius * 2);
            x = mRadius;
            y = mRadius;

        }

        setMeasuredDimension(mWidth, mHeight);
    }

    Paint bigCirclePaint = new Paint();
    Paint sectorPaint = new Paint();
    RectF rect = new RectF(0, 0, mWidth, mHeight);
    Paint smallCirclePaint = new Paint();
    Paint mPaint = new Paint();
    Rect bounds = new Rect();

    @Override
    protected void onDraw(Canvas canvas) {
        //要画的弧度
        int mEndAngle = (int) (mCurPercent * 3.6);
        //绘制大圆
        bigCirclePaint.setAntiAlias(true);
        bigCirclePaint.setColor(mBigColor);
        canvas.drawCircle(x, y, mRadius, bigCirclePaint);
        //饼状图
        sectorPaint.setColor(mSmallColor);
        sectorPaint.setAntiAlias(true);
        rect.right = mWidth;
        rect.bottom = mHeight;

        canvas.drawArc(rect, 270, mEndAngle, true, sectorPaint);

        //绘制小圆,颜色透明
        smallCirclePaint.setAntiAlias(true);
        smallCirclePaint.setColor(mBigColor);
        canvas.drawCircle(x, y, mRadius - mStripeWidth, smallCirclePaint);

        //绘制文本
        String text = mCurPercent + "%";

        mPaint.setStrokeWidth(3);
        mPaint.setTextSize(mCenterTextSize);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(text, (getMeasuredWidth() >> 1) - (bounds.width() >> 1), baseline, mPaint);


    }

    //外部设置百分比数
    public void setPercent(int percent) {
        if (percent > 100) {
            throw new IllegalArgumentException("percent must less than 100!");
        }
        setCurPercent(percent);
    }

    //内部设置百分比 用于动画效果
    private void setCurPercent(int percent) {

        mCurPercent = percent;
        invalidate();
    }

    public static int dpToPx(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int spToPx(int sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }


}
