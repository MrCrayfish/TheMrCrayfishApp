package com.mrcrayfish.app.activities;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.adapters.MenuAdapter;
import com.mrcrayfish.app.objects.MenuItem;
import com.mrcrayfish.app.services.ServiceVideoChecker;
import com.mrcrayfish.app.util.YouTubeUtil;

public class VideoMenuActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_grid);
		overridePendingTransition(R.anim.animation_slide_left_1, R.anim.animation_slide_left_2);

		setupActionBar();

		ListView menu = (ListView) findViewById(R.id.menuList);
		menu.setDivider(null);
		menu.setDividerHeight(0);

		List<MenuItem> items = new ArrayList<MenuItem>();
		Intent videoIntent = new Intent(this, VideosActivity.class);
		videoIntent.putExtra("playlist_id", "UUSwwxl2lWJcbGOGQ_d04v2Q");
		items.add(new MenuItem("Latest Uploads", "Discover MrCrayfish's newest videos", R.drawable.menu_item_bg_1, this, videoIntent));
		items.add(new MenuItem("Playlists", "Collections of Videos", R.drawable.menu_item_bg_2, this, PlaylistActivity.class));
		items.add(new MenuItem("Watch Later", "Your saved videos", R.drawable.menu_item_bg_3, this, SavedVideosActivity.class));
		
		Runnable r = new Runnable()
		{
			@Override
			public void run()
			{
				YouTubeUtil.openChannel(VideoMenuActivity.this, "mrcrayfishminecraft");
			}
		};
		items.add(new MenuItem("View Channel", "Go to MrCrayfish's Channel", R.drawable.menu_item_bg_3, r));
		
		Intent selectsIntent = new Intent(this, VideosActivity.class);
		selectsIntent.putExtra("playlist_id", "PLy11IosblXIHt81m2qd1d5QQXSlrRZWBi");
		selectsIntent.putExtra("title", "Cray Selects");
		items.add(new MenuItem("Cray Selects", "A selection of MrCrayfish's favourite videos", R.drawable.menu_item_bg_3, this, selectsIntent));
		
		menu.setAdapter(new MenuAdapter(this, items.toArray(new MenuItem[0])));

		Intent i = new Intent(this, ServiceVideoChecker.class);
		startService(i);

		getActionBar().setDisplayHomeAsUpEnabled(true);
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
		title.setText("Videos");

		ab.setCustomView(v);
		ab.setDisplayShowCustomEnabled(true);
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

	public void snap(View v)
	{
		MediaPlayer mp = MediaPlayer.create(this, R.raw.snap_snap);
		mp.start();
		Toast.makeText(this, "Snap Snap!", Toast.LENGTH_SHORT).show();
	}
}
