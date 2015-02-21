package com.mrcrayfish.app.tumblr;

public class VideoPost extends Post
{
	private String title;
	private String video_id;
	private String views;
	private float rating;

	public VideoPost(String title, String video_id, String date, String views, float rating)
	{
		super(video_id, date);
		this.title = title;
		this.video_id = video_id;
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

	public String getViews()
	{
		return views;
	}

	public float getRating()
	{
		return rating;
	}
}
