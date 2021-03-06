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

import com.mrcrayfish.app.activities.SavedVideosActivity;
import com.mrcrayfish.app.objects.VideoItem;
import com.mrcrayfish.app.util.StreamUtils;

public class TaskFetchVideos extends AsyncTask<String, Integer, ArrayList<VideoItem>>
{
	private SavedVideosActivity activity;
	private int videosSize = 0;

	public TaskFetchVideos(SavedVideosActivity activity)
	{
		this.activity = activity;
	}

	@Override
	protected ArrayList<VideoItem> doInBackground(String... video_ids)
	{
		videosSize = video_ids.length;
		try
		{
			ArrayList<VideoItem> videos = new ArrayList<VideoItem>();
			int count = 0;
			for (String video_id : video_ids)
			{
				this.publishProgress(count);
				HttpParams httpparams = new BasicHttpParams();
				httpparams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
				HttpClient client = new DefaultHttpClient(httpparams);
				HttpGet request = new HttpGet("https://gdata.youtube.com/feeds/api/videos?q=" + video_id + "&v=2&alt=jsonc&max-results=1");
				HttpResponse response = client.execute(request);

				String data = StreamUtils.convertToString(response.getEntity().getContent());
				JSONObject json = new JSONObject(data);
				JSONArray items = json.getJSONObject("data").getJSONArray("items");

				JSONObject video = items.getJSONObject(0);

				String title = video.getString("title");
				String views = video.getString("viewCount");
				String date = "Unknown Date";
				double rating = video.getDouble("rating");

				try
				{
					SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
					Date pre = oldFormat.parse(video.getString("uploaded"));
					SimpleDateFormat newFormat = new SimpleDateFormat("d MMM yy", Locale.US);
					date = newFormat.format(pre);
				}
				catch (ParseException e1)
				{
					e1.printStackTrace();
				}

				videos.add(new VideoItem(title, video_id, date, views, (float) rating));
				count++;
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
		activity.getLoadingText().setText("Loading Video " + (values[0].intValue() + 1) + " of " + videosSize);
	}

	@Override
	protected void onPostExecute(ArrayList<VideoItem> videos)
	{
		activity.loadingContainer.setVisibility(View.INVISIBLE);
		if (videos != null)
		{
			activity.setVideoList(videos);
			activity.initList();
		}
	}
}
