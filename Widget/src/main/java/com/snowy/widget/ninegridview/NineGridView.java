package com.snowy.widget.ninegridview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.snowy.widget.R;

import java.util.List;

public class NineGridView extends ViewGroup {
    private static final int SINGLE_IMAGE_MODE_RELATIVE_TO_SELF = 1;//单图显示模式:按照原图比例计算高度
    private final int SINGLE_IMAGE_MODE_SPECIFIED_RATIO = 2;//单图显示模式:指定宽高比例
    private final float DEFAULT_WIDTH_RELATIVE_TO_PARENT = 2 / 2.0f;//单张图片相对总可用宽度的比例
    private float widthRatioToParent = DEFAULT_WIDTH_RELATIVE_TO_PARENT;
    private int singleImageMode = SINGLE_IMAGE_MODE_SPECIFIED_RATIO;

    private static final int DEFAULT_SPACING = 2;
//    private static final int MAX_COUNT = 9;
    private int space = 0;
    private int gridWidth = 0;
    private int grideHeight = 0;
    private List<String> urls = null;
    private Context context;
    private int rows;
    private int colums;
    private float singleImageHeightRatio = 1;//单张图片相对自己宽度的比例(默认为1:和宽度相等)
    public interface ImageCreator {
        ImageView createImageView(Context context);

        void loadImage(Context context, String url, ImageView imageView);
    }

    public interface OnItemClickListener{
        void onClickItem(int position, View view);
    }

    private ImageCreator imageCreator;
    private OnItemClickListener onItemClickListener;

    public NineGridView(Context context) {
        super(context);
        initView(context, null);
    }

    public NineGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public NineGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NineGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setImageCreator(ImageCreator imageCreator) {
        this.imageCreator = imageCreator;
    }

    private void initView(Context context, AttributeSet attrs) {
        this.context = context;
        int defaultSpace = dp2px(context, DEFAULT_SPACING);
        @SuppressLint("CustomViewStyleable") TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LGNineGrideView);
        space = (int) typedArray.getDimension(R.styleable.LGNineGrideView_space, defaultSpace);
        singleImageHeightRatio = typedArray.getFloat(R.styleable.LGNineGrideView_singleImageRatio,1);
        singleImageMode = typedArray.getInteger(R.styleable.LGNineGrideView_singleImageRatioMode, SINGLE_IMAGE_MODE_SPECIFIED_RATIO);
        widthRatioToParent = typedArray.getFloat(R.styleable.LGNineGrideView_singleImageWidthRatioToParent, DEFAULT_WIDTH_RELATIVE_TO_PARENT);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

        if (urls == null) {
            setVisibility(GONE);
            return;
        }
        int size = urls.size();
        int sugMinWidth = getSuggestedMinimumWidth();
        int minWidth = getPaddingLeft() + getPaddingRight() + sugMinWidth;
        int totalWidth = resolveSizeAndState(minWidth, widthMeasureSpec, 0);
        int availableWidth = totalWidth - getPaddingLeft() - getPaddingRight();
        if (size == 1) {
            gridWidth = (int)(availableWidth * widthRatioToParent);

            grideHeight = (int)(gridWidth * singleImageHeightRatio);
            if(singleImageMode == SINGLE_IMAGE_MODE_RELATIVE_TO_SELF){
                ImageView view = (ImageView) getChildAt(0);
                if(view != null){
                    Rect rect = view.getDrawable().getBounds();
                    int bitmapWidth = rect.width();
                    int bitmapHeight = rect.height();
                    gridWidth = bitmapWidth;
                    grideHeight = bitmapHeight;
                    if(gridWidth >= totalWidth){
                        gridWidth = totalWidth;
                        grideHeight = gridWidth * bitmapHeight / bitmapWidth;
                    }
                }
            }
        } else {
            gridWidth = (availableWidth - space * (colums - 1)) / 3;
            //noinspection SuspiciousNameCombination
            grideHeight = gridWidth;
        }

        int height = rows * grideHeight + (rows - 1) * space + getPaddingTop() + getPaddingBottom();


        setMeasuredDimension(totalWidth, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int size = urls.size();
        if (size == 0)
            return;
        int tmpWidth = gridWidth;
        int tmpHeight = grideHeight;

        int childSize = getChildCount();

        for (int i = 0; i < size; ++i) {
            ImageView view = (ImageView) getChildAt(i);
            view.setVisibility(VISIBLE);
            l = i % colums * (tmpWidth + space) + getPaddingLeft();
            t = i / colums * (tmpHeight + space) + getPaddingTop();
            r = l + tmpWidth;
            b = t + tmpHeight;
            view.layout(l, t, r, b);
        }

        if (size < childSize) {
            for (int i = size; i < childSize; ++i) {
                ImageView view = (ImageView) getChildAt(i);
                view.setVisibility(GONE);
            }
        }
    }

    private void initRowAndColum(int size) {
        rows = (size - 1) / 3 + 1;
        colums = (size - 1) % 3 + 1;
        if (size == 4) {
            rows = 2;
            colums = 2;
        } else {
            colums = 3;
        }
    }

    public void setUrls(List<String> urls) {
        if (urls == null || urls.isEmpty()) {
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);
        if (this.urls == urls) {
            return;
        }
        this.urls.clear();
        this.urls.addAll(urls);
        initRowAndColum(urls.size());
        if (imageCreator == null) {
            try {
                throw new Exception("imageCreator is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < urls.size(); ++i) {
            String url = urls.get(i);
            ImageView view = (ImageView) getChildAt(i);

            if (view == null) {
                final int position = i;
                view = imageCreator.createImageView(context);
                addView(view);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onItemClickListener != null){
                            onItemClickListener.onClickItem(position,v);
                        }
                    }
                });
            }


            imageCreator.loadImage(context, url, view);
        }
        requestLayout();
    }



    /**
     * dp转px
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }
}
