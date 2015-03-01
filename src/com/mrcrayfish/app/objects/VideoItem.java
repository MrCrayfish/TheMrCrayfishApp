package com.mrcrayfish.app.objects;

public class VideoItem
{
	private String title;
	private String video_id;
	private String date;
	private String views;
	private float rating;

	public VideoItem(String title, String video_id, String date, String views, float rating)
	{
		this.title = title;
		this.video_id = video_id;
		this.date = date;
		this.views = views;
		this.rating = rating;
	}

	public String getTitle()
	{
		return title;
	}

	public String getVideoId()
	{
		return video_id;
	}

	public String getDate()
	{
		return date;
	}

	public String getViews()
	{
		return views;
	}

	public float getRating()
	{
		return rating;
	}
}
