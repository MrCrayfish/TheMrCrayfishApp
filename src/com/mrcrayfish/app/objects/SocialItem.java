package com.mrcrayfish.app.objects;

public class SocialItem
{
	private String name;
	private String url;
	private int icon;
	private int colour;
	private int bg;

	public SocialItem(String name, String url, int icon, int colour, int bg)
	{
		this.name = name;
		this.url = url;
		this.icon = icon;
		this.colour = colour;
		this.bg = bg;
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

	public int getBackground()
	{
		return bg;
	}
}
