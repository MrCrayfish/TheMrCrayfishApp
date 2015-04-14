package com.mrcrayfish.app.mod;

public class ModDownload extends ModPart
{
	private ModLink[] links;

	public ModDownload(ModLink ... links)
	{
		this.links = links;
	}

	public ModLink[] getLinks()
	{
		return links;
	}
}
