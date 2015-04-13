package com.mrcrayfish.app.tumblr;


public class LinkPost extends Post
{
	private String title;
	private String link;
	private String description;

	public LinkPost(String id, String title, String link, String description, String date)
	{
		super(id, date);
		this.title = title;
		this.link = link;
		this.description = description;
	}

	public String getTitle()
	{
		return title;
	}

	public String getLink()
	{
		return link;
	}

	public String getDescription()
	{
		return description;
	}
}
