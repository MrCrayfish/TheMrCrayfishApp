package com.mrcrayfish.app.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.activities.PlaylistActivity;
import com.mrcrayfish.app.adapters.PlaylistAdapter;
import com.mrcrayfish.app.objects.PlaylistItem;
import com.mrcrayfish.app.util.BitmapCache;
import com.mrcrayfish.app.util.StreamUtils;

public class TaskFetchPlaylists extends AsyncTask<Void, Integer, ArrayList<PlaylistItem>>
{
	private final String TAG = "com.mrcrayfish.app.tasks";
	private PlaylistActivity activity;
	private int playlist_size = 0;

	public TaskFetchPlaylists(PlaylistActivity activity)
	{
		this.activity = activity;
	}

	@Override
	protected ArrayList<PlaylistItem> doInBackground(Void... params)
	{
		try
		{
			HttpParams httpparams = new BasicHttpParams();
			httpparams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			HttpClient client = new DefaultHttpClient(httpparams);
			HttpGet request = new HttpGet("https://gdata.youtube.com/feeds/api/users/mrcrayfishminecraft/playlists?v=2&alt=jsonc");
			HttpResponse response = client.execute(request);

			String data = StreamUtils.convertToString(response.getEntity().getContent());
			JSONObject json = new JSONObject(data);
			JSONArray items = json.getJSONObject("data").getJSONArray("items");

			playlist_size = items.length();

			ArrayList<PlaylistItem> playlists = new ArrayList<PlaylistItem>();
			for (int i = 0; i < items.length(); i++)
			{
				this.publishProgress(i);

				JSONObject playlist = items.getJSONObject(i);
				String playlist_id = playlist.getString("id");
				String title = playlist.getString("title");
				int size = playlist.getInt("size");
				String thumbnail_url = playlist.getJSONObject("thumbnail").getString("hqDefault");
				String date = "Unknown Date";

				try
				{
					SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
					Date pre = oldFormat.parse(playlist.getString("updated"));
					SimpleDateFormat newFormat = new SimpleDateFormat("d MMM yyyy", Locale.US);
					date = newFormat.format(pre);
				}
				catch (ParseException e1)
				{
					e1.printStackTrace();
				}

				String[] urlData = thumbnail_url.split("/");
				String video_id = urlData[urlData.length - 2];

				HttpURLConnection connection = null;
				Bitmap thumbnail = BitmapCache.getCachedBitmap(video_id);
				if (thumbnail == null)
				{
					try
					{
						connection = (HttpURLConnection) new URL("http://i.ytimg.com/vi/" + video_id + "/maxresdefault.jpg").openConnection();
						connection.connect();
						InputStream input = connection.getInputStream();
						thumbnail = BitmapFactory.decodeStream(input);
						BitmapCache.saveBitmapToCache(video_id, thumbnail);
					}
					catch (Exception e)
					{
						thumbnail = BitmapFactory.decodeResource(activity.getResources(), R.drawable.unknown);
					}
				}

				playlists.add(new PlaylistItem(playlist_id, title, date, size));
			}
			return playlists;
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
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values)
	{
		activity.getLoadingText().setText("Loading Playlist " + (values[0].intValue() + 1) + " of " + playlist_size);
	}

	@Override
	protected void onPostExecute(ArrayList<PlaylistItem> playlists)
	{
		Log.i(TAG, "Post Processing");
		activity.loadingContainer.setVisibility(View.INVISIBLE);
		if (playlists != null)
		{
			activity.setPlaylistList(playlists);
			activity.initList();
			if (activity.swipeLayout.isRefreshing())
			{
				activity.swipeLayout.setRefreshing(false);
				((PlaylistAdapter) activity.getPlaylistList().getAdapter()).notifyDataSetChanged();
			}
		}
	}

}
