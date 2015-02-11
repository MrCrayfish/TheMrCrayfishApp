package com.mrcrayfish.app.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.activities.GridActivity;
import com.mrcrayfish.app.recievers.SaveReceiver;
import com.mrcrayfish.app.recievers.WatchReceiver;
import com.mrcrayfish.app.util.StreamUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.BigPictureStyle;

public class ServiceVideoChecker extends Service
{
	private boolean running = true;
	private String prevVideoId = null;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Runnable r = new Runnable()
		{
			@Override
			public void run()
			{
				while (running)
				{
					synchronized (this)
					{
						System.out.println("Checking for video");
						checkForVideo();
						try
						{
							// wait(600000);
							wait(40000);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		};
		Thread checker = new Thread(r);
		checker.start();
		return Service.START_STICKY;
	}

	public void checkForVideo()
	{
		try
		{
			HttpParams httpparams = new BasicHttpParams();
			httpparams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			HttpClient client = new DefaultHttpClient(httpparams);
			HttpGet request = new HttpGet("http://gdata.youtube.com/feeds/api/playlists/PLy11IosblXIEvmCD1OOsbFkqowZZvN5xi?v=2&alt=jsonc&max-results=1");
			HttpResponse response = client.execute(request);

			String data = StreamUtils.convertToString(response.getEntity().getContent());
			JSONObject json = new JSONObject(data);
			JSONArray items = json.getJSONObject("data").getJSONArray("items");
			JSONObject item = items.getJSONObject(0);
			String video_id = item.getJSONObject("video").getString("id");
			System.out.println("New Video ID: " + video_id + " Previous Video ID: " + prevVideoId);

			System.out.println("Starting check");
			if (prevVideoId != null)
			{
				System.out.println("prevVideoId is no null");
				if (!video_id.equals(prevVideoId))
				{
					System.out.println("IDs are different!");
					String title = item.getJSONObject("video").getString("title");

					HttpURLConnection connection = null;
					Bitmap thumbnail = null;

					try
					{
						connection = (HttpURLConnection) new URL("http://i.ytimg.com/vi/" + video_id + "/maxresdefault.jpg").openConnection();
						connection.connect();
					}
					catch (FileNotFoundException e)
					{
						thumbnail = BitmapFactory.decodeResource(getResources(), R.drawable.unknown);
					}

					if (thumbnail == null)
					{
						InputStream input = connection.getInputStream();
						thumbnail = BitmapFactory.decodeStream(input);
					}

					NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
					nm.notify(1, build(title, video_id, thumbnail));
					System.out.println("Sending notification");
				}
			}
			prevVideoId = video_id;

		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	public Notification build(String title, String video_id, Bitmap thumbnail)
	{
		NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
		notification.setSmallIcon(R.drawable.mrcrayfish_icon);
		notification.setTicker("MrCrayfish has uploaded a new video!");
		notification.setWhen(System.currentTimeMillis());
		notification.setContentTitle("New Video by MrCrayfish");
		notification.setContentText(title);
		notification.setLights(Color.RED, 500, 500);
		notification.setAutoCancel(true);

		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		notification.setSound(alarmSound);

		BigPictureStyle style = new NotificationCompat.BigPictureStyle();
		style.bigPicture(thumbnail);
		style.setSummaryText(title);
		notification.setStyle(style);

		Intent watchIntent = new Intent(this, WatchReceiver.class);
		watchIntent.putExtra("video_id", video_id);
		PendingIntent pendingWatch = PendingIntent.getBroadcast(this, 0, watchIntent, 0);
		notification.addAction(android.R.drawable.ic_media_play, "Watch Now", pendingWatch);

		Intent openIntent = new Intent(this, SaveReceiver.class);
		PendingIntent pendingDismiss = PendingIntent.getActivity(this, 0, openIntent, 0);
		notification.addAction(android.R.drawable.ic_search_category_default, "Save", pendingDismiss);

		Intent intent = new Intent(this, GridActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setContentIntent(pendingIntent);

		return notification.build();
	}
}
