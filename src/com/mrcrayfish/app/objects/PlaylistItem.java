package com.mrcrayfish.app.objects;

import android.graphics.Bitmap;

public class PlaylistItem
{
	private String playlist_id;
	private String title;
	private String date;
	private int size;

	public PlaylistItem(String playlist_id, String title, String date, int size)
	{
		this.playlist_id = playlist_id;
		this.title = title;
		this.date = date;
		this.size = size;
	}
	
	public String getPlaylistId()
	{
		return playlist_id;
	}

	public String getTitle()
	{
		return title;
	}

	public String getDate()
	{
		return date;
	}
	
	public int getSize()
	{
		return size;
	}
}
