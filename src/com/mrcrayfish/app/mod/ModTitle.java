package com.mrcrayfish.app.mod;

public class ModTitle extends ModPart
{
	private int banner;
	private String title;
	private int icon;
	
	public ModTitle(int banner, String title, int icon)
	{
		this.banner = banner;
		this.title = title;
		this.icon = icon;
	}

	public int getBanner()
	{
		return banner;
	}

	public String getTitle()
	{
		return title;
	}
	
	public int getIcon()
	{
		return icon;
	}
}
