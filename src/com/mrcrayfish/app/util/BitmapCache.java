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
	public static Bitmap getCachedBitmap(String id, String folder)
	{
		String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TheMrCrayfishApp/" + folder;
		File dir = new File(path);
		if (!dir.exists())
		{
			dir.mkdirs();
		}
		File file = new File(dir, id);
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

	public static void saveBitmapToCache(String id, Bitmap image, String folder)
	{
		String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TheMrCrayfishApp/" + folder;
		File dir = new File(path);
		if (!dir.exists())
		{
			dir.mkdirs();
		}
		File file = new File(dir, id);
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
