package com.mrcrayfish.app.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mrcrayfish.app.util.SavedVideos;

public class SaveReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		String video_id = intent.getExtras().getString("video_id");
		SavedVideos.add(context, video_id);

		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.cancel(1);

		context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
	}
}
