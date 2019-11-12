package com.snowy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DimenRes;
import androidx.annotation.Nullable;

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
    private int arrowSrcColor;
    private int arrowVisibility;
    private int topLineHeight;
    private int topLineWidth;
    private int topLineColor;
    private int topLineMarginStart;
    private int topLineMarginEnd;
    private int bottomLineHeight;
    private int bottomLineWidth;
    private int bottomLineColor;
    private int bottomLineMarginStart;
    private int bottomLineMarginEnd;
    private int height;

    public ItemView(Context context) {
        this(context, null);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //
    public ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.widget_item_view, this);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.album);
//            int width = a.getLayoutDimension(R.styleable.album_android_layout_width, -1);
        height = a.getLayoutDimension(R.styleable.album_android_layout_height, -2);
        a.recycle();


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemView, defStyleAttr, 0);

        iconWidth = typedArray.getLayoutDimension(R.styleable.ItemView_itemView_icon_width, LayoutParams.WRAP_CONTENT);
        iconHeight = typedArray.getLayoutDimension(R.styleable.ItemView_itemView_icon_height, LayoutParams.WRAP_CONTENT);
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

        textViewMarginStart = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_textView_marginStart, getDimensionPixelSize(R.dimen.item_text_margin_start));
        textViewWidth = typedArray.getLayoutDimension(R.styleable.ItemView_itemView_textView_width, LayoutParams.WRAP_CONTENT);
        textViewHeight = typedArray.getLayoutDimension(R.styleable.ItemView_itemView_textView_height, LayoutParams.WRAP_CONTENT);


        arrowHeight = typedArray.getLayoutDimension(R.styleable.ItemView_itemView_arrow_height, LayoutParams.WRAP_CONTENT);
        arrowWidth = typedArray.getLayoutDimension(R.styleable.ItemView_itemView_arrow_width, LayoutParams.WRAP_CONTENT);
        arrowMarginEnd = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_arrow_marginEnd, getDimensionPixelSize(R.dimen.item_margin_left_and_right));
        arrowText = typedArray.getString(R.styleable.ItemView_itemView_arrow_text);
        arrowTextColor = typedArray.getColor(R.styleable.ItemView_itemView_arrow_textColor, Color.BLACK);
        arrowTextSize = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_arrow_textSize, getDimensionPixelSize(R.dimen.item_view_default_text_size));
        arrowSrc = typedArray.getDrawable(R.styleable.ItemView_itemView_arrow_src);
        arrowSrcColor = typedArray.getColor(R.styleable.ItemView_itemView_arrow_srcColor, Color.GRAY);
        arrowVisibility = typedArray.getInt(R.styleable.ItemView_itemView_arrow_visibility, VISIBLE);


        topLineHeight = typedArray.getLayoutDimension(R.styleable.ItemView_itemView_topLine_height, getDimensionPixelSize(R.dimen.item_view_default_line_height));
        topLineWidth = typedArray.getLayoutDimension(R.styleable.ItemView_itemView_topLine_width, LayoutParams.MATCH_PARENT);
        topLineColor = typedArray.getColor(R.styleable.ItemView_itemView_topLine_color, Color.TRANSPARENT);
        topLineMarginStart = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_topLine_marginStart, 0);
        topLineMarginEnd = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_topLine_marginEnd, 0);


        bottomLineHeight = typedArray.getLayoutDimension(R.styleable.ItemView_itemView_bottomLine_height, getDimensionPixelSize(R.dimen.item_view_default_line_height));
        bottomLineWidth = typedArray.getLayoutDimension(R.styleable.ItemView_itemView_bottomLine_width, LayoutParams.MATCH_PARENT);
        bottomLineColor = typedArray.getColor(R.styleable.ItemView_itemView_bottomLine_color, Color.TRANSPARENT);
        bottomLineMarginStart = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_bottomLine_marginStart, 0);
        bottomLineMarginEnd = typedArray.getDimensionPixelSize(R.styleable.ItemView_itemView_bottomLine_marginEnd, 0);


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


        Drawable drawable = getResources().getDrawable(R.drawable.ic_fore);
        drawable.setColorFilter(new PorterDuffColorFilter(arrowSrcColor, PorterDuff.Mode.SRC_IN));


        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        arrowTv.setCompoundDrawables(null, null, drawable, null);

        if (arrowSrc != null) {
            arrowSrc.setBounds(0, 0, arrowSrc.getMinimumWidth(), arrowSrc.getMinimumHeight());
            arrowTv.setCompoundDrawables(null, null, arrowSrc, null);
        }

        RelativeLayout itemRl = findViewById(R.id.item_rl);
        LinearLayout.LayoutParams itemRlParams = (LinearLayout.LayoutParams) itemRl.getLayoutParams();
        if (height == LayoutParams.MATCH_PARENT || height == LayoutParams.WRAP_CONTENT) {
            itemRlParams.height = height;
        } else {
            itemRlParams.height = height - topLineHeight - bottomLineHeight;
        }
        itemRlParams.setMargins(0, getDimensionPixelSize(R.dimen.item_view_default_margin), 0, getDimensionPixelSize(R.dimen.item_view_default_margin));
        itemRl.setLayoutParams(itemRlParams);

        View topLine = findViewById(R.id.top_line);
        topLine.setBackgroundColor(topLineColor);
        LinearLayout.LayoutParams topLineParams = (LinearLayout.LayoutParams) topLine.getLayoutParams();
        topLineParams.height = topLineHeight;
        topLineParams.width = topLineWidth;
        topLineParams.setMarginStart(topLineMarginStart);
        topLineParams.setMarginEnd(topLineMarginEnd);
        topLine.setLayoutParams(topLineParams);

        View bottomLine = findViewById(R.id.bottom_line);
        bottomLine.setBackgroundColor(bottomLineColor);
        LinearLayout.LayoutParams bottomLineParams = (LinearLayout.LayoutParams) bottomLine.getLayoutParams();
        bottomLineParams.height = bottomLineHeight;
        bottomLineParams.width = bottomLineWidth;

        if (height == LayoutParams.MATCH_PARENT) {
            bottomLineParams.setMargins(0, -(bottomLineHeight + topLineHeight), 0, 0);
        }
        bottomLineParams.setMarginStart(bottomLineMarginStart);
        bottomLineParams.setMarginEnd(bottomLineMarginEnd);
        bottomLine.setLayoutParams(bottomLineParams);
        setClickable(true);
        setFocusable(true);
    }

    private int getDimensionPixelSize(@DimenRes int id) {
        return getResources().getDimensionPixelSize(id);
    }


}
