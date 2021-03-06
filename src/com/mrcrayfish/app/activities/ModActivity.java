package com.mrcrayfish.app.activities;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.adapters.ModAdapter;
import com.mrcrayfish.app.mod.Mod;
import com.mrcrayfish.app.mod.ModPart;

public class ModActivity extends Activity
{
	private String modName;

	private ListView modContentlist;
	private ModAdapter adapter;
	private List<ModPart> modContent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_mod);
		overridePendingTransition(R.anim.animation_slide_left_1, R.anim.animation_slide_left_2);
		this.modName = getIntent().getStringExtra("modname");

		setupActionBar();

		modContentlist = (ListView) findViewById(R.id.modContentList);
		modContentlist.setDivider(null);
		modContentlist.setDividerHeight(5);

		modContent = Mod.getFromModId(getIntent().getStringExtra("modid"));
		if (modContent != null)
		{
			adapter = new ModAdapter(this, modContent);
			modContentlist.setAdapter(adapter);
		}

		getActionBar().setDisplayHomeAsUpEnabled(true);
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
		title.setText(modName);

		ab.setCustomView(v);
		ab.setDisplayShowCustomEnabled(true);
	}
}
