package com.mrcrayfish.app.mod;

public class ModRecipes extends ModPart
{
	private String url;
	private String version;

	public ModRecipes(String url, String version)
	{
		this.url = url;
		this.version = version;
	}

	public String getURL()
	{
		return url;
	}

	public String getVersion()
	{
		return version;
	}
}
