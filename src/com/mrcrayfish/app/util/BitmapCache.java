package com.mrcrayfish.app.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class BitmapCache
{
	public static Bitmap getCachedBitmap(String video_id)
	{
		String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TheMrCrayfishApp/thumbnail_cache";
		File dir = new File(path);
		if (!dir.exists())
		{
			dir.mkdirs();
		}
		File file = new File(dir, video_id);
		Bitmap bitmap = null;
		if (file.exists() && file.isFile())
		{
			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		}
		path = null;
		dir = null;
		file = null;
		return bitmap;
	}

	public static void saveBitmapToCache(String video_id, Bitmap image)
	{
		String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TheMrCrayfishApp/thumbnail_cache";
		File dir = new File(path);
		if (!dir.exists())
		{
			dir.mkdirs();
		}
		File file = new File(dir, video_id);
		try
		{
			FileOutputStream fOut = new FileOutputStream(file);
			image.compress(Bitmap.CompressFormat.JPEG, 60, fOut);
			fOut.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		path = null;
		dir = null;
		file = null;
	}
}
