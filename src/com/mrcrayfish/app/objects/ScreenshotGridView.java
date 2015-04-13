package com.mrcrayfish.app.objects;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class ScreenshotGridView extends GridView
{
	public ScreenshotGridView(Context context)
	{
		super(context);
	}

	public ScreenshotGridView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public ScreenshotGridView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}
	
	

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
