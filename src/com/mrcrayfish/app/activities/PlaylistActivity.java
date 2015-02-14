package com.mrcrayfish.app.activities;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.adapters.PlaylistAdapter;
import com.mrcrayfish.app.objects.PlaylistItem;
import com.mrcrayfish.app.tasks.TaskFetchPlaylists;

public class PlaylistActivity extends Activity
{
	public SwipeRefreshLayout swipeLayout;
	public RelativeLayout loadingContainer;
	private TextView loadingText;
	private ListView playlistList;
	private PlaylistAdapter playlistAdapter;
	private ArrayList<PlaylistItem> playlists = null;

	public String playlists_info = "Loading Playlists";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_playlist);
		overridePendingTransition(R.anim.animation_slide_left_1, R.anim.animation_slide_left_2);

		setupActionBar();

		loadingContainer = (RelativeLayout) findViewById(R.id.loadingContainer);
		loadingText = (TextView) findViewById(R.id.loadingText);
		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
		playlistList = (ListView) findViewById(R.id.playlistList);

		Typeface type = Typeface.createFromAsset(getAssets(), "fonts/bebas_neue.otf");
		loadingText.setTypeface(type);
		loadingText.setText(playlists_info);

		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		if (playlists == null)
		{
			new TaskFetchPlaylists(this).execute();
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
		if (playlists != null)
		{
			playlistAdapter = new PlaylistAdapter(this, playlists.toArray(new PlaylistItem[0]));
			playlistList.setAdapter(playlistAdapter);
		}
	}

	public void setPlaylistList(ArrayList<PlaylistItem> playlists)
	{
		this.playlists = playlists;
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
		title.setText("Playlists");

		ab.setCustomView(v);
		ab.setDisplayShowCustomEnabled(true);
	}

	public TextView getLoadingText()
	{
		return loadingText;
	}

	public ListView getPlaylistList()
	{
		return playlistList;
	}
}
