package com.mrcrayfish.app.objects;

import android.content.Context;
import android.content.Intent;

public class MenuItem
{
	private String title;
	private String description;
	private int background;
	private Runnable r;

	public MenuItem(String title, String description, int background, final Context context, final Intent intent)
	{
		this.title = title;
		this.description = description;
		this.background = background;
		r = new Runnable()
		{
			@Override
			public void run()
			{
				context.startActivity(intent);

			}
		};
	}

	public MenuItem(String title, String description, int background, final Context context, final Class<?> clazz)
	{
		this.title = title;
		this.description = description;
		this.background = background;
		r = new Runnable()
		{
			@Override
			public void run()
			{
				context.startActivity(new Intent(context, clazz));
			}
		};
	}

	public MenuItem(String title, String description, int background, Runnable r)
	{
		this.title = title;
		this.description = description;
		this.background = background;
		this.r = r;
	}

	public String getTitle()
	{
		return title;
	}
	
	public String getDescription()
	{
		return description;
	}

	public int getBackground()
	{
		return background;
	}

	public Runnable getRunnable()
	{
		return r;
	}
}
