package com.mrcrayfish.app.activities;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.adapters.SoundAdapter;
import com.mrcrayfish.app.objects.SoundItem;

public class SoundboardActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_soundboard);
		overridePendingTransition(R.anim.animation_slide_left_1, R.anim.animation_slide_left_2);

		GridView grid = (GridView) findViewById(R.id.soundGrid);
		grid.setAdapter(new SoundAdapter(this, compile()));

		setupActionBar();

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Toast.makeText(this, "Going to be redesigned!", Toast.LENGTH_LONG).show();
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
		title.setText("Soundboard");

		ab.setCustomView(v);
		ab.setDisplayShowCustomEnabled(true);
	}

	public List<SoundItem> compile()
	{
		List<SoundItem> items = new ArrayList<SoundItem>();
		items.add(new SoundItem("Oh Yeah!", R.raw.oh_yeah));
		items.add(new SoundItem("Drum Roll Please...", R.raw.drum_roll_please));
		items.add(new SoundItem("Snap Snap", R.raw.snap_snap));
		items.add(new SoundItem("64 Blocks Of Choice", R.raw.blocks_of_choice));
		items.add(new SoundItem("Burp!", R.raw.burp));
		items.add(new SoundItem("Fus Roh Quack", R.raw.fus_roh_quack));
		items.add(new SoundItem("Snore Dirt", R.raw.snore_dirt));
		items.add(new SoundItem("Hey, it's MrCrayfish", R.raw.hey_its_mrcrayfish));
		return items;
	}
}
