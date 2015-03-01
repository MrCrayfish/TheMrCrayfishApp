package com.mrcrayfish.app.activities;

import java.util.ArrayList;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.adapters.MenuAdapter;
import com.mrcrayfish.app.interfaces.IMenu;
import com.mrcrayfish.app.objects.MenuItem;
import com.mrcrayfish.app.util.YouTubeUtil;

public class VideoMenuActivity extends Activity implements IMenu
{
	private ListView menu;
	private MenuAdapter adapater;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_menu);
		overridePendingTransition(R.anim.animation_slide_left_1, R.anim.animation_slide_left_2);

		setupActionBar();

		menu = (ListView) findViewById(R.id.menuList);
		menu.setDivider(null);
		menu.setDividerHeight(0);

		adapater = new MenuAdapter(this, getItems());
		menu.setAdapter(adapater);

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

		ab.setDisplayHomeAsUpEnabled(true);
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

	@Override
	public ArrayList<MenuItem> getItems()
	{
		ArrayList<MenuItem> items = new ArrayList<MenuItem>();
		Intent videoIntent = new Intent(this, VideosActivity.class);
		videoIntent.putExtra("playlist_id", "UUSwwxl2lWJcbGOGQ_d04v2Q");
		items.add(new MenuItem("Latest Uploads", "Discover MrCrayfish's newest videos", R.drawable.menu_item_bg_7, videoIntent));
		items.add(new MenuItem("Playlists", "Collections of Videos", R.drawable.menu_item_bg_8, this, PlaylistActivity.class));
		items.add(new MenuItem("Watch Later", "Your saved videos", R.drawable.menu_item_bg_9, this, SavedVideosActivity.class));
		items.add(new MenuItem("View Channel", "Go to MrCrayfish's Channel", R.drawable.menu_item_bg_10, YouTubeUtil.openChannel(this, "mrcrayfishminecraft")));
		Intent selectsIntent = new Intent(this, VideosActivity.class);
		selectsIntent.putExtra("playlist_id", "PLy11IosblXIHt81m2qd1d5QQXSlrRZWBi");
		selectsIntent.putExtra("title", "Cray Selects");
		items.add(new MenuItem("Cray Selects", "A selection of MrCrayfish's favourites", R.drawable.menu_item_bg_11, selectsIntent));
		return items;
	}

	@Override
	public ArrayAdapter<MenuItem> getMenuAdapter()
	{
		return adapater;
	}
}
