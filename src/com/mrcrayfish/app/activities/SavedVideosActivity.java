package com.mrcrayfish.app.activities;

import java.util.ArrayList;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.adapters.VideoAdapter;
import com.mrcrayfish.app.objects.VideoItem;
import com.mrcrayfish.app.tasks.TaskFetchVideos;

public class SavedVideosActivity extends Activity
{
	private ProgressBar loadProgress;
	private ListView videoList;
	private VideoAdapter videoAdapter;
	private ArrayList<VideoItem> videos = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_videos);

		loadProgress = (ProgressBar) findViewById(R.id.loadProgress);
		videoList = (ListView) findViewById(R.id.savedVideosList);
		videoList.setDivider(new ColorDrawable(getResources().getColor(R.color.red)));
		videoList.setDividerHeight(10);

		SharedPreferences prefs = getSharedPreferences("saved-videos", Context.MODE_PRIVATE);
		Set<String> videos = prefs.getStringSet("ids", null);
		if (videos != null)
		{
			loadProgress.setMax(videos.size());
		}
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		if (videos == null)
		{
			SharedPreferences prefs = getSharedPreferences("saved-videos", Context.MODE_PRIVATE);
			Set<String> videos = prefs.getStringSet("ids", null);
			if (videos != null)
			{
				new TaskFetchVideos(this).execute(videos.toArray(new String[0]));
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item)
	{
		int id = item.getItemId();
		if (id == android.R.id.home)
		{
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		overridePendingTransition(R.anim.animation_slide_right_1, R.anim.animation_slide_right_2);
	}

	public void initList()
	{
		if (videos != null)
		{
			videoAdapter = new VideoAdapter(this, videos.toArray(new VideoItem[0]));
			videoList.setAdapter(videoAdapter);
		}
	}

	public void setVideoList(ArrayList<VideoItem> videos)
	{
		this.videos = videos;
	}

	@SuppressLint("InflateParams")
	public void setupActionBar()
	{
		ActionBar ab = getActionBar();
		ab.setDisplayShowHomeEnabled(false);
		ab.setDisplayShowTitleEnabled(false);

		LayoutInflater inflator = LayoutInflater.from(this);
		View v = inflator.inflate(R.layout.app_bar, null);
		Typeface type = Typeface.createFromAsset(getAssets(), "fonts/bebas_neue.otf");
		TextView title = (TextView) v.findViewById(R.id.barTitle);
		title.setTypeface(type);
		title.setText("Saved Videos");

		ab.setCustomView(v);
		ab.setDisplayShowCustomEnabled(true);
	}

	public ProgressBar getLoadProgress()
	{
		return loadProgress;
	}

	public ListView getVideoList()
	{
		return videoList;
	}
}
