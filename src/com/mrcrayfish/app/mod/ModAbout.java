package com.mrcrayfish.app.mod;

public class ModAbout extends ModPart
{
	private int titleImage;
	private String desc;

	public ModAbout(int titleImage, String desc)
	{
		this.titleImage = titleImage;
		this.desc = desc;
	}

	public int getTitleImage()
	{
		return titleImage;
	}

	public String getDesc()
	{
		return desc;
	}
}
