package com.mrcrayfish.app.mod;

public class ModLink extends ModPart
{
	private String text;
	private String url;
	
	public ModLink(String text, String url)
	{
		this.text = text;
		this.url = url;
	}

	public String getText()
	{
		return text;
	}

	public String getUrl()
	{
		return url;
	}
}
