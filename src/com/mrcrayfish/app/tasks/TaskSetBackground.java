package com.mrcrayfish.app.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class TaskSetBackground extends AsyncTask<Integer, Void, Bitmap>
{
	private ImageView v;

	public TaskSetBackground(ImageView v)
	{
		this.v = v;
	}

	@Override
	protected Bitmap doInBackground(Integer... params)
	{
		return BitmapFactory.decodeResource(v.getResources(), params[0].intValue());
	}

	@Override
	protected void onPostExecute(Bitmap result)
	{
		v.setImageBitmap(result);
		v.setAlpha(1.0F);
	}
}
