package com.mrcrayfish.app.objects;

import android.content.Context;
import android.content.Intent;

public class MenuItem
{
	private String title;
	private String description;
	private int icon;
	private Intent intent;

	public MenuItem(String title, String description, int icon, Intent intent)
	{
		this.title = title;
		this.description = description;
		this.icon = icon;
		this.intent = intent;
	}

	public MenuItem(String title, String description, int icon, Context context, final Class<?> clazz)
	{
		this.title = title;
		this.description = description;
		this.icon = icon;
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

	public int getIcon()
	{
		return icon;
	}

	public Intent getIntent()
	{
		return intent;
	}
}
