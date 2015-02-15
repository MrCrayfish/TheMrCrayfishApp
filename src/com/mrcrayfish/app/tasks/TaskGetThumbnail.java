package com.mrcrayfish.app.tasks;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.util.BitmapCache;

public class TaskGetThumbnail extends AsyncTask<String, Void, Bitmap>
{
	private Context context;
	private ImageView view;
	private LruCache<String, Bitmap> cache;

	public TaskGetThumbnail(Context context, ImageView view, LruCache<String, Bitmap> cache)
	{
		this.context = context;
		this.view = view;
		this.cache = cache;
	}

	@Override
	protected Bitmap doInBackground(String... args)
	{
		return getThumbnail(args[0]);
	}

	public Bitmap getThumbnail(String video_id)
	{
		HttpURLConnection connection = null;
		Bitmap thumbnail = BitmapCache.getCachedBitmap(video_id);
		if (thumbnail == null)
		{
			try
			{
				connection = (HttpURLConnection) new URL("http://i.ytimg.com/vi/" + video_id + "/maxresdefault.jpg").openConnection();
				connection.connect();
				InputStream input = connection.getInputStream();
				thumbnail = BitmapFactory.decodeStream(input);
				BitmapCache.saveBitmapToCache(video_id, thumbnail);
			}
			catch (Exception e)
			{
				thumbnail = BitmapFactory.decodeResource(context.getResources(), R.drawable.unknown);
			}
		}
		cache.put(video_id, thumbnail);
		return thumbnail;
	}

	@Override
	protected void onPostExecute(Bitmap result)
	{
		view.setImageBitmap(result);
		view.animate().setDuration(500).alpha(1.0F);
	}
}
