package com.mrcrayfish.app.activities;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.adapters.MenuAdapter;
import com.mrcrayfish.app.interfaces.IMenu;
import com.mrcrayfish.app.objects.MenuItem;

public class ModListActivity extends Activity implements IMenu
{
	private MenuAdapter adapater;
	
	public RelativeLayout loadingContainer;
	private TextView loadingText;
	private ListView modList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_mod_menu);
		overridePendingTransition(R.anim.animation_slide_left_1, R.anim.animation_slide_left_2);

		setupActionBar();

		modList = (ListView) findViewById(R.id.menuList);
		modList.setDivider(null);
		modList.setDividerHeight(0);
		
		loadingContainer = (RelativeLayout) findViewById(R.id.loadingContainer);
		loadingText = (TextView) findViewById(R.id.loadingText);

		adapater = new MenuAdapter(this, getItems());
		modList.setAdapter(adapater);

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
		title.setText("Mod List");

		ab.setCustomView(v);
		ab.setDisplayShowCustomEnabled(true);
	}

	public ArrayAdapter<MenuItem> getMenuAdapter()
	{
		return adapater;
	}

	public ArrayList<MenuItem> getItems()
	{
		ArrayList<MenuItem> mods = new ArrayList<MenuItem>();
		Intent furnitureIntent = new Intent(this, ModActivity.class);
		setModInfo(furnitureIntent, "cfm", R.string.name_furniture_mod, R.string.desc_furniture_mod);
		mods.add(new MenuItem("Furniture Mod", "Chairs, Tables, and more!", R.drawable.chair, furnitureIntent));

		Intent skateboardIntent = new Intent(this, ModActivity.class);
		setModInfo(skateboardIntent, "csm", R.string.name_skateboard_mod, R.string.desc_skateboard_mod);
		mods.add(new MenuItem("Skateboarding Mod", "Tricks, Flips, Grinds!", R.drawable.ic_skateboard, skateboardIntent));

		Intent constructionIntent = new Intent(this, ModActivity.class);
		setModInfo(constructionIntent, "ccm", R.string.name_construct_mod, R.string.desc_construct_mod);
		mods.add(new MenuItem("Construction Mod", "Create buildings without skill!", R.drawable.ic_hammer, constructionIntent));

		Intent tokensIntent = new Intent(this, ModActivity.class);
		setModInfo(tokensIntent, "ct", R.string.name_tokens_mod, R.string.desc_tokens_mod);
		mods.add(new MenuItem("CrayTokens", "A simple currency!", R.drawable.ic_token, tokensIntent));
		return mods;
	}

	public void setModInfo(Intent modInfo, String modId, int modName, int modDesc)
	{
		modInfo.putExtra("modId", modId);
		modInfo.putExtra("modName", getResources().getString(modName));
		modInfo.putExtra("modDesc", getResources().getString(modDesc));
	}
	
	public TextView getLoadingText()
	{
		return loadingText;
	}
}
