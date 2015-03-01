package com.mrcrayfish.app.tumblr;

public class PhotoPost extends Post
{
	private String caption;
	private String imageUrl;

	public PhotoPost(String id, String caption, String imageUrl, String date)
	{
		super(id, date);
		this.caption = caption;
		this.imageUrl = imageUrl;
	}

	public String getCaption()
	{
		return caption;
	}

	public String getImageUrl()
	{
		return imageUrl;
	}
}
