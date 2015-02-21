package com.mrcrayfish.app.tumblr;

import android.text.Spanned;

public class LinkPost extends Post
{
	private String title;
	private String link;
	private Spanned description;
	
	public LinkPost(String id, String title, String link, Spanned description, String date)
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

	public Spanned getDescription()
	{
		return description;
	}
}
