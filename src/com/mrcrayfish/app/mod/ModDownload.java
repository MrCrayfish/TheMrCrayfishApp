package com.mrcrayfish.app.mod;

public class ModDownload extends Mod
{
	private String url;
	private String version;

	public ModDownload(String url, String version)
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
