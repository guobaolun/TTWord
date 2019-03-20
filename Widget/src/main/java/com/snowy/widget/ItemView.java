package com.snowy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkcool.circletextimageview.CircleTextImageView;


/**
 * @author guobaolun
 */

public class ItemView extends LinearLayout {

    private String iconText;
    private int iconHeight;
    private int iconWidth;
    private int iconMarginStart;
    private int iconTextColor;
    private int iconTextSize;
    private int iconBackground;
    private Drawable iconSrc;
    private int iconVisibility;
    private int iconBorderColor;
    private int iconBorderWidth;
    private int textViewTextSize;
    private int textViewColor;
    private String textViewText;
    private int textViewMarginStart;
    private int textViewWidth;
    private int textViewHeight;
    private int arrowHeight;
    private int arrowWidth;
    private int arrowMarginEnd;
    private String arrowText;
    private int arrowTextColor;
    private int arrowTextSize;
    private Drawable arrowSrc;
    private int arrowVisibility;

    public ItemView(Context context) {
        this(context, null);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.widget_item_view, this);


//    <com.thinkcool.circletextimageview.CircleTextImageView
//		android:id="@+id/icon_view"
//		android:layout_width="22dp"
//		android:layout_height="22dp"
//		android:layout_centerVertical="true"
//		android:layout_marginStart="@dimen/item_margin_left_and_right"
//		android:gravity="center"
//				/>


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemView, defStyleAttr, 0);
        iconWidth = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_icon_width, LayoutParams.WRAP_CONTENT);
        iconHeight = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_icon_height, LayoutParams.WRAP_CONTENT);
        iconMarginStart = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_icon_marginStart, getDimensionPixelSize(R.dimen.item_margin_left_and_right));
        iconText = typedArray.getString(R.styleable.ItemView_itemView_icon_text);
        iconTextColor = typedArray.getColor(R.styleable.ItemView_itemView_icon_textColor, Color.BLACK);
        iconTextSize = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_icon_textSize, getDimensionPixelSize(R.dimen.item_view_default_text_size));
        iconBackground = typedArray.getColor(R.styleable.ItemView_itemView_icon_background, Color.TRANSPARENT);
        iconSrc = typedArray.getDrawable(R.styleable.ItemView_itemView_icon_src);
        iconBorderWidth = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_icon_border_width, 0);
        iconBorderColor = typedArray.getColor(R.styleable.ItemView_itemView_icon_border_color, Color.BLACK);
        iconVisibility = typedArray.getInt(R.styleable.ItemView_itemView_icon_visibility, VISIBLE);


        textViewTextSize = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_textView_textSize, getDimensionPixelSize(R.dimen.item_view_default_text_size));
        textViewColor = typedArray.getColor(R.styleable.ItemView_itemView_textView_textColor, Color.BLACK);
        textViewText = typedArray.getString(R.styleable.ItemView_itemView_textView_text);

        textViewMarginStart = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_textView_marginStart,getDimensionPixelSize(R.dimen.item_text_margin_start));
        textViewWidth = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_textView_width, LayoutParams.WRAP_CONTENT);
        textViewHeight = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_textView_height, LayoutParams.WRAP_CONTENT);


        arrowHeight = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_arrow_height, LayoutParams.WRAP_CONTENT);
        arrowWidth = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_arrow_width, LayoutParams.WRAP_CONTENT);
        arrowMarginEnd = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_arrow_marginEnd,getDimensionPixelSize(R.dimen.item_margin_left_and_right));
        arrowText = typedArray.getString(R.styleable.ItemView_itemView_arrow_text);
        arrowTextColor = typedArray.getColor(R.styleable.ItemView_itemView_arrow_textColor, Color.BLACK);
        arrowTextSize = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_arrow_textSize,getDimensionPixelSize(R.dimen.item_view_default_text_size));
        arrowSrc = typedArray.getDrawable(R.styleable.ItemView_itemView_arrow_src);
        arrowVisibility = typedArray.getInt(R.styleable.ItemView_itemView_arrow_visibility, VISIBLE);

        typedArray.recycle();
        initView();
    }

    private void initView() {

        CircleTextImageView iconView = findViewById(R.id.icon_view);
        RelativeLayout.LayoutParams iconViewParams = (RelativeLayout.LayoutParams) iconView.getLayoutParams();
        iconViewParams.height = iconHeight;
        iconViewParams.width = iconWidth;
        iconViewParams.setMarginStart(iconMarginStart);
        iconView.setLayoutParams(iconViewParams);
        iconView.setText(iconText);
        iconView.setTextColor(iconTextColor);
//        iconView.setTextSize(TypedValue.COMPLEX_UNIT_PX, iconTextSize);
        iconView.setTextSize(iconTextSize);
        iconView.setBackgroundColor(iconBackground);
        iconView.setImageDrawable(iconSrc);
        iconView.setVisibility(iconVisibility);
        iconView.setBorderColor(iconBorderColor);
        iconView.setBorderWidth(iconBorderWidth);

        TextView textTv = findViewById(R.id.text_tv);
        RelativeLayout.LayoutParams textTvParams = (RelativeLayout.LayoutParams) textTv.getLayoutParams();
        textTvParams.height = textViewHeight;
        textTvParams.width = textViewWidth;
        System.out.println("================"+textViewMarginStart);
        textTvParams.setMarginStart(textViewMarginStart);
        textTv.setLayoutParams(textTvParams);

        textTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textViewTextSize);






        textTv.setTextColor(textViewColor);
        textTv.setText(textViewText);

        TextView arrowTv = findViewById(R.id.arrow_tv);
        RelativeLayout.LayoutParams arrowParams = (RelativeLayout.LayoutParams) arrowTv.getLayoutParams();
        arrowParams.height = arrowHeight;
        arrowParams.width = arrowWidth;
        arrowParams.setMarginStart(arrowMarginEnd);
        arrowTv.setLayoutParams(arrowParams);
        arrowTv.setText(arrowText);
        arrowTv.setTextColor(arrowTextColor);
        arrowTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, arrowTextSize);
        arrowTv.setVisibility(arrowVisibility);

        if (arrowSrc != null){
            arrowSrc.setBounds(0, 0, arrowSrc.getMinimumWidth(), arrowSrc.getMinimumHeight());
            arrowTv.setCompoundDrawables(null, null, arrowSrc, null);
        }


    }

    private int getDimensionPixelSize(@DimenRes int id){
        return getResources().getDimensionPixelSize(id);
    }


}
