package com.mrcrayfish.app.objects;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class LinkListView extends ListView
{
	public LinkListView(Context context)
	{
		super(context);
	}

	public LinkListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public LinkListView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
