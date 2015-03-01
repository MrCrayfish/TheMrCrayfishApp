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
import android.widget.TextView;

import com.mrcrayfish.app.R;
import com.mrcrayfish.app.adapters.MenuAdapter;
import com.mrcrayfish.app.interfaces.IMenu;
import com.mrcrayfish.app.objects.MenuItem;

public class ModListActivity extends Activity implements IMenu
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

	@Override
	public ArrayAdapter<MenuItem> getMenuAdapter()
	{
		return adapater;
	}

	@Override
	public ArrayList<MenuItem> getItems()
	{
		ArrayList<MenuItem> mods = new ArrayList<MenuItem>();
		Intent furnitureIntent = new Intent(this, ModActivity.class);
		setModInfo(furnitureIntent, "cfm", R.string.name_furniture_mod, R.string.desc_furniture_mod);
		mods.add(new MenuItem("Furniture Mod", getResources().getString(R.string.desc_furniture_mod_simple), R.drawable.menu_item_bg_2, furnitureIntent));
		
		Intent skateboardIntent = new Intent(this, ModActivity.class);
		setModInfo(skateboardIntent, "csm", R.string.name_skateboard_mod, R.string.desc_skateboard_mod);
		mods.add(new MenuItem("Skateboarding Mod", "Adds skateboards, tricks, rail and ramps!", R.drawable.menu_item_bg_2, skateboardIntent));
		
		Intent constructionIntent = new Intent(this, ModActivity.class);
		setModInfo(constructionIntent, "ccm", R.string.name_construct_mod, R.string.desc_construct_mod);
		mods.add(new MenuItem("Construction Mod", "Create awesome buildings without skill!", R.drawable.menu_item_bg_2, constructionIntent));
		
		Intent tokensIntent = new Intent(this, ModActivity.class);
		setModInfo(tokensIntent, "ct", R.string.name_tokens_mod, R.string.desc_tokens_mod);
		mods.add(new MenuItem("CrayTokens", "A simple currency!", R.drawable.menu_item_bg_2, tokensIntent));
		return mods;
	}
	
	public void setModInfo(Intent modInfo, String modId, int modName, int modDesc)
	{
		modInfo.putExtra("modId", modId);
		modInfo.putExtra("modName", getResources().getString(modName));
		modInfo.putExtra("modDesc", getResources().getString(modDesc));
	}
}
