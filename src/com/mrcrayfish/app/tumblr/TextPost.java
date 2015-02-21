package com.mrcrayfish.app.tumblr;

import android.text.Spanned;

public class TextPost extends Post
{
	private String title;
	private Spanned contents;

	public TextPost(String id, String title, Spanned contents, String date)
	{
		super(id, date);
		this.title = title;
		this.contents = contents;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public Spanned getContent()
	{
		return contents;
	}
}
