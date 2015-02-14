package com.mrcrayfish.app.receivers;

import java.util.HashSet;
import java.util.Set;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class SaveReceiver extends BroadcastReceiver
{
	private final String TAG = "com.mrcrayfish.app.recievers";
	@Override
	public void onReceive(Context context, Intent intent)
	{
		String video_id = intent.getExtras().getString("video_id");
		SharedPreferences prefs = context.getSharedPreferences("saved-videos", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();

		Set<String> savedVideos = prefs.getStringSet("ids", new HashSet<String>());
		savedVideos.add(video_id);
		editor.remove("ids");
		editor.putStringSet("ids", savedVideos);
		editor.apply();
		Log.i(TAG, "Saved video to Preferences!");
		
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.cancel(1);
		
		context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
	}
}
