package com.mrcrayfish.app.objects;

import android.content.Context;
import android.content.Intent;

public class MenuItem
{

	private String title;
	private int background;
	private Intent intent;

	public MenuItem(String title, int background, Intent intent)
	{
		this.title = title;
		this.background = background;
		this.intent = intent;
	}

	public MenuItem(String title, int background, Context context, Class<?> clazz)
	{
		this.title = title;
		this.background = background;
		this.intent = new Intent(context, clazz);
	}

	public String getTitle()
	{
		return title;
	}

	public int getBackground()
	{
		return background;
	}

	public Intent getIntent()
	{
		return intent;
	}
}
