package com.mrcrayfish.app.recievers;

import java.util.HashSet;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SaveReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		String video_id = intent.getExtras().getString("video_id");
		SharedPreferences prefs = context.getSharedPreferences("saved-videos", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();

		Set<String> savedVideos = prefs.getStringSet("ids", new HashSet<String>());
		savedVideos.add(video_id);
		editor.putStringSet("ids", savedVideos);
		editor.apply();
	}
}
