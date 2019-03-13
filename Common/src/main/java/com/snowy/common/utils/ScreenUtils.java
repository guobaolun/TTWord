package com.snowy.common.utils;

import android.content.Context;
import android.util.TypedValue;

import java.lang.reflect.Field;

public class ScreenUtils {

	/**
	 * 获取状态栏高度
     */
	public static int getStatusBarHeight(Context context) {
		int sbar = 0;
		try {
			Class<?> c = Class.forName("com.android.internal.R$dimen");
			Object obj = c.newInstance();
			Field field = c.getField("status_bar_height");
			int x = Integer.parseInt(field.get(obj).toString());
			sbar = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sbar;
	}


	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = getScreenDensity(context);
		return (int) (pxValue / scale);
	}

	/**
	 * 把密度转换为像素
	 */
	public static int dip2px(Context context, float dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp, context.getResources().getDisplayMetrics());
	}


	/**
	 * 将sp值转换为px值
	 */
	public static int sp2px(Context context, float spValue) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
	}


	/**
	 * 得到设备屏幕的高度
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 得到设备屏幕的宽度
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 得到设备的密度	
	 */
	private static float getScreenDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}


}
