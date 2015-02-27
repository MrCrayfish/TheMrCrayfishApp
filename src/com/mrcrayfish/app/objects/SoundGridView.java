package com.mrcrayfish.app.objects;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;

public class SoundGridView extends GridView
{
	public SoundGridView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public SoundGridView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public SoundGridView(Context context)
	{
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		ListAdapter adapter = getAdapter();

		if (getChildCount() > 0)
		{
			int numColumns = getWidth() / getChildAt(0).getMeasuredWidth();

			if (adapter != null)
			{
				for (int i = 0; i < getChildCount(); i += numColumns)
				{
					int maxHeight = 0;
					for (int j = i; j < i + numColumns; j++)
					{
						View view = getChildAt(j);
						if (view != null && view.getHeight() > maxHeight)
						{
							maxHeight = view.getHeight();
						}
					}

					if (maxHeight > 0)
					{
						for (int j = i; j < i + numColumns; j++)
						{
							View view = getChildAt(j);
							if (view != null && view.getHeight() != maxHeight)
							{
								view.setMinimumHeight(maxHeight);
							}
						}
					}
				}
			}
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}