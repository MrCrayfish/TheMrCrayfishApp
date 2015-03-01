package com.mrcrayfish.app.interfaces;

import java.util.ArrayList;

import com.mrcrayfish.app.objects.VideoItem;

public interface IVideoList
{
	public void setVideoList(ArrayList<VideoItem> videos);

	public void removeVideo(int position);

	public void updateVideoList();
}
