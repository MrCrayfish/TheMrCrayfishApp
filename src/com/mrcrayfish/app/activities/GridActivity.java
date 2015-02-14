package com.mrcrayfish.app.activities;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.adapters.MenuAdapter;
import com.mrcrayfish.app.objects.MenuItem;
import com.mrcrayfish.app.services.ServiceVideoChecker;

public class GridActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_grid);

		setupActionBar();

		ListView menu = (ListView) findViewById(R.id.menuList);
		menu.setDivider(null);
		menu.setDividerHeight(0);

		List<MenuItem> items = new ArrayList<MenuItem>();
		Intent videoIntent = new Intent(this, VideosActivity.class);
		videoIntent.putExtra("playlist_id", "PLy11IosblXIEvmCD1OOsbFkqowZZvN5xi");
		items.add(new MenuItem("Latest Videos", R.drawable.menu_item_bg_1, videoIntent));
		items.add(new MenuItem("Playlists", R.drawable.menu_item_bg_2, this, PlaylistActivity.class));
		items.add(new MenuItem("Saved Videos", R.drawable.menu_item_bg_3, this, SavedVideosActivity.class));
		items.add(new MenuItem("Furnture Server", R.drawable.menu_item_bg_4, this, EmptyActivity.class));
		items.add(new MenuItem("Category", R.drawable.menu_item_bg_5, this, EmptyActivity.class));
		items.add(new MenuItem("Category", R.drawable.menu_item_bg_6, this, SettingsActivity.class));
		menu.setAdapter(new MenuAdapter(this, items.toArray(new MenuItem[0])));

		Intent i = new Intent(this, ServiceVideoChecker.class);
		startService(i);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.grid, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item)
	{
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
