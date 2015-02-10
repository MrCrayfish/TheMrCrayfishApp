package com.mrcrayfish.app.activities;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.adapters.VideoAdapter;
import com.mrcrayfish.app.objects.VideoItem;
import com.mrcrayfish.app.tasks.TaskFetchVideos;

public class VideosActivity extends Activity
{
	public SwipeRefreshLayout swipeLayout;
	private ProgressBar loadProgress;
	private ListView videoList;
	private VideoAdapter videoAdapter;
	private ArrayList<VideoItem> videos = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_latest_videos);
		overridePendingTransition(R.anim.animation_slide_left_1, R.anim.animation_slide_left_2);
		
		setupActionBar();

		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
		loadProgress = (ProgressBar) findViewById(R.id.loadProgress);
		videoList = (ListView) findViewById(R.id.lastestVideosList);

		loadProgress.setMax(10);
		videoList.setDivider(new ColorDrawable(getResources().getColor(R.color.red)));
		videoList.setDividerHeight(10);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		if (videos == null)
		{
			new TaskFetchVideos(this).execute(getIntent().getStringExtra("playlist_id"));
		}
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
