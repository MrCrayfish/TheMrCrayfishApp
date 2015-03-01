package com.mrcrayfish.app.objects;

public class PlaylistItem
{
	private String playlist_id;
	private String thumbnail_id;
	private String title;
	private String date;
	private int size;

	public PlaylistItem(String playlist_id, String thumbnail_id, String title, String date, int size)
	{
		this.playlist_id = playlist_id;
		this.thumbnail_id = thumbnail_id;
		this.title = title;
		this.date = date;
		this.size = size;
	}

	public String getPlaylistId()
	{
		return playlist_id;
	}

	public String getThumbnailId()
	{
		return thumbnail_id;
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
