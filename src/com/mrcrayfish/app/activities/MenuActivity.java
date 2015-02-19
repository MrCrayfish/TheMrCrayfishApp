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
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.adapters.MenuAdapter;
import com.mrcrayfish.app.interfaces.IMenu;
import com.mrcrayfish.app.objects.MenuItem;
import com.mrcrayfish.app.services.ServiceVideoChecker;

public class MenuActivity extends Activity implements IMenu
{
	private ListView menu;
	private MenuAdapter adapater;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_menu);

		setupActionBar();

		menu = (ListView) findViewById(R.id.menuList);
		menu.setDivider(null);
		menu.setDividerHeight(0);

		adapater = new MenuAdapter(this, getItems());
		menu.setAdapter(adapater);

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

	public void snap(View v)
	{
		MediaPlayer mp = MediaPlayer.create(this, R.raw.snap_snap);
		mp.start();
	}

	@Override
	public ArrayList<MenuItem> getItems()
	{
		ArrayList<MenuItem> items = new ArrayList<MenuItem>();
		items.add(new MenuItem("Videos", "The latest and greatest content", R.drawable.menu_item_bg_1, this, VideoMenuActivity.class));
		items.add(new MenuItem("Mods", "Collection of Mods by MrCrayfish", R.drawable.menu_item_bg_2, this, EmptyActivity.class));
		items.add(new MenuItem("Blog", "Get the latest news and updates", R.drawable.menu_item_bg_5, this, BlogActivity.class));
		items.add(new MenuItem("Soundboard", "I think you know what this is!", R.drawable.menu_item_bg_3, this, EmptyActivity.class));
		items.add(new MenuItem("Social Media", "Want more content?", R.drawable.menu_item_bg_6, this, SettingsActivity.class));
		return items;
	}

	@Override
	public ArrayAdapter<MenuItem> getMenuAdapter()
	{
		return adapater;
	}
}
