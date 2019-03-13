package com.snowy.widget;

import android.widget.ListView;

public class FollowListView extends ListView {
	
	
	public FollowListView(android.content.Context context,
			android.util.AttributeSet attrs) {
		super(context, attrs);
	}

	
	/**
	 * 设置不滚动
	 */
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);  
	    super.onMeasure(widthMeasureSpec, expandSpec);  
				  

	}
}
