package com.mrcrayfish.app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.activities.ScreenshotActivity;

public class ScreenshotAdapter extends BaseAdapter
{
	private Context context;
	private int[] screenshots;

	public ScreenshotAdapter(Context context, int[] screenshots)
	{
		this.context = context;
		this.screenshots = screenshots;
	}

	@Override
	public int getCount()
	{
		return screenshots.length;
	}

	@Override
	public Object getItem(int position)
	{
		return screenshots[position];
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@Override
	@SuppressLint("ViewHolder")
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		LayoutInflater layout = LayoutInflater.from(context);
		View view = layout.inflate(R.layout.screenshot, parent, false);
		
		ImageView screenshot = (ImageView) view.findViewById(R.id.screenshot);
		screenshot.setImageResource(screenshots[position]);
		screenshot.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(context, ScreenshotActivity.class);
				intent.putExtra("screenshot", screenshots[position]);
				context.startActivity(intent);
			}
		});
		return screenshot;
	}

}
