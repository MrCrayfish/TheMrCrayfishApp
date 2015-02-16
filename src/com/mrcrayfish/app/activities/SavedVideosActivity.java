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
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.adapters.SavedVideoAdapter;
import com.mrcrayfish.app.interfaces.IVideoList;
import com.mrcrayfish.app.objects.VideoItem;
import com.mrcrayfish.app.tasks.TaskFetchVideos;
import com.mrcrayfish.app.util.SavedVideos;

public class SavedVideosActivity extends Activity implements IVideoList
{
	public RelativeLayout loadingContainer;
	private TextView loadingText;
	private TextView noVideos;
	private ListView videoList;
	private SavedVideoAdapter videoAdapter;
	private ArrayList<VideoItem> videos = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_saved_videos);
		overridePendingTransition(R.anim.animation_slide_left_1, R.anim.animation_slide_left_2);

		loadingContainer = (RelativeLayout) findViewById(R.id.loadingContainer);
		loadingText = (TextView) findViewById(R.id.loadingText);
		noVideos = (TextView) findViewById(R.id.noVideos);
		videoList = (ListView) findViewById(R.id.savedVideosList);
		videoList.setDivider(new ColorDrawable(getResources().getColor(R.color.red)));
		videoList.setDividerHeight(10);

		Typeface type = Typeface.createFromAsset(getAssets(), "fonts/bebas_neue.otf");
		loadingText.setTypeface(type);
		noVideos.setTypeface(type);

		setupActionBar();

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
			if (videos != null && videos.size() > 0)
			{
				new TaskFetchVideos(this).execute(videos.toArray(new String[0]));
			}
			else
			{
				loadingContainer.setVisibility(View.INVISIBLE);
				noVideos.setVisibility(View.VISIBLE);
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
			videoAdapter = new SavedVideoAdapter(this, videos, this);
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
		title.setText("Saved Videos");

		ab.setCustomView(v);
		ab.setDisplayShowCustomEnabled(true);
	}

	public TextView getLoadingText()
	{
		return loadingText;
	}

	public TextView getNoVideoText()
	{
		return noVideos;
	}

	@Override
	public void setVideoList(ArrayList<VideoItem> videos)
	{
		this.videos = videos;
	}

	@Override
	public void removeVideo(int position)
	{
		VideoItem video = videos.remove(position);
		SavedVideos.remove(this, video.getVideoId());
	}

	@Override
	public void updateVideoList()
	{
		videoAdapter.notifyDataSetChanged();
		if (videos.size() == 0)
		{
			noVideos.setVisibility(View.VISIBLE);
		}
		else
		{
			noVideos.setVisibility(View.GONE);
		}
	}
}
