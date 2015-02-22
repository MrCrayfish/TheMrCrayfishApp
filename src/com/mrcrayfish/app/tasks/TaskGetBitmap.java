package com.mrcrayfish.app.tasks;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.util.BitmapCache;

public class TaskGetBitmap extends AsyncTask<String, Void, Bitmap>
{
	private Context context;
	private ImageView view;
	private LruCache<String, Bitmap> cache;
	private Type type;

	public TaskGetBitmap(Context context, ImageView view, LruCache<String, Bitmap> cache, Type type)
	{
		this.context = context;
		this.view = view;
		this.cache = cache;
		this.type = type;
	}

	@Override
	protected Bitmap doInBackground(String... args)
	{
		if (type == Type.YOUTUBE)
		{
			return getBitmap(args[0], "http://i.ytimg.com/vi/" + args[0] + "/maxresdefault.jpg");
		}
		if (type == Type.TUMBLR)
		{
			return getBitmap(args[0], args[1]);
		}
		return null;
	}

	public Bitmap getBitmap(String id, String url)
	{
		HttpURLConnection connection = null;
		Bitmap bitmap = BitmapCache.getCachedBitmap(id, type.name().toLowerCase(Locale.US));
		if (bitmap == null)
		{
			try
			{
				connection = (HttpURLConnection) new URL(url).openConnection();
				connection.connect();
				InputStream input = connection.getInputStream();
				bitmap = BitmapFactory.decodeStream(input);
				BitmapCache.saveBitmapToCache(id, bitmap, type.name().toLowerCase(Locale.US));
			}
			catch (Exception e)
			{
				bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.unknown);
			}
		}
		cache.put(id, bitmap);
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap result)
	{
		if (result != null)
		{
			view.setImageBitmap(result);
			view.animate().setDuration(500).alpha(1.0F);
		}
	}

	public static enum Type
	{
		YOUTUBE, TUMBLR;
	}
}
