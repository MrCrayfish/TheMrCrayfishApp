package com.mrcrayfish.app.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.android.youtube.player.YouTubeIntents;

public class YouTubeUtil
{
	public static void openVideo(Context context, String video_id)
	{
		Intent intent = YouTubeIntents.createPlayVideoIntent(context, video_id);

		if (YouTubeIntents.isYouTubeInstalled(context))
		{
			context.startActivity(intent);
		}
		else
		{
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.youtube.com/watch?v=" + video_id));
			context.startActivity(intent);
		}
	}

	public static void openPlaylist(Context context, String playlist_id)
	{
		Intent intent = YouTubeIntents.createPlayPlaylistIntent(context, playlist_id);
		if (YouTubeIntents.isYouTubeInstalled(context))
		{
			context.startActivity(intent);
		}
		else
		{
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.youtube.com/playlist?list=" + playlist_id));
			context.startActivity(intent);
		}
	}
	
	public static Intent openChannel(Context context, String user_id)
	{
		if (YouTubeIntents.isYouTubeInstalled(context))
		{
			return YouTubeIntents.createUserIntent(context, user_id);
		}
		else
		{
			return new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.youtube.com/user/" + user_id));
		}
	}
}
