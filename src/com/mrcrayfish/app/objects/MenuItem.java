package com.mrcrayfish.app.objects;

import android.content.Context;
import android.content.Intent;

public class MenuItem
{
	private String title;
	private String description;
	private int background;
	private Intent intent;

	public MenuItem(String title, String description, int background, Intent intent)
	{
		this.title = title;
		this.description = description;
		this.background = background;
		this.intent = intent;
	}

	public MenuItem(String title, String description, int background, Context context, final Class<?> clazz)
	{
		this.title = title;
		this.description = description;
		this.background = background;
		this.intent = new Intent(context, clazz);
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

	public Intent getIntent()
	{
		return intent;
	}
}
