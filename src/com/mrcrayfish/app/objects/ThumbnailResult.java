package com.mrcrayfish.app.objects;

import android.graphics.Bitmap;

public class ThumbnailResult
{
	private int code;
	private Bitmap bitmap;

	public ThumbnailResult(int code, Bitmap bitmap)
	{
		this.code = code;
		this.bitmap = bitmap;
	}

	public int getCode()
	{
		return code;
	}

	public Bitmap getBitmap()
	{
		return bitmap;
	}
}
