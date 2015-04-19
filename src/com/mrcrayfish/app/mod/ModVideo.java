package com.mrcrayfish.app.mod;

public class ModVideo extends ModPart
{
	private String title;
	private String video_id;

	public ModVideo(String title, String video_id)
	{
		this.title = title;
		this.video_id = video_id;
	}

	public String getTitle()
	{
		return title;
	}

	public String getVideoId()
	{
		return video_id;
	}
}
