package com.mrcrayfish.app.tasks;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
import android.text.Html;
import android.view.View;

import com.mrcrayfish.app.activities.BlogActivity;
import com.mrcrayfish.app.tumblr.LinkPost;
import com.mrcrayfish.app.tumblr.PhotoPost;
import com.mrcrayfish.app.tumblr.Post;
import com.mrcrayfish.app.tumblr.TextPost;
import com.mrcrayfish.app.tumblr.VideoPost;
import com.mrcrayfish.app.util.StreamUtils;

public class TaskFetchBlogPosts extends AsyncTask<Void, Object, ArrayList<Post>>
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
			data = data.replace("\n\n", "");

			JSONObject json = new JSONObject(data);
			JSONArray items = json.getJSONArray("posts");
			this.publishProgress("video_amount", items.length());

			ArrayList<Post> posts = new ArrayList<Post>();
			for (int i = 0; i < items.length(); i++)
			{
				this.publishProgress("progress", i);

				JSONObject post = items.getJSONObject(i);
				String id = post.getString("id");
				String type = post.getString("type");
				String date = convertDate(post.getString("date"));
				if (type.equals("regular"))
				{
					String title = post.getString("regular-title");
					String content = post.getString("regular-body");
					posts.add(new TextPost(id, title, Html.fromHtml(content.replace("<p>", "").replace("</p>", "")), date));
				}
				else if (type.equals("photo"))
				{
					String caption = post.getString("photo-caption").replaceAll("\\<[^>]*>", "");
					String imageUrl = post.getString("photo-url-1280");
					posts.add(new PhotoPost(id, caption, imageUrl, date));
				}
				else if (type.equals("link"))
				{
					String linkTitle = post.getString("link-text");
					String linkUrl = post.getString("link-url");
					String linkDesc = post.getString("link-description");
					try
					{
						String domain = getDomainName(linkUrl);
						if (domain.equals("youtube") && linkUrl.contains("watch?v="))
						{
							Post video = convertVideoIdToPost(date, linkUrl.split("=")[1]);
							if (video != null)
							{
								posts.add(video);
							}
						}
						else
						{
							posts.add(new LinkPost(id, linkTitle, linkUrl, linkDesc, date));
						}
					}
					catch (URISyntaxException e)
					{
						e.printStackTrace();
					}
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
	protected void onProgressUpdate(Object... values)
	{
		String type = (String) values[0];
		if (type.equals("video_amount"))
		{
			Integer integer = (Integer) values[1];
			this.postCount = integer.intValue();
		}
		else if (type.equals("progress"))
		{
			Integer integer = (Integer) values[1];
			activity.getLoadingText().setText("Loading Post " + (integer.intValue() + 1) + " of " + postCount);
		}

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

	public String getDomainName(String url) throws URISyntaxException
	{
		URI uri = new URI(url);
		String domain = uri.getHost();
		domain = domain.endsWith(".com") ? domain.substring(0, domain.length() - ".com".length()) : domain;
		return domain.startsWith("www.") ? domain.substring(4) : domain;
	}

	public Post convertVideoIdToPost(String date, String video_id)
	{
		try
		{
			HttpParams httpparams = new BasicHttpParams();
			httpparams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			HttpClient client = new DefaultHttpClient(httpparams);
			HttpGet request = new HttpGet("http://gdata.youtube.com/feeds/api/videos?q=" + video_id + "&v=2&alt=jsonc&max-results=1");
			HttpResponse response = client.execute(request);

			String data = StreamUtils.convertToString(response.getEntity().getContent());
			JSONObject json = new JSONObject(data);
			JSONArray items = json.getJSONObject("data").getJSONArray("items");

			JSONObject video = items.getJSONObject(0);

			String title = video.getString("title");
			String views = video.getString("viewCount");
			double rating = video.getDouble("rating");

			return new VideoPost(title, video_id, date, views, (float) rating);
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
}
