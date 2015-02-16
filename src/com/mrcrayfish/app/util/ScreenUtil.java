package com.mrcrayfish.app.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class ScreenUtil
{
	public static int toPixels(Context context, float dip)
	{
		Resources r = context.getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
	}
}
