package com.mrcrayfish.app.tumblr;

public class Post
{
	private String id;
	private String date;

	public Post(String id, String date)
	{
		this.id = id;
		this.date = date;
	}

	public String getId()
	{
		return id;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}
}
