package com.mrcrayfish.app.tumblr;

public class TextPost extends Post
{
	private String title;
	private String contents;

	public TextPost(String id, String title, String contents, String date)
	{
		super(id, date);
		this.title = title;
		this.contents = contents;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getContent()
	{
		return contents;
	}
}
