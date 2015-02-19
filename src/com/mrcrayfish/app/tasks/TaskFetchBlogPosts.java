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

import com.mrcrayfish.app.activities.BlogActivity;
import com.mrcrayfish.app.tumblr.PhotoPost;
import com.mrcrayfish.app.tumblr.Post;
import com.mrcrayfish.app.tumblr.TextPost;
import com.mrcrayfish.app.util.StreamUtils;

public class TaskFetchBlogPosts extends AsyncTask<Void, Integer, ArrayList<Post>>
{
	private BlogActivity activity;
	private int postCount;

	public TaskFetchBlogPosts(BlogActivity activity)
	{
		this.activity = activity;
	}

	@Override
	protected ArrayList<Post> doInBackground(Void... params)
	{
		try
		{
			HttpParams httpparams = new BasicHttpParams();
			httpparams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			HttpClient client = new DefaultHttpClient(httpparams);
			HttpGet request = new HttpGet("http://mrcrayfish.tumblr.com/api/read/json");
			HttpResponse response = client.execute(request);

			String data = StreamUtils.convertToString(response.getEntity().getContent());
			data = data.replace("var tumblr_api_read = ", "");
			data = data.replace(";", "");

			JSONObject json = new JSONObject(data);
			postCount = json.getInt("posts-total");
			JSONArray items = json.getJSONArray("posts");

			ArrayList<Post> posts = new ArrayList<Post>();
			for (int i = 0; i < postCount; i++)
			{
				JSONObject post = items.getJSONObject(i);
				String id = post.getString("id");
				String type = post.getString("type");
				String date = convertDate(post.getString("date"));
				if (type.equals("regular"))
				{
					String title = post.getString("regular-title");
					String content = post.getString("regular-body").replaceAll("\\<[^>]*>", "");
					posts.add(new TextPost(id, title, content, date));
				}
				else if (type.equals("photo"))
				{
					String caption = post.getString("photo-caption").replaceAll("\\<[^>]*>", "");
					String imageUrl = post.getString("photo-url-1280");
					posts.add(new PhotoPost(id, caption, imageUrl, date));
				}
			}
			return posts;
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
		activity.getLoadingText().setText("Loading Post " + (values[0].intValue() + 1) + " of " + postCount);
	}

	@Override
	protected void onPostExecute(ArrayList<Post> posts)
	{
		activity.loadingContainer.setVisibility(View.INVISIBLE);
		if (posts != null)
		{
			activity.setPostList(posts);
			activity.initList();
			if (activity.swipeLayout.isRefreshing())
			{
				activity.swipeLayout.setRefreshing(false);
				activity.getAdapter().notifyDataSetChanged();
			}
		}
	}

	public String convertDate(String date)
	{
		try
		{
			SimpleDateFormat oldFormat = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss", Locale.US);
			Date pre = oldFormat.parse(date);
			SimpleDateFormat newFormat = new SimpleDateFormat("d MMM yy", Locale.US);
			System.out.println(newFormat.format(pre));
			return newFormat.format(pre);
		}
		catch (ParseException e1)
		{
			e1.printStackTrace();
		}
		return "";
	}
}
