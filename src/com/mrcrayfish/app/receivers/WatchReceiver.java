package com.mrcrayfish.app.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.android.youtube.player.YouTubeIntents;

public class WatchReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		String video_id = intent.getExtras().getString("video_id");
		Intent videoIntent = YouTubeIntents.createPlayVideoIntent(context, video_id);
		videoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		if (YouTubeIntents.isYouTubeInstalled(context))
		{
			context.startActivity(videoIntent);
		}
		else
		{
			videoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.youtube.com/watch?v=" + video_id));
			context.startActivity(videoIntent);
		}

		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.cancel(1);

		context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
	}
}
