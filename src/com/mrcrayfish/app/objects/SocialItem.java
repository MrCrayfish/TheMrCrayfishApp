package com.mrcrayfish.app.objects;

public class SocialItem
{
	private String name;
	private String url;
	private int icon;
	private int colour;

	public SocialItem(String name, String url, int icon, int colour)
	{
		this.name = name;
		this.url = url;
		this.icon = icon;
		this.colour = colour;
	}

	public String getName()
	{
		return name;
	}

	public int getIcon()
	{
		return icon;
	}

	public String getUrl()
	{
		return url;
	}

	public int getColour()
	{
		return colour;
	}
}
