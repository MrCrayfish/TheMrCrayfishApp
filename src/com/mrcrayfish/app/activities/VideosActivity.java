package com.mrcrayfish.app.activities;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.adapters.VideoAdapter;
import com.mrcrayfish.app.interfaces.IVideoList;
import com.mrcrayfish.app.objects.VideoItem;
import com.mrcrayfish.app.tasks.TaskFetchPlaylistVideos;

public class VideosActivity extends Activity implements IVideoList
{
	public SwipeRefreshLayout swipeLayout;
	public RelativeLayout loadingContainer;
	private TextView loadingText;
	private ListView videoList;
	private VideoAdapter videoAdapter;

	private ArrayList<VideoItem> videos = null;
	public String video_load_amount;

	private TaskFetchPlaylistVideos task;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_latest_videos);
		overridePendingTransition(R.anim.animation_slide_left_1, R.anim.animation_slide_left_2);

		setupActionBar();

		loadingContainer = (RelativeLayout) findViewById(R.id.loadingContainer);
		loadingText = (TextView) findViewById(R.id.loadingText);
		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
		videoList = (ListView) findViewById(R.id.lastestVideosList);

		videoList.setDivider(new ColorDrawable(getResources().getColor(R.color.red)));
		videoList.setDividerHeight(10);

		Typeface type = Typeface.createFromAsset(getAssets(), "fonts/bebas_neue.otf");
		loadingText.setTypeface(type);

		setupVideoAmount();

		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		if (videos == null)
		{
			task = new TaskFetchPlaylistVideos(this);
			task.execute(getIntent().getStringExtra("playlist_id"));
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
		task.cancel(true);
		overridePendingTransition(R.anim.animation_slide_right_1, R.anim.animation_slide_right_2);
	}

	public void initList()
	{
		if (videos != null)
		{
			videoAdapter = new VideoAdapter(this, videos, this);
			videoList.setAdapter(videoAdapter);
		}
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

		String barTitle = getIntent().getStringExtra("title");
		if (barTitle == null)
			barTitle = "Latest Videos";
		title.setText(barTitle);

		ab.setCustomView(v);
		ab.setDisplayShowCustomEnabled(true);
	}

	public void setupVideoAmount()
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		video_load_amount = prefs.getString("video_load_amount", "10");

		if (getIntent().getBooleanExtra("isPlaylist", false))
		{
			video_load_amount = getIntent().getIntExtra("playlistSize", 0) + "";
		}

		if (video_load_amount.equals("-1"))
		{
			loadingText.setText("Loading All Videos");
		}
		else
		{
			loadingText.setText("Loading Video 1 of " + video_load_amount);
		}
	}

	public TextView getLoadingText()
	{
		return loadingText;
	}

	@Override
	public void setVideoList(ArrayList<VideoItem> videos)
	{
		this.videos = videos;
	}

	@Override
	public void removeVideo(int position)
	{
		this.videos.remove(position);
	}

	@Override
	public void updateVideoList()
	{
		videoAdapter.notifyDataSetChanged();
	}
}
