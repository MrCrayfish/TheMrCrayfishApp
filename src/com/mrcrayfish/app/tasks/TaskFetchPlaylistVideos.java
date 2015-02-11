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
import android.view.View;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.activities.VideosActivity;
import com.mrcrayfish.app.adapters.VideoAdapter;
import com.mrcrayfish.app.objects.VideoItem;
import com.mrcrayfish.app.util.BitmapCache;
import com.mrcrayfish.app.util.StreamUtils;

public class TaskFetchPlaylistVideos extends AsyncTask<String, Integer, ArrayList<VideoItem>>
{
	private VideosActivity activity;

	public TaskFetchPlaylistVideos(VideosActivity activity)
	{
		this.activity = activity;
	}

	@Override
	protected ArrayList<VideoItem> doInBackground(String... params)
	{
		try
		{
			HttpParams httpparams = new BasicHttpParams();
			httpparams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			HttpClient client = new DefaultHttpClient(httpparams);
			HttpGet request = new HttpGet("http://gdata.youtube.com/feeds/api/playlists/" + params[0] + "?v=2&alt=jsonc" + getMaxResults());
			HttpResponse response = client.execute(request);

			String data = StreamUtils.convertToString(response.getEntity().getContent());
			JSONObject json = new JSONObject(data);
			JSONArray items = json.getJSONObject("data").getJSONArray("items");

			ArrayList<VideoItem> videos = new ArrayList<VideoItem>();
			for (int i = 0; i < items.length(); i++)
			{
				this.publishProgress(i);

				JSONObject video = items.getJSONObject(i);
				JSONObject metadata = video.getJSONObject("video");

				String id = metadata.getString("id");
				String title = metadata.getString("title");
				String views = metadata.getString("viewCount");
				String date = "Unknown Date";
				double rating = metadata.getDouble("rating");

				try
				{
					SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
					Date pre = oldFormat.parse(metadata.getString("uploaded"));
					SimpleDateFormat newFormat = new SimpleDateFormat("d MMM yyyy", Locale.US);
					date = newFormat.format(pre);
				}
				catch (ParseException e1)
				{
					e1.printStackTrace();
				}

				HttpURLConnection connection = null;
				Bitmap thumbnail = BitmapCache.getCachedBitmap(id);
				if (thumbnail == null)
				{
					try
					{
						connection = (HttpURLConnection) new URL("http://i.ytimg.com/vi/" + id + "/maxresdefault.jpg").openConnection();
						connection.connect();
						InputStream input = connection.getInputStream();
						thumbnail = BitmapFactory.decodeStream(input);
						BitmapCache.saveBitmapToCache(id, thumbnail);
					}
					catch (Exception e)
					{
						thumbnail = BitmapFactory.decodeResource(activity.getResources(), R.drawable.unknown);
					}
				}

				videos.add(new VideoItem(title, thumbnail, id, date, views, (float) rating));
			}
			return videos;
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
		activity.getLoadingText().setText("Loading Video " + (values[0].intValue() + 1) + " of " + activity.video_load_amount);
	}

	@Override
	protected void onPostExecute(ArrayList<VideoItem> videos)
	{
		activity.loadingContainer.setVisibility(View.INVISIBLE);
		if (videos != null)
		{
			activity.setVideoList(videos);
			activity.initList();
			if (activity.swipeLayout.isRefreshing())
			{
				activity.swipeLayout.setRefreshing(false);
				((VideoAdapter) activity.getVideoList().getAdapter()).notifyDataSetChanged();
			}
		}
	}
	
	public String getMaxResults()
	{
		String max_results = "&max-results=" + activity.video_load_amount;
		if (activity.video_load_amount.equals("-1"))
		{
			max_results = "";
		}
		return max_results;
	}

}
