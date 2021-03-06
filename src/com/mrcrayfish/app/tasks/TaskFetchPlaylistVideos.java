package com.mrcrayfish.app.tasks;

import java.io.IOException;
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

import android.os.AsyncTask;
import android.view.View;

import com.mrcrayfish.app.activities.VideosActivity;
import com.mrcrayfish.app.objects.VideoItem;
import com.mrcrayfish.app.util.StreamUtils;

public class TaskFetchPlaylistVideos extends AsyncTask<String, Object, ArrayList<VideoItem>>
{
	private VideosActivity activity;
	private int video_amount = 0;

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
			this.publishProgress("video_amount", items.length());

			ArrayList<VideoItem> videos = new ArrayList<VideoItem>();
			for (int i = 0; i < items.length(); i++)
			{
				System.out.println("Getting info for video " + i);
				this.publishProgress("progress", i);

				JSONObject video = items.getJSONObject(i);
				JSONObject metadata = video.getJSONObject("video");

				if (!metadata.has("status"))
				{
					String id = metadata.getString("id");
					String title = metadata.getString("title");
					String views = metadata.getString("viewCount");
					String date = "Unknown Date";

					double rating = 0;
					if (metadata.has("rating"))
					{
						rating = metadata.getDouble("rating");
					}

					try
					{
						SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
						Date pre = oldFormat.parse(metadata.getString("uploaded"));
						SimpleDateFormat newFormat = new SimpleDateFormat("d MMM yy", Locale.US);
						date = newFormat.format(pre);
					}
					catch (ParseException e1)
					{
						e1.printStackTrace();
					}

					videos.add(new VideoItem(title, id, date, views, (float) rating));
				}
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
	protected void onProgressUpdate(Object... values)
	{
		String type = (String) values[0];
		if (type.equals("video_amount"))
		{
			Integer integer = (Integer) values[1];
			this.video_amount = integer.intValue();
		}
		else if (type.equals("progress"))
		{
			Integer integer = (Integer) values[1];
			activity.getLoadingText().setText("Loading Video " + (integer.intValue() + 1) + " of " + video_amount);
		}

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
				activity.updateVideoList();
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
