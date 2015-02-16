package com.mrcrayfish.app.util;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class SavedVideos
{
	public static void add(Context context, String video_id)
	{
		SharedPreferences prefs = context.getSharedPreferences("saved-videos", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		Set<String> savedVideos = prefs.getStringSet("ids", new HashSet<String>());
		if (!savedVideos.contains(video_id))
		{
			savedVideos.add(video_id);
			editor.putStringSet("ids", savedVideos);
			editor.apply();
			Toast.makeText(context, "Successfully added video to Saved Videos", Toast.LENGTH_LONG).show();
		}
		else
		{
			Toast.makeText(context, "Video is already in Saved Videos", Toast.LENGTH_LONG).show();
		}
	}

	public static void remove(Context context, String video_id)
	{
		SharedPreferences prefs = context.getSharedPreferences("saved-videos", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.clear();
		Set<String> savedVideos = prefs.getStringSet("ids", new HashSet<String>());
		savedVideos.remove(video_id);
		editor.putStringSet("ids", savedVideos);
		editor.commit();
		Toast.makeText(context, "Successfully removed video", Toast.LENGTH_LONG).show();
	}
	
	public static boolean has(Context context, String video_id)
	{
		SharedPreferences prefs = context.getSharedPreferences("saved-videos", Context.MODE_PRIVATE);
		Set<String> savedVideos = prefs.getStringSet("ids", new HashSet<String>());
		return savedVideos.contains(video_id);
	}
}
